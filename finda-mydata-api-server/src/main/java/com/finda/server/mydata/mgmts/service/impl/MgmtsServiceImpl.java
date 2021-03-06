package com.finda.server.mydata.mgmts.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finda.server.mydata.auth.domain.entity.MydataTranReqPtclEntity;
import com.finda.server.mydata.auth.domain.entity.MydataUserEntity;
import com.finda.server.mydata.auth.domain.repository.MydataAuthHistRepository;
import com.finda.server.mydata.auth.domain.repository.MydataTranReqPtclRepository;
import com.finda.server.mydata.auth.domain.repository.MydataUserRepository;
import com.finda.server.mydata.common.domain.entity.ApiDomainCode;
import com.finda.server.mydata.common.dto.response.ResBodyDto;
import com.finda.server.mydata.mgmts.domain.entity.MgmtTokenEntity;
import com.finda.server.mydata.mgmts.domain.entity.OrgEntity;
import com.finda.server.mydata.mgmts.domain.entity.OrgIpEntity;
import com.finda.server.mydata.mgmts.domain.repository.MgmtOrgMapRepository;
import com.finda.server.mydata.mgmts.domain.repository.MgmtTokenRepository;
import com.finda.server.mydata.mgmts.domain.repository.MgmtsOrgIpRepository;
import com.finda.server.mydata.mgmts.domain.repository.MgmtsOrgRepository;
import com.finda.server.mydata.mgmts.dto.request.MgmtsAuthReqDto;
import com.finda.server.mydata.mgmts.dto.request.MgmtsConsentsReqDto;
import com.finda.server.mydata.mgmts.dto.request.MgmtsReqDto;
import com.finda.server.mydata.mgmts.dto.request.MgmtsStatisticsReqDto;
import com.finda.server.mydata.mgmts.dto.response.MgmtsAuthResDto;
import com.finda.server.mydata.mgmts.dto.response.MgmtsConsentsResDto;
import com.finda.server.mydata.mgmts.dto.response.MgmtsOrgResDto;
import com.finda.server.mydata.mgmts.service.MgmtsService;
import com.finda.server.mydata.transaction.ApiTransactionExecutor;
import com.finda.server.mydata.transaction.ApiTransactionRequest;
import com.finda.server.mydata.transaction.domain.ApiTranHistEntity;
import com.finda.server.mydata.transaction.domain.repository.ApiTranHistRepository;
import com.finda.server.util.MultiValueMapConverter;
import com.finda.services.finda.common.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class MgmtsServiceImpl implements MgmtsService {
    private static final int IRREGULAR = 2;

    @Value("${mydata.mgmt.oauth.secretkey}")
    private String secretKey;

    @Value("${mydata.mgmt.oauth.scope:manage}")
    private String scope;

    @Value("${mydata.mgmt.oauth.req.client.id}")
    private String clientId;

    @Value("${mydata.mgmt.oauth.req.client.secret}")
    private String clientSecret;

    @Value("${mydata.mgmt.oauth.ttl}")
    private int ttl;

    @Value("${mydata.code.findaOrgCode}")
    private String findaOrgCode;

    @Value("${mydata.code.portalOrgCode}")
    private String portalOrgCode;

    @Value("${mydata.portal.domain}")
    private String portalDomain;

    private final ApiTransactionExecutor apiTransactionExecutor;
    private final MgmtsOrgRepository mgmtsOrgRepository;
    private final MgmtsOrgIpRepository mgmtsOrgIpRepository;
    private final MydataUserRepository mydataUserRepository;
    private final MydataTranReqPtclRepository mydataTranReqPtclRepository;
    private final MgmtTokenRepository mgmtTokenRepository;
    private final MgmtOrgMapRepository mgmtOrgMapRepository;
    private final ObjectMapper objectMapper;
    private final int statDateCnt = 7;
    private final int tmSlotCnt = 2;
    private final Integer successCode = 200;
    private final ApiTranHistRepository apiTranHistRepository;
    private final Logger logger = LoggerFactory.getLogger("mydata");

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public MgmtsAuthResDto getMgmtsAuthToken001() {
        MgmtsAuthReqDto mgmtsAuthReqDto = new MgmtsAuthReqDto();
        mgmtsAuthReqDto.setRequest(clientId, clientSecret);

        MultiValueMap<String, String> header = new HttpHeaders();
        header.add("Content-Type", "application/x-www-form-urlencoded");
        MultiValueMap<String, String> params = MultiValueMapConverter.convert(objectMapper, mgmtsAuthReqDto);
        ApiDomainCode apiDomainCode = new ApiDomainCode(this.portalDomain, "/mgmts/oauth/2.0/token", null);
        ApiTransactionRequest<MultiValueMap<String, String>> apiTransactionRequest = new ApiTransactionRequest<>(
                params
                , header
                , HttpMethod.POST
                , apiDomainCode
                , null);
        try {
            ResponseEntity<MgmtsAuthResDto> res = apiTransactionExecutor.execute(apiTransactionRequest, MgmtsAuthResDto.class).get();
            if (res.getStatusCode().is2xxSuccessful()) {
                MgmtTokenEntity entity = new MgmtTokenEntity();
                BeanUtils.copyProperties(res.getBody(), entity);
                mgmtTokenRepository.save(entity);
                return res.getBody();

            }
            throw new RuntimeException(String.format("[mgmts] AuthToken Http Request Fail. HttpStatusCode: %d", res.getStatusCodeValue()));
        } catch (InterruptedException | ExecutionException e) {
            // exception ????????? ??????
            throw new RuntimeException(String.format("[mgmts] AuthToken Request Execution Fail. message: %s", e.getMessage()));
        }
    }

    private String getMgmtToken() {
        MgmtTokenEntity entity = mgmtTokenRepository.findAccessTokenByOrderByInsertTimeDesc();
        return entity.getAccessToken();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public MgmtsOrgResDto insertOrgsInfo() {
        MgmtsReqDto reqDto = new MgmtsReqDto();
        reqDto.setSearchTimestamp(0);

        MultiValueMap<String, String> header = new HttpHeaders();
        header.add("Authorization", "Bearer "+this.getMgmtToken());

        ApiTransactionRequest<Void> req = new ApiTransactionRequest<>(
                header
                , HttpMethod.GET
                , new ApiDomainCode(this.portalDomain, "/v1/mgmts/orgs", "")
                , reqDto);
        ResponseEntity<MgmtsOrgResDto> res = null;
        MgmtsOrgResDto resDto = null;
        try {
            res = apiTransactionExecutor.execute(req, MgmtsOrgResDto.class).get();
            if (res.getStatusCode().is2xxSuccessful()) {
                resDto = res.getBody();
            }
            this.setOrgInfo(resDto);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resDto;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public ResBodyDto sendStatistics(int type) {
        MgmtsStatisticsReqDto reqDto = setStatics(type);

        MultiValueMap<String, String> header = new HttpHeaders();
        header.add("Authorization", "Bearer "+this.getMgmtToken());

        ApiTransactionRequest<Void> req = new ApiTransactionRequest<>(
                header
                , HttpMethod.POST
                , new ApiDomainCode(this.portalDomain, "/v1/mgmts/statistics/mydata", "")
                , reqDto);
        ResponseEntity<MgmtsOrgResDto> res = null;
        MgmtsOrgResDto resDto = null;
        try {
            res = apiTransactionExecutor.execute(req, MgmtsOrgResDto.class).get();
            if (res.getStatusCode().is2xxSuccessful()) {
                resDto = res.getBody();
            }
            this.setOrgInfo(resDto);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resDto;
    }

    /**
     * ???????????? setting
     *
     * @param resDto
     */
    private void setOrgInfo(MgmtsOrgResDto resDto) {
        int orgCnt = resDto.getOrgCnt();
        if (orgCnt != 0) {
            List<MgmtsOrgResDto.OrgInfo> orgList = resDto.getOrgList();

            for (int i = 0; i < orgCnt; i++) {
                MgmtsOrgResDto.OrgInfo orgInfo = orgList.get(i);

                //???????????? ??????,??????,?????? ??????
                OrgEntity orgEntity = new OrgEntity();

                if (orgInfo.getOpType().equals("D")) { //??????
                    orgEntity = mgmtsOrgRepository.findByUniqueKey(orgInfo.getOrgCode());
                    mgmtsOrgRepository.delete(orgEntity);
                    /*orgIpEntity = mgmtsOrgIpRepository.findByOrgCode(orgInfo.getOrgCode());
                    mgmtsOrgIpRepository.delete(orgIpEntity);*/
                } else {//??????, ??????
                    BeanUtils.copyProperties(orgInfo, orgEntity);
                    mgmtsOrgRepository.save(orgEntity);

                //  setOrgIpInfo(orgInfo.getIpCnt(),orgInfo.getIpList(),orgInfo.getOrgCode());
                }
            }
        }
    }

    /**
     * ?????? IP?????? setting
     * @param ipList
     * @param orgCode
     */
    public void setOrgIpInfo(int cnt, List<MgmtsOrgResDto.IpInfo> ipList, String orgCode){

        OrgIpEntity orgIpEntity = new OrgIpEntity();
        if (cnt != 0) {
            for (int a = 0; a < cnt; a++) {
                MgmtsOrgResDto.IpInfo ipInfo = ipList.get(a);
                orgIpEntity.setOrgCode(orgCode);
                orgIpEntity.setSendRecvDv("recv"); // ?????????
                orgIpEntity.setIp(ipInfo.getIp());
                mgmtsOrgIpRepository.save(orgIpEntity);
            }
            /*List<MgmtsOrgResDto.DomainIpInfo> domainIpList = ipList;
            for (int a = 0; a < cnt; a++) {
                MgmtsOrgResDto.DomainIpInfo domainIpInfo = domainIpList.get(a);
                orgIpEntity.setOrgCode(orgCode);
                orgIpEntity.setSendRecvDv("send"); // ?????????
                orgIpEntity.setIp(domainIpInfo.getDomainIp());
                mgmtsOrgIpRepository.save(orgIpEntity);
            }*/
        }
    }

    /**
     * ???????????? setting
     * @param type
     * @return
     */
    public MgmtsStatisticsReqDto setStatics(int type){
        MgmtsStatisticsReqDto mgmtsStatisticsReqDto = new MgmtsStatisticsReqDto();
        mgmtsStatisticsReqDto.setClientId(clientId);

        LocalDate currentDate = LocalDate.now();

        mgmtsStatisticsReqDto.setInquiryDate(currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        mgmtsStatisticsReqDto.setStatDateCnt(statDateCnt);
        mgmtsStatisticsReqDto.setType(type);

        LocalDateTime aWeekAgo = currentDate.atStartOfDay().minusDays(7L);
        LocalDateTime yesterday = currentDate.atTime(LocalTime.MAX).minusDays(1L);

        // orgCode, apiCode??? Not Null?????? ?????? ?????? ?????? ???????????? 7??? ????????? apiTranHistory ?????? ??????
        List<ApiTranHistEntity> apiTranHistEntityList = apiTranHistRepository
                .findAllByOrgCodeIsNotNullAndApiCodeIsNotNullAndInsertTimeBetween(type, aWeekAgo, yesterday);

        //List<MydataTranReqPtclEntity> mydataTranReqPtclEntityList = mydataTranReqPtclRepository.findAllByOrgCodeAndDeleteYnIsFalseAndInsertTimeBetween(type, aWeekAgo, yesterday);

        // ?????????(insertTime), orgCode ???????????? Group By
        Map<LocalDate, Map<String, List<ApiTranHistEntity>>> apiTranHistoryPerDate = apiTranHistEntityList.stream()
                .collect(
                        groupingBy(h -> h.getInsertTime().toLocalDate(),
                                groupingBy(ApiTranHistEntity::getOrgCode)
                        )
                );

        List<MgmtsStatisticsReqDto.StatDateInfo> statDateList = new ArrayList<>();
        List<MgmtsStatisticsReqDto.StatOrgInfo> orgList = new ArrayList<>();
        List<MgmtsStatisticsReqDto.ApiTypeInfo> apiTypeList = new ArrayList<>();
        List<MgmtsStatisticsReqDto.TmSlotInfo> tmSlotList = new ArrayList<>();

        int consentNew;
        int consentRevoke;
        int consentOwn = 0;

        // ?????????(insertTime) ??????
        for (LocalDate date : apiTranHistoryPerDate.keySet()) {
            MgmtsStatisticsReqDto.StatDateInfo statDateInfo = new MgmtsStatisticsReqDto.StatDateInfo();
            Map<String, List<ApiTranHistEntity>> apiTranHistoryByOrgCode = apiTranHistoryPerDate.get(date);
            Set<String> orgCodeKeySet = apiTranHistoryByOrgCode.keySet();
            int orgCnt = orgCodeKeySet.size();

            statDateInfo.setStatDate(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            statDateInfo.setOrgCnt(orgCnt);

            // orgCode??? ??????
            for (String orgCode : orgCodeKeySet) {
                MgmtsStatisticsReqDto.StatOrgInfo statOrgInfo = new MgmtsStatisticsReqDto.StatOrgInfo();

                // ?????????????????? ?????? consent_new, consent_revoke, consent_own ??????
                if (mgmtsStatisticsReqDto.getType() == IRREGULAR) {
                    // apiCode ??? ???????????? ?????? code filter??? ?????? ??????
                    List<ApiTranHistEntity> apiTranHistoryList = apiTranHistoryByOrgCode.get(orgCode)
                            .stream()
                            .filter(h -> h.getApiDomainCode().getApiCode().equals("AU02")
                                    || h.getApiDomainCode().getApiCode().equals("AU03")
                                    || h.getApiDomainCode().getApiCode().equals("AU04")
                                    || h.getApiDomainCode().getApiCode().equals("AU11"))
                            .sorted(Comparator.comparing(ApiTranHistEntity::getInsertTime).reversed())
                            .collect(toList());

                    // ?????? ?????? ?????? ???????????? ??? count
                    consentNew = (int) apiTranHistoryList.stream()
                            .filter(h -> h.getApiDomainCode().getApiCode().equals("AU02")
                                    || h.getApiDomainCode().getApiCode().equals("AU03")
                                    || h.getApiDomainCode().getApiCode().equals("AU11")).count();

                    // ????????? ???????????? ??? count
                    consentRevoke = (int) apiTranHistoryList.stream()
                            .filter(h -> h.getApiDomainCode().getApiCode().equals("AU04")).count();

                    // ?????? ????????? ???????????? ??? count, ?????? ?????? ??? ?????? count ??????
                    for (ApiTranHistEntity apiTranHistEntity : apiTranHistoryList) {
                        if (apiTranHistEntity.getApiDomainCode().getApiCode().equals("AU02")
                                || apiTranHistEntity.getApiDomainCode().getApiCode().equals("AU03")
                                || apiTranHistEntity.getApiDomainCode().getApiCode().equals("AU11")) {
                            consentOwn++;
                        } else if (apiTranHistEntity.getApiDomainCode().getApiCode().equals("AU04")) {
                            consentOwn = 0;
                        }

                    }

                    statOrgInfo.setConsentNew(consentNew);
                    statOrgInfo.setConsentRevoke(consentRevoke);
                    statOrgInfo.setConsentOwn(consentOwn);
                }

                // ApiCode ???????????? Group By
                Map<String, List<ApiTranHistEntity>> apiTranHistoryByApiCode = apiTranHistoryByOrgCode
                        .get(orgCode)
                        .stream()
                        .collect(groupingBy(h -> h.getApiDomainCode().getApiCode()));

                // ApiCode Key ??? ???????????? ApiTypeCnt ??????
                Set<String> apiCodeKeySet = apiTranHistoryByApiCode.keySet();
                int apiCodeKeySetSize = apiCodeKeySet.size();
                statOrgInfo.setApiTypeCnt(apiCodeKeySetSize);

                // apiCode??? ??????
                for (String apiCode : apiTranHistoryByApiCode.keySet()) {
                    MgmtsStatisticsReqDto.ApiTypeInfo apiTypeInfo = new MgmtsStatisticsReqDto.ApiTypeInfo();
                    List<ApiTranHistEntity> apiTranHistEntitiesByApiCode = apiTranHistoryByApiCode.get(apiCode);

                    apiTypeInfo.setApiType(apiCode);
                    apiTypeInfo.setTmSlotCnt(tmSlotCnt);

                    // tmSlot ???????????? Group By
                    Map<String, List<ApiTranHistEntity>> apiTranHistoryByTmSlot = apiTranHistEntitiesByApiCode.stream().collect(
                            groupingBy(ApiTranHistEntity::getTmSlot)
                    );

                    // tmSlot??? ??????
                    for (String tmSlot : apiTranHistoryByTmSlot.keySet()) {
                        MgmtsStatisticsReqDto.TmSlotInfo tmSlotInfo = new MgmtsStatisticsReqDto.TmSlotInfo();
                        List<ApiTranHistEntity> apiTranHistoryForTmSlotList = apiTranHistoryByTmSlot.get(tmSlot);

                        // rspTime ????????? ?????? ?????? ??? List ??????
                        List<Long> rspTimeList = apiTranHistoryForTmSlotList.stream()
                                .map(ApiTranHistEntity::getResponseTime)
                                .collect(toList());

                        // statusCode ?????? ??? List ??????
                        List<Integer> statusCodeList = apiTranHistoryForTmSlotList.stream()
                                .map(ApiTranHistEntity::getStatusCode)
                                .collect(toList());

                        long rspTotal = rspTimeList.stream().mapToLong(i -> i).sum();
                        long rspAvg = 0;
                        long rspStDev = 0;
                        if (!rspTimeList.isEmpty()) {

                            // rspTime ????????? ??????
                            rspAvg = (rspTotal / rspTimeList.size());
                            int sum = 0;

                            for (Long rspTime : rspTimeList) {
                                sum += Math.pow(rspTime - rspAvg, 2);
                            }

                            // rspTime ???????????? ??????
                            rspStDev = (long) Math.sqrt(sum / rspAvg);
                        }

                        int successCnt = 0;
                        int failCnt = 0;

                        // statusCode ??????, ?????? ?????? ?????? ??? count
                        for (Integer statusCode : statusCodeList) {
                            if (statusCode.equals(successCode)) {
                                successCnt++;
                            } else {
                                failCnt++;
                            }
                        }

                        tmSlotInfo.setTmSlot(tmSlot);
                        tmSlotInfo.setRspAvg(String.valueOf(rspAvg));
                        tmSlotInfo.setRspTotal(String.valueOf(rspTotal));
                        tmSlotInfo.setRspStdev(String.valueOf(rspStDev));
                        tmSlotInfo.setSuccessApiCnt(String.valueOf(successCnt));
                        tmSlotInfo.setFailApiCnt(String.valueOf(failCnt));

                        tmSlotList.add(tmSlotInfo);
                    }

                    apiTypeInfo.setTmSlotList(tmSlotList);
                    apiTypeList.add(apiTypeInfo);
                }

                statOrgInfo.setApiTypeList(apiTypeList);
                orgList.add(statOrgInfo);
            }

            statDateInfo.setOrgList(orgList);
            statDateList.add(statDateInfo);
        }
        mgmtsStatisticsReqDto.setStatDateList(statDateList);
        logger.info("mgmtsStatisticsReqDto: {}",mgmtsStatisticsReqDto);
        return mgmtsStatisticsReqDto;
    }

    @Override
    public MgmtsAuthResDto getMgmtsAuthToken(MgmtsAuthReqDto mgmtsAuthReqDto) {
        Map<String, Object> claims = getClaims();
        String token = JwtUtils.createToken(claims, this.secretKey, ttl);
        MgmtsAuthResDto mgmtsAuthResDto = new MgmtsAuthResDto();
        mgmtsAuthResDto.setResponseToken(token, ttl);
        return mgmtsAuthResDto;
    }

    private Map<String, Object> getClaims() {
        String jti = UUID.randomUUID().toString();
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("iss", this.findaOrgCode); // ?????? ????????????
        claims.put("aud", this.portalOrgCode); // ???????????? ????????????
        claims.put("jti", jti); // ?????? ???
        claims.put("scope", this.scope);
        return claims;
    }

    @Override
    public MgmtsConsentsResDto getMgmtsConsents(MgmtsConsentsReqDto mgmtsConsentsReqDto) {
        MydataUserEntity mydataUserEntity = mydataUserRepository.findByUserCi(mgmtsConsentsReqDto.getUserCi());
        MgmtsConsentsResDto resDto = new MgmtsConsentsResDto();

        if (mydataUserEntity == null) {
            resDto.setDtoIsMemberFalse();
        } else {
            List<MydataTranReqPtclEntity> list = mydataTranReqPtclRepository.findAllByUserCi(mydataUserEntity.getUserCi());
            resDto.setDtoIsMemberTrue(clientId, list);
        }
        return resDto;
    }


}
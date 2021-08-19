package com.finda.server.mydata.common.service.impl;

import com.finda.server.mydata.common.code.ApiType;
import com.finda.server.mydata.common.domain.entity.OrgApiInfoEntity;
import com.finda.server.mydata.common.domain.entity.OrgConsentsInfoEntity;
import com.finda.server.mydata.common.domain.entity.id.OrgApiInfoId;
import com.finda.server.mydata.common.domain.entity.id.OrgConsentsInfoId;
import com.finda.server.mydata.common.domain.repository.OrgApiInfoRepository;
import com.finda.server.mydata.common.domain.repository.OrgConsentsInfoRepository;
import com.finda.server.mydata.common.dto.request.OrgReqDto;
import com.finda.server.mydata.common.dto.response.OrgApiResDto;
import com.finda.server.mydata.common.dto.response.OrgConsentsResDto;
import com.finda.server.mydata.common.service.ApiUrlGenerator;
import com.finda.server.mydata.common.service.CommonService;
import com.finda.server.mydata.transaction.ApiTransactionExecutor;
import com.finda.server.mydata.transaction.ApiTransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
public class CommonServiceImpl implements CommonService {

    private final ApiTransactionExecutor apiTransactionExecutor;
    private final ApiUrlGenerator apiUrlGenerator;
    private final OrgApiInfoRepository orgApiInfoRepository;
    private final OrgConsentsInfoRepository orgConsentsInfoRepository;

    @Override
    public OrgApiResDto searchApiList(String industry, OrgReqDto reqDto) {
        ApiTransactionRequest<Void> req = new ApiTransactionRequest<>(
                null
                , HttpMethod.GET
                , apiUrlGenerator.getUrl(reqDto.getOrgCode(),ApiType.API_TYPE_CM01.getCode())
                , reqDto
        );
        ResponseEntity<OrgApiResDto> res = null;
        OrgApiResDto resDto = null;
        try {
            res = apiTransactionExecutor.execute(req, OrgApiResDto.class).get();
            if (res.getStatusCode().is2xxSuccessful()) {
                resDto = res.getBody();
            }
            this.setApiInfo(reqDto.getOrgCode(),resDto);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resDto;
    }


    @Override
    public OrgConsentsResDto searchConsents(String industry, Long authUserId,OrgReqDto reqDto) {
        ApiTransactionRequest<Void> req = new ApiTransactionRequest<>(
                apiUrlGenerator.getHeaders(authUserId)
                , HttpMethod.GET
                , apiUrlGenerator.getUrl(reqDto.getOrgCode(),ApiType.API_TYPE_CM02.getCode())
                , reqDto);
        ResponseEntity<OrgConsentsResDto> res = null;
        OrgConsentsResDto resDto = null;
        try {
            res = apiTransactionExecutor.execute(req, OrgConsentsResDto.class).get();
            if (res.getStatusCode().is2xxSuccessful()) {
                resDto = res.getBody();
            }
            this.setConsentsInfo(authUserId,reqDto,resDto);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resDto;
    }

    /**
     * 정보제공자 API 목록 저장
     * @param orgCode,resDto
     */
    private void setApiInfo(String orgCode, OrgApiResDto resDto) {
        int apiCnt = resDto.getApiCnt();
        if (apiCnt != 0) {
            List<OrgApiResDto.ApiInfo> apiList = resDto.getApiList();
            for (int i = 0; i < apiCnt; i++) {
                OrgApiResDto.ApiInfo apiInfo = apiList.get(i);
                String apiCode = apiInfo.getApiCode();
                String apiUri = apiInfo.getApiUri();

                OrgApiInfoEntity orgApiInfoEntity = orgApiInfoRepository.findUriByApiCode(orgCode, apiCode);
                if (orgApiInfoEntity != null) {
                    orgApiInfoRepository.delete(orgApiInfoEntity);
                }
                orgApiInfoEntity.setOrgApiInfoId(new OrgApiInfoId(orgCode,apiCode));
                orgApiInfoEntity.setApiUri(apiUri);
                orgApiInfoEntity.setVersion(resDto.getVersion());
                orgApiInfoEntity.setMinVersion(resDto.getMinVersion());
            }
        }
    }

    /**
     * 전송요구내역 저장
     * @param authUserId, reqDto, resDto
     */
    private void setConsentsInfo(Long authUserId, OrgReqDto reqDto, OrgConsentsResDto resDto) {
        OrgConsentsInfoEntity orgConsentsInfoEntity = orgConsentsInfoRepository.findByUnikey(reqDto.getOrgCode(),authUserId);
        if (orgConsentsInfoEntity != null) {
            orgConsentsInfoRepository.delete(orgConsentsInfoEntity);
        }
        orgConsentsInfoEntity.setOrgConsentsInfoId(new OrgConsentsInfoId(reqDto.getOrgCode(),authUserId));
        orgConsentsInfoEntity.setScheduled(resDto.isScheduled());
        orgConsentsInfoEntity.setCycle(resDto.getCycle());
        orgConsentsInfoEntity.setEndDate(resDto.getEndDate());
        orgConsentsInfoEntity.setPurpose(resDto.getPurpose());
        orgConsentsInfoEntity.setPeriod(resDto.getPeriod());
    }
}

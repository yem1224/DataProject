package com.finda.server.mydata.auth.service.impl;

import com.finda.server.mydata.auth.AuthConstants;
import com.finda.server.mydata.auth.domain.entity.MydataTranReqPtclEntity;
import com.finda.server.mydata.auth.domain.entity.id.MydataTranReqPtclId;
import com.finda.server.mydata.auth.domain.repository.MydataTranReqPtclHistoryRepository;
import com.finda.server.mydata.auth.domain.repository.MydataTranReqPtclRepository;
import com.finda.server.mydata.auth.service.MydataTranReqPtclManagementService;
import com.finda.server.mydata.common.code.ApiType;
import com.finda.server.mydata.common.domain.entity.ApiDomainCode;
import com.finda.server.mydata.common.dto.response.MydataTranReqPtclResDto;
import com.finda.server.mydata.mgmts.service.MgmtsQueryService;
import com.finda.server.mydata.transaction.ApiTransactionExecutor;
import com.finda.server.mydata.transaction.ApiTransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
public class MydataTranReqPtclManagementServiceImpl implements MydataTranReqPtclManagementService {

    private final MydataTranReqPtclRepository mydataTranReqPtclRepository;
    private final MydataTranReqPtclHistoryRepository mydataTranReqPtclHistoryRepository;
    private final ApiTransactionExecutor apiTransactionExecutor;
    private final MgmtsQueryService mgmtsQueryService;

    @Transactional
    @Override
    public void requestAndSave(String userCi, String orgCode, String accessToken) {
        HttpHeaders httpHeaders = createHeaders(accessToken);
        MultiValueMap<String, String> requestParameters = createRequestParameters(orgCode);

        try {
            ResponseEntity<MydataTranReqPtclResDto> responseEntity = apiTransactionExecutor.execute(
                    userCi,
                    createTranReqPtclRequest(orgCode, httpHeaders, requestParameters),
                    MydataTranReqPtclResDto.class
            ).get();

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                MydataTranReqPtclResDto mydataTranReqPtclResDto = responseEntity.getBody();

                MydataTranReqPtclEntity mydataTranReqPtcl = createOrUpdateMydataTranReqPtcl(userCi, orgCode, mydataTranReqPtclResDto);
                Long nextSeqNo = findNextSeqNo(userCi, orgCode);

                mydataTranReqPtclHistoryRepository.save(mydataTranReqPtcl.toHistory(nextSeqNo));
                mydataTranReqPtclRepository.save(mydataTranReqPtcl);
            } else {
                //TODO 요청 실패 시, 로직
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("[MydataTranReqPtclManagementServiceImpl] RestTemplate execute fail.");
        }
    }

    private HttpHeaders createHeaders(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AuthConstants.AUTHORIZATION, accessToken);
        return httpHeaders;
    }

    private MultiValueMap<String, String> createRequestParameters(String orgCode) {
        MultiValueMap<String, String> requestParameters = new LinkedMultiValueMap<>();
        requestParameters.add(AuthConstants.ORG_CODE, orgCode);
        return requestParameters;
    }

    private ApiTransactionRequest<Void> createTranReqPtclRequest(String orgCode, HttpHeaders httpHeaders, MultiValueMap<String, String> requestParameters) {
        return new ApiTransactionRequest<>(
                httpHeaders,
                HttpMethod.GET,
                new ApiDomainCode(mgmtsQueryService.findDomainByOrgCode(orgCode), ApiType.API_TYPE_CM02),
                requestParameters
        );
    }

    private MydataTranReqPtclEntity createOrUpdateMydataTranReqPtcl(String userCi,
                                                                    String orgCode,
                                                                    MydataTranReqPtclResDto mydataTranReqPtclResDto) {
        Optional<MydataTranReqPtclEntity> foundEntity = mydataTranReqPtclRepository
                .findByUserCiAndOrgCode(userCi, orgCode);

        return foundEntity.map(mydataTranReqPtclEntity -> updateEntity(mydataTranReqPtclResDto, mydataTranReqPtclEntity))
                .orElseGet(() ->
                        MydataTranReqPtclEntity.builder()
                                .userCi(userCi)
                                .orgCode(orgCode)
                                .isScheduled(mydataTranReqPtclResDto.getIsScheduled())
                                .cycle(mydataTranReqPtclResDto.getCycle())
                                .endDate(mydataTranReqPtclResDto.getEndDate())
                                .purpose(mydataTranReqPtclResDto.getPurpose())
                                .period(mydataTranReqPtclResDto.getPeriod())
                                .build()
                );
    }

    private long findNextSeqNo(String userCi, String orgCode) {
        return mydataTranReqPtclHistoryRepository.findLastSeqNoByCiAndOrgCode(userCi, orgCode) + 1L;
    }

    private MydataTranReqPtclEntity updateEntity(MydataTranReqPtclResDto mydataTranReqPtclResDto, MydataTranReqPtclEntity entity) {
        entity.update(
                mydataTranReqPtclResDto.getIsScheduled(),
                mydataTranReqPtclResDto.getCycle(),
                mydataTranReqPtclResDto.getEndDate(),
                mydataTranReqPtclResDto.getPurpose(),
                mydataTranReqPtclResDto.getPeriod()
        );
        return entity;
    }

    @Override
    public void delete(String userCi, String orgCode) {
        mydataTranReqPtclRepository.deleteById(MydataTranReqPtclId.create(userCi, orgCode));
    }
}

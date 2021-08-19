package com.finda.server.mydata.auth.service;

import com.finda.server.mydata.auth.AuthConstants;
import com.finda.server.mydata.auth.domain.entity.IntegrationAuthNonceEntity;
import com.finda.server.mydata.auth.domain.entity.id.IntegrationAuthNonceId;
import com.finda.server.mydata.auth.domain.repository.IntegrationAuthNonceRepository;
import com.finda.server.mydata.auth.dto.SignedSignatureDto;
import com.finda.server.mydata.auth.dto.request.IntegrationAuthReqDto;
import com.finda.server.mydata.auth.dto.response.AuthTokenDto;
import com.finda.server.mydata.auth.dto.response.IntegrationAuthResDto;
import com.finda.server.mydata.common.code.ApiType;
import com.finda.server.mydata.common.domain.entity.ApiDomainCode;
import com.finda.server.mydata.mgmts.domain.repository.MgmtsOrgRepository;
import com.finda.server.mydata.transaction.ApiTransactionExecutor;
import com.finda.server.mydata.transaction.ApiTransactionRequest;
import com.finda.services.finda.common.exception.EntityNotFoundException;
import com.finda.services.finda.common.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Component
public class IntegrationAuthTransactionExecutor {

    @Value("${mydata.mgmt.oauth.req.client.id}")
    private String clientId;

    @Value("${mydata.mgmt.oauth.req.client.secret}")
    private String clientSecret;

    private final TxIdGenerator txIdGenerator;
    private final IntegrationAuthNonceRepository integrationAuthNonceRepository;
    private final MydataAuthBaseManagementService mydataAuthBaseManagementService;
    private final ApiTransactionExecutor apiTransactionExecutor;
    private final MydataTranReqPtclManagementService mydataTranReqPtclManagementService;

    @Transactional
    public void authSessionCheck(String authSession) {
        List<IntegrationAuthNonceEntity> integrationAuthNonces = integrationAuthNonceRepository.findAllBySession(authSession);
        if (integrationAuthNonces.isEmpty()) {
            throw new IllegalArgumentException(String.format("AuthSession is not exists. authSession: [%s]", authSession));
        }

        integrationAuthNonces.forEach(integrationAuthNonceEntity -> {
            if (integrationAuthNonceEntity.isExpired()) {
                throw new IllegalArgumentException(String.format("AuthSession is expired already. authSession: [%s]", authSession));
            }
            integrationAuthNonceEntity.expire();
        });
        integrationAuthNonceRepository.saveAll(integrationAuthNonces);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Async
    public void execute(String session, String userCi, String caOrgCode, String orgInfoDomain, SignedSignatureDto signedSignatureDto, LocalDateTime transactionTime) {
        String txId = txIdGenerator.issuedAt(transactionTime, signedSignatureDto.getOrgCode(), caOrgCode);
        IntegrationAuthNonceEntity integrationAuthNonceEntity = integrationAuthNonceRepository
                .findById(IntegrationAuthNonceId.create(session, userCi, signedSignatureDto.getOrgCode()))
                .orElseThrow(() -> new EntityNotFoundException(
                        ServiceException.ErrorType.ClientSystem,
                        "Integration Authentication IntegrationAuthNonceEntity",
                        String.format("IntegrationAuthNonceEntity is not exists by key [%s]", userCi + "-" + signedSignatureDto),
                        null
                ));

        if (integrationAuthNonceEntity.isExpired()) {
            throw new IllegalArgumentException(String.format("OrgCode[%s] session[%s] is expired.", signedSignatureDto.getOrgCode(), integrationAuthNonceEntity.getSession()));
        }
        integrationAuthNonceEntity.expire();

        IntegrationAuthReqDto integrationAuthReqDto = IntegrationAuthReqDto.builder()
                .txId(txId)
                .orgCode(signedSignatureDto.getOrgCode())
                .grantType(AuthConstants.PASSWORD)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .caCode(caOrgCode)
                .username(userCi)
                .passwordLen(signedSignatureDto.getSignedConsent().length())
                .password(signedSignatureDto.getSignedConsent())
                .authType("0")
                .consentType("0")
                .signedPersonInfoReqLen(signedSignatureDto.getSignedPersonInfoReq().length())
                .signedPersonInfoReq(signedSignatureDto.getSignedPersonInfoReq())
                .consentNonce(integrationAuthNonceEntity.getConsentNonce())
                .ucpidNonce(integrationAuthNonceEntity.getUcpidNonce())
                .build();

        try {
            ResponseEntity<IntegrationAuthResDto> response = apiTransaction(userCi, orgInfoDomain, integrationAuthReqDto);
            if (response.getStatusCode().is2xxSuccessful()) {
                IntegrationAuthResDto integrationAuthResDto = response.getBody();
                updateIssuedAuthToken(userCi, signedSignatureDto.getOrgCode(), integrationAuthResDto, transactionTime, caOrgCode);
                mydataTranReqPtclManagementService.requestAndSave(
                        userCi,
                        signedSignatureDto.getOrgCode(),
                        integrationAuthResDto.getAccessToken()
                );
            } else {
                updateFailedAuthToken(userCi, signedSignatureDto.getOrgCode());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        integrationAuthNonceRepository.save(integrationAuthNonceEntity);
    }

    private ResponseEntity<IntegrationAuthResDto> apiTransaction(String userCi,
                                                                 String orgInfoDomain,
                                                                 IntegrationAuthReqDto integrationAuthReqDto) throws InterruptedException, ExecutionException {
        return apiTransactionExecutor.execute(
                userCi,
                new ApiTransactionRequest<>(integrationAuthReqDto,
                        HttpMethod.POST,
                        new ApiDomainCode(orgInfoDomain, ApiType.API_TYPE_AU11),
                        null
                ),
                IntegrationAuthResDto.class
        ).get();
    }

    private void updateIssuedAuthToken(String userCi,
                                       String orgCode,
                                       IntegrationAuthResDto integrationAuthResDto,
                                       LocalDateTime now,
                                       String caOrgCode) {
        mydataAuthBaseManagementService.issueToken(
                userCi,
                orgCode,
                AuthTokenDto.builder()
                        .tokenType(integrationAuthResDto.getTokenType())
                        .accessToken(integrationAuthResDto.getAccessToken())
                        .expiresIn(integrationAuthResDto.getExpiresIn())
                        .refreshToken(integrationAuthResDto.getRefreshToken())
                        .refreshTokenExpiresIn(integrationAuthResDto.getRefreshTokenExpiresIn())
                        .scope(integrationAuthResDto.getScope())
                        .build(),
                now,
                caOrgCode
        );
    }

    private void updateFailedAuthToken(String userCi,
                                       String orgCode) {
        mydataAuthBaseManagementService.failedIssueToken(userCi, orgCode);
    }
}

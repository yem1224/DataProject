package com.finda.server.mydata.auth.service.impl;

import com.finda.server.mydata.auth.domain.entity.MydataAuthBaseEntity;
import com.finda.server.mydata.auth.domain.entity.MydataUserEntity;
import com.finda.server.mydata.auth.domain.entity.StateSessionEntity;
import com.finda.server.mydata.auth.domain.repository.MydataUserRepository;
import com.finda.server.mydata.auth.domain.repository.StateSessionRepository;
import com.finda.server.mydata.auth.dto.IndividualAuthResDto;
import com.finda.server.mydata.auth.dto.request.*;
import com.finda.server.mydata.auth.dto.response.AuthRenewResDto;
import com.finda.server.mydata.auth.dto.response.AuthTokenDto;
import com.finda.server.mydata.auth.dto.response.IndividualAuthRevokeResDto;
import com.finda.server.mydata.auth.service.IndividualAuthService;
import com.finda.server.mydata.auth.service.MydataAuthBaseManagementService;
import com.finda.server.mydata.auth.service.MydataTranReqPtclManagementService;
import com.finda.server.mydata.common.code.ApiType;
import com.finda.server.mydata.common.domain.entity.ApiDomainCode;
import com.finda.server.mydata.mgmts.service.MgmtsQueryService;
import com.finda.server.mydata.transaction.ApiTransactionExecutor;
import com.finda.server.mydata.transaction.ApiTransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
public class IndividualAuthServiceImpl implements IndividualAuthService {

    private static final String RESPONSE_TYPE = "code";

    @Value("${mydata.mgmt.oauth.req.client.id}")
    private String clientId;

    @Value("${mydata.mgmt.oauth.req.client.secret}")
    private String clientSecret;

    @Value("${mydata.mgmt.oauth.redirectUri}")
    private String redirectUri;

    @Value("${mydata.mgmt.oauth.appScheme}")
    private String appScheme;

    private final StateSessionRepository stateSessionRepository;
    private final ApiTransactionExecutor apiTransactionExecutor;
    private final MydataAuthBaseManagementService mydataAuthBaseManagementService;
    private final MydataUserRepository mydataUserRepository;
    private final MgmtsQueryService mgmtsQueryService;
    private final MydataTranReqPtclManagementService mydataTranReqPtclManagementService;

    @Transactional
    @Override
    public IndividualAuthResDto authorize(Long userId, AuthorizeReqDto authorizeReqDto) {
        MydataUserEntity mydataUserEntity = mydataUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("This user is not registered MyData Service [userId: %d]", userId)));

        StateSessionEntity state = createStateSession(mydataUserEntity.getUserCi(), authorizeReqDto.getOrgCode());
        authorizeReqDto.setResponseType("code");
        authorizeReqDto.setState(state.getId());
        authorizeReqDto.setClientId(clientId);
        authorizeReqDto.setRedirectUri(redirectUri);
        authorizeReqDto.setAppScheme(appScheme);

        ApiTransactionRequest<AuthorizeReqDto> individualAuthorizeApiTransactionRequest
                = createIndividualAuthorizeHttpRequest(mydataUserEntity.getUserCi(), authorizeReqDto);

        CompletableFuture<ResponseEntity<Void>> execute =
                apiTransactionExecutor.execute(individualAuthorizeApiTransactionRequest, Void.class);

        try {
            ResponseEntity<Void> responseEntity = execute.get();
            URI location = responseEntity.getHeaders().getLocation();
            return IndividualAuthResDto.builder()
                    .redirectUri(location)
                    .state(state.getId())
                    .build();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(String.format("[Individual authorize] Exception : %s", e.getMessage()));
        }
    }

    private StateSessionEntity createStateSession(String userCi, String orgCode) {
        StateSessionEntity stateSession = StateSessionEntity.builder()
                .userCi(userCi)
                .orgCode(orgCode)
                .build();
        return stateSessionRepository.save(stateSession);
    }

    private ApiTransactionRequest<AuthorizeReqDto> createIndividualAuthorizeHttpRequest(
            String userCi,
            AuthorizeReqDto authorizeReqDto) {
        MultiValueMap<String, String> headers = createIndividualAuthorizeRequestHeader(userCi);

        return new ApiTransactionRequest<>(
                authorizeReqDto,
                headers,
                HttpMethod.GET,
                new ApiDomainCode(mgmtsQueryService.findDomainByOrgCode(authorizeReqDto.getOrgCode()), ApiType.API_TYPE_AU01),
                null
        );
    }

    private MultiValueMap<String, String> createIndividualAuthorizeRequestHeader(String userCi) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("x-user-ci", userCi);
        return headers;
    }

    @Transactional
    @Override
    public void issueToken(AuthCallbackReqDto authCallbackReqDto) {
        StateSessionEntity stateSession = findStateSession(authCallbackReqDto.getState());
        AuthTokenReqDto authTokenReqDto = createIndividualAuthTokneRequest(authCallbackReqDto, stateSession);

        try {
            CompletableFuture<ResponseEntity<AuthTokenDto>> completableAuthTokenFuture = apiTransactionExecutor.execute(
                    createIssueTokenRequest(
                            authTokenReqDto,
                            mgmtsQueryService.findDomainByOrgCode(authTokenReqDto.getOrgCode())
                    ),
                    AuthTokenDto.class
            );

            ResponseEntity<AuthTokenDto> responseEntity = completableAuthTokenFuture.get();
            AuthTokenDto authTokenDto = responseEntity.getBody();
            mydataAuthBaseManagementService.issueToken(
                    stateSession.getUserCi(),
                    authTokenReqDto.getOrgCode(),
                    authTokenDto,
                    LocalDateTime.now(),
                    null
            );
            mydataTranReqPtclManagementService.requestAndSave(
                    stateSession.getUserCi(),
                    stateSession.getOrgCode(),
                    authTokenDto.getAccessToken()
            );
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("[issue] RestTemplate execute fail.");
        }
    }

    private StateSessionEntity findStateSession(String state) {
        StateSessionEntity stateSessionEntity = stateSessionRepository.findById(state)
                .orElseThrow(() -> new RuntimeException("StateSession is not exists"));

        if (stateSessionEntity.isExpired()) {
            throw new RuntimeException("State Session is expired.");
        }

        return stateSessionEntity;
    }

    private AuthTokenReqDto createIndividualAuthTokneRequest(AuthCallbackReqDto authCallbackReqDto, StateSessionEntity stateSession) {
        return AuthTokenReqDto.builder()
                .orgCode(stateSession.getOrgCode())
                .grantType("authorization_code")
                .code(authCallbackReqDto.getCode())
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri(redirectUri)
                .build();
    }

    private ApiTransactionRequest<AuthTokenReqDto> createIssueTokenRequest(
            AuthTokenReqDto authTokenReqDto,
            String domain) {
        return new ApiTransactionRequest<>(
                authTokenReqDto,
                new HttpHeaders(),
                HttpMethod.POST,
                new ApiDomainCode(domain, ApiType.API_TYPE_AU02),
                null
        );
    }

    @Transactional
    @Override
    public void renew(Long userId, AuthTokenRenewReqDto authTokenRenewReqDto) {
        MydataUserEntity mydataUserEntity = mydataUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("This user is not registered MyData Service [userId: %d]", userId)));

        MydataAuthBaseEntity mydataAuthBaseEntity = mydataAuthBaseManagementService
                .findByUserIdAndOrgCode(mydataUserEntity.getUserCi(), authTokenRenewReqDto.getOrgCode())
                .orElseThrow(() -> new EntityNotFoundException(String.format("This orgCode is not exists [orgCode: %s]", authTokenRenewReqDto.getOrgCode())));

        setAuthTokenRenewReqData(authTokenRenewReqDto, mydataAuthBaseEntity);

        try {
            CompletableFuture<ResponseEntity<AuthRenewResDto>> completableFuture = apiTransactionExecutor.execute(
                    createRenewTransactionRequest(
                            authTokenRenewReqDto,
                            mgmtsQueryService.findDomainByOrgCode(authTokenRenewReqDto.getOrgCode())
                    ),
                    AuthRenewResDto.class
            );

            ResponseEntity<AuthRenewResDto> responseEntity = completableFuture.get();
            AuthRenewResDto authRenewResDto = responseEntity.getBody();
            mydataAuthBaseManagementService.renew(
                    mydataAuthBaseEntity,
                    authRenewResDto
            );
            mydataTranReqPtclManagementService.requestAndSave(
                    mydataAuthBaseEntity.getUserCi(),
                    mydataAuthBaseEntity.getOrgCode(),
                    authRenewResDto.getAccessToken()
            );
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("[renew] RestTemplate execute fail.");
        }
    }

    private void setAuthTokenRenewReqData(AuthTokenRenewReqDto authTokenRenewReqDto, MydataAuthBaseEntity mydataAuthBaseEntity) {
        authTokenRenewReqDto.setGrantType("refresh_token");
        authTokenRenewReqDto.setRefreshToken(mydataAuthBaseEntity.getRefreshToken());
        authTokenRenewReqDto.setClientId(clientId);
        authTokenRenewReqDto.setClientSecret(clientSecret);
    }

    private ApiTransactionRequest<AuthTokenRenewReqDto> createRenewTransactionRequest(
            AuthTokenRenewReqDto authTokenRenewReqDto,
            String domain) {
        return new ApiTransactionRequest<>(
                authTokenRenewReqDto,
                new HttpHeaders(),
                HttpMethod.POST,
                new ApiDomainCode(domain, ApiType.API_TYPE_AU03),
                null
        );
    }

    @Transactional
    @Override
    public void revoke(Long userId, AuthTokenRevokeReqDto authTokenRevokeReqDto) {
        MydataUserEntity mydataUserEntity = mydataUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("This user is not registered MyData Service [userId: %d]", userId)));

        MydataAuthBaseEntity mydataAuthBaseEntity = mydataAuthBaseManagementService
                .findByUserIdAndOrgCode(mydataUserEntity.getUserCi(), authTokenRevokeReqDto.getOrgCode())
                .orElseThrow(() -> new EntityNotFoundException(String.format("This orgCode is not exists [orgCode: %s]", authTokenRevokeReqDto.getOrgCode())));

        authTokenRevokeReqDto.setToken(mydataAuthBaseEntity.getAccessToken());
        authTokenRevokeReqDto.setClientId(clientId);
        authTokenRevokeReqDto.setClientSecret(clientSecret);

        try {
            mydataAuthBaseManagementService.deleteByAccessToken(authTokenRevokeReqDto.getToken());
            mydataTranReqPtclManagementService.delete(mydataAuthBaseEntity.getUserCi(), mydataAuthBaseEntity.getOrgCode());

            ApiTransactionRequest<AuthTokenRevokeReqDto> requestEntity = createRevokeRequestEntity(
                    authTokenRevokeReqDto,
                    mgmtsQueryService.findDomainByOrgCode(authTokenRevokeReqDto.getOrgCode())
            );

            CompletableFuture<ResponseEntity<IndividualAuthRevokeResDto>> completableFuture =
                    apiTransactionExecutor.execute(requestEntity, IndividualAuthRevokeResDto.class);

            ResponseEntity<IndividualAuthRevokeResDto> responseEntity = completableFuture.get();

            IndividualAuthRevokeResDto result = responseEntity.getBody();

        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("[revoke] RestTemplate execute fail.");
        }
    }

    private ApiTransactionRequest<AuthTokenRevokeReqDto> createRevokeRequestEntity(AuthTokenRevokeReqDto authTokenRevokeReqDto, String domain) {
        return new ApiTransactionRequest<>(
                authTokenRevokeReqDto,
                HttpMethod.POST,
                new ApiDomainCode(domain, ApiType.API_TYPE_AU04),
                null
        );
    }
}

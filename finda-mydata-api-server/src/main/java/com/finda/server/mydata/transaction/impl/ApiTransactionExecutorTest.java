package com.finda.server.mydata.transaction.impl;

import com.finda.server.mydata.common.ApiTranIdGenerator;
import com.finda.server.mydata.transaction.ApiTransactionExecutor;
import com.finda.server.mydata.transaction.ApiTransactionRequest;
import com.finda.server.mydata.transaction.domain.ApiTranHistEntity;
import com.finda.server.mydata.transaction.domain.repository.ApiTranHistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

import static com.finda.server.mydata.auth.AuthConstants.X_API_TRAN_ID;

@RequiredArgsConstructor
@Profile({"dev", "stg"})
@Component
public class ApiTransactionExecutorTest implements ApiTransactionExecutor {
    private static final String RSP_CODE = "rspCode";
    private static final String RSP_MSG = "rspMsg";
    private static final String TX_ID = "txId";

    private final RestTemplate restTemplate;
    private final ApiTranIdGenerator apiTranIdGenerator;
    private final ApiTranHistRepository apiTranHistRepository;


    @Override
    public <T, R> CompletableFuture<ResponseEntity<R>> execute(ApiTransactionRequest<T> apiTransactionRequest, Class<R> responseType) {
        String apiTranId = apiTranIdGenerator.issue();
        apiTransactionRequest.addHeader(X_API_TRAN_ID, apiTranId);

        long startTime = System.currentTimeMillis();
        ResponseEntity<R> response = restTemplate.exchange(
                apiTransactionRequest.toRequestEntity(),
                responseType
        );

        long responseTime = System.currentTimeMillis() - startTime;
        R responseBody = response.getBody();

        apiTranHistRepository.save(
                ApiTranHistEntity.builder()
                        .apiTranId(apiTransactionRequest.getHeaderFirst(X_API_TRAN_ID))
                        .txId(apiTransactionRequest.reflectFieldFromBody(TX_ID))
                        .orgCode(apiTransactionRequest.getOrgCode())
                        .type(apiTransactionRequest.isScheduledRequest() ? 1 : 2)
                        .apiDomainCode(apiTransactionRequest.getApiDomainCode())
                        .responseTime(responseTime)
                        .method(apiTransactionRequest.getMethod())
                        .statusCode(response.getStatusCodeValue())
                        .rspCode(reflectField(responseBody, RSP_CODE))
                        .rspMsg(reflectField(responseBody, RSP_MSG))
                        .build()
        );
        return CompletableFuture.completedFuture(response);
    }

    @Override
    public <T, R> CompletableFuture<ResponseEntity<R>> execute(@Nullable String userCi, ApiTransactionRequest<T> apiTransactionRequest, Class<R> responseType) {
        String apiTranId = apiTranIdGenerator.issue();
        apiTransactionRequest.addHeader(X_API_TRAN_ID, apiTranId);

        long startTime = System.currentTimeMillis();
        ResponseEntity<R> response = getExchange(apiTransactionRequest, responseType);
        long responseTime = System.currentTimeMillis() - startTime;
        R responseBody = response.getBody();

        apiTranHistRepository.save(
                ApiTranHistEntity.builder()
                        .userCi(userCi)
                        .apiTranId(apiTransactionRequest.getHeaderFirst(X_API_TRAN_ID))
                        .txId(apiTransactionRequest.reflectFieldFromBody(TX_ID))
                        .orgCode(apiTransactionRequest.getOrgCode())
                        .type(apiTransactionRequest.isScheduledRequest() ? 1 : 2)
                        .apiDomainCode(apiTransactionRequest.getApiDomainCode())
                        .responseTime(responseTime)
                        .method(apiTransactionRequest.getMethod())
                        .statusCode(response.getStatusCodeValue())
                        .rspCode(reflectField(responseBody, RSP_CODE))
                        .rspMsg(reflectField(responseBody, RSP_MSG))
                        .build()
        );
        return CompletableFuture.completedFuture(response);
    }

    @Override
    public <T, R> CompletableFuture<List<ResponseEntity<R>>> execute(List<ApiTransactionRequest<T>> apiTransactionRequests, Class<R> responseType) {
        List<ResponseEntity<R>> results = new ArrayList<>();
        List<ApiTranHistEntity> apiTranHistEntities = new ArrayList<>();

        for (ApiTransactionRequest<T> apiTransactionRequest : apiTransactionRequests) {
            long startTime = System.currentTimeMillis();
            ResponseEntity<R> response = getExchange(apiTransactionRequest, responseType);
            long responseTime = System.currentTimeMillis() - startTime;
            R responseBody = response.getBody();

            apiTranHistEntities.add(
                    ApiTranHistEntity.builder()
                            .apiTranId(apiTransactionRequest.getHeaderFirst(X_API_TRAN_ID))
                            .txId(apiTransactionRequest.reflectFieldFromBody(TX_ID))
                            .orgCode(apiTransactionRequest.getOrgCode())
                            .type(apiTransactionRequest.isScheduledRequest() ? 1 : 2)
                            .apiDomainCode(apiTransactionRequest.getApiDomainCode())
                            .responseTime(responseTime)
                            .method(apiTransactionRequest.getMethod())
                            .statusCode(response.getStatusCodeValue())
                            .rspCode(reflectField(responseBody, RSP_CODE))
                            .rspMsg(reflectField(responseBody, RSP_MSG))
                            .build()
            );
            results.add(response);
        }

        apiTranHistRepository.saveAll(apiTranHistEntities);
        return CompletableFuture.completedFuture(results);
    }

    @Override
    public <T, R> CompletableFuture<List<ResponseEntity<R>>> execute(@Nullable String userCi, List<ApiTransactionRequest<T>> apiTransactionRequests, Class<R> responseType) {
        List<ResponseEntity<R>> results = new ArrayList<>();
        List<ApiTranHistEntity> apiTranHistEntities = new ArrayList<>();

        for (ApiTransactionRequest<T> apiTransactionRequest : apiTransactionRequests) {
            long startTime = System.currentTimeMillis();
            ResponseEntity<R> response = getExchange(apiTransactionRequest, responseType);
            long responseTime = System.currentTimeMillis() - startTime;
            R responseBody = response.getBody();

            apiTranHistEntities.add(
                    ApiTranHistEntity.builder()
                            .userCi(userCi)
                            .apiTranId(apiTransactionRequest.getHeaderFirst(X_API_TRAN_ID))
                            .txId(apiTransactionRequest.reflectFieldFromBody(TX_ID))
                            .orgCode(apiTransactionRequest.getOrgCode())
                            .type(apiTransactionRequest.isScheduledRequest() ? 1 : 2)
                            .apiDomainCode(apiTransactionRequest.getApiDomainCode())
                            .responseTime(responseTime)
                            .method(apiTransactionRequest.getMethod())
                            .statusCode(response.getStatusCodeValue())
                            .rspCode(reflectField(responseBody, RSP_CODE))
                            .rspMsg(reflectField(responseBody, RSP_MSG))
                            .build()
            );
            results.add(response);
        }

        apiTranHistRepository.saveAll(apiTranHistEntities);
        return CompletableFuture.completedFuture(results);
    }

    private <T, R> ResponseEntity<R> getExchange(ApiTransactionRequest<T> apiTransactionRequest, Class<R> responseType) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextLong(5000L, 15000L));
            Constructor<R> constructor = responseType.getConstructor();
            return new ResponseEntity<>(constructor.newInstance(), HttpStatus.OK);
        } catch (InterruptedException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    private <T> String reflectField(T body, String fieldName) {
        if (Objects.isNull(body)) {
            return null;
        }
        Class<?> bodyClass = body.getClass();
        try {
            Field declaredField = bodyClass.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            Object o = declaredField.get(body);

            if (o instanceof String) {
                return (String) o;
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
        return null;
    }
}

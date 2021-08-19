package com.finda.server.mydata.transaction;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ApiTransactionExecutor {
    <T, R> CompletableFuture<ResponseEntity<R>> execute(ApiTransactionRequest<T> apiTransactionRequest,
                                                        Class<R> responseType);

    <T, R> CompletableFuture<ResponseEntity<R>> execute(@Nullable String userCi,
                                                        ApiTransactionRequest<T> apiTransactionRequest,
                                                        Class<R> responseType);

    <T, R> CompletableFuture<List<ResponseEntity<R>>> execute(List<ApiTransactionRequest<T>> apiTransactionRequests,
                                                              Class<R> responseType);

    <T, R> CompletableFuture<List<ResponseEntity<R>>> execute(@Nullable String userCi,
                                                              List<ApiTransactionRequest<T>> apiTransactionRequests,
                                                              Class<R> responseType);
}

package com.finda.server.mydata.auth.domain.repository;

import com.finda.server.mydata.auth.domain.entity.IntegrationAuthProgressEntity;

import java.util.Optional;

public interface IntegrationAuthProgressCustomRepository {
    Optional<IntegrationAuthProgressEntity> findByIdFetchTaskAndAuthBase(Long progressId);
}

package com.finda.server.mydata.auth.domain.repository;

import com.finda.server.mydata.auth.domain.entity.IntegrationAuthProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntegrationAuthProgressRepository extends
        JpaRepository<IntegrationAuthProgressEntity, Long>, IntegrationAuthProgressCustomRepository {
}

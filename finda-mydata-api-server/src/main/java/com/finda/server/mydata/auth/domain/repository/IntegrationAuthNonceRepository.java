package com.finda.server.mydata.auth.domain.repository;

import com.finda.server.mydata.auth.domain.entity.IntegrationAuthNonceEntity;
import com.finda.server.mydata.auth.domain.entity.id.IntegrationAuthNonceId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IntegrationAuthNonceRepository extends JpaRepository<IntegrationAuthNonceEntity, IntegrationAuthNonceId> {
    List<IntegrationAuthNonceEntity> findAllBySession(String session);
}

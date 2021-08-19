package com.finda.server.mydata.auth.domain.repository;

import com.finda.server.mydata.auth.domain.entity.MydataAuthBaseEntity;
import com.finda.server.mydata.auth.domain.entity.id.MydataAuthBaseId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MydataAuthBaseRepository extends
        JpaRepository<MydataAuthBaseEntity, MydataAuthBaseId>,
        MydataAuthBaseCustomRepository {
    Optional<MydataAuthBaseEntity> findByUserCiAndOrgCode(String userCi, String orgCode);

    Optional<MydataAuthBaseEntity> findByAccessToken(String accessToken);

    Optional<MydataAuthBaseEntity> findByRefreshToken(String refreshToken);
}

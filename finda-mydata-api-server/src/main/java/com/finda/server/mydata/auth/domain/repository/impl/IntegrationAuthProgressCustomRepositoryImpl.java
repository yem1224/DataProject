package com.finda.server.mydata.auth.domain.repository.impl;

import com.finda.server.mydata.auth.domain.entity.IntegrationAuthProgressEntity;
import com.finda.server.mydata.auth.domain.repository.IntegrationAuthProgressCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.finda.server.mydata.auth.domain.entity.QIntegrationAuthProgressEntity.integrationAuthProgressEntity;
import static com.finda.server.mydata.auth.domain.entity.QIntegrationAuthTaskEntity.integrationAuthTaskEntity;

@RequiredArgsConstructor
public class IntegrationAuthProgressCustomRepositoryImpl implements IntegrationAuthProgressCustomRepository {
    private final JPAQueryFactory query;

    @Override
    public Optional<IntegrationAuthProgressEntity> findByIdFetchTaskAndAuthBase(Long progressId) {
        IntegrationAuthProgressEntity result = query
                .selectDistinct(integrationAuthProgressEntity)
                .from(integrationAuthProgressEntity)
                .leftJoin(integrationAuthProgressEntity.integrationAuthTaskEntities, integrationAuthTaskEntity).fetchJoin()
                .where(integrationAuthProgressEntity.id.eq(progressId))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}

package com.finda.server.mydata.auth.domain.repository.impl;

import com.finda.server.mydata.auth.domain.entity.MydataAuthBaseEntity;
import com.finda.server.mydata.auth.domain.repository.MydataAuthBaseCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.finda.server.mydata.auth.domain.entity.QMydataAuthBaseEntity.mydataAuthBaseEntity;

@RequiredArgsConstructor
public class MydataAuthBaseCustomRepositoryImpl implements MydataAuthBaseCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MydataAuthBaseEntity> findAllByUserCiAndOrgCodes(String userCi, List<String> orgCodes) {
        return jpaQueryFactory.select(mydataAuthBaseEntity)
                .from(mydataAuthBaseEntity)
                .where(
                        mydataAuthBaseEntity.userCi.eq(userCi),
                        mydataAuthBaseEntity.orgCode.in(orgCodes)
                )
                .fetch();
    }
}

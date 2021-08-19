package com.finda.server.mydata.mgmts.domain.repository.impl;

import com.finda.server.mydata.mgmts.domain.repository.MgmtsOrgCustomRepository;
import com.finda.server.mydata.mgmts.dto.OrgInfoDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.finda.server.mydata.mgmts.domain.entity.QOrgEntity.orgEntity;

@RequiredArgsConstructor
public class MgmtsOrgCustomRepositoryImpl implements MgmtsOrgCustomRepository {

    private final JPAQueryFactory query;

    @Override
    public List<OrgInfoDto> findOrgInfosByOrgCodes(List<String> orgCodes) {
        return query.from(orgEntity)
                .select(Projections.constructor(
                        OrgInfoDto.class,
                        orgEntity.orgCode,
                        orgEntity.orgType,
                        orgEntity.orgName,
                        orgEntity.domain,
                        orgEntity.relayOrgCode,
                        orgEntity.industry
                        )
                ).where(orgEntity.orgCode.in(orgCodes))
                .fetch();
    }

    @Override
    public Optional<OrgInfoDto> findOrgInfoByOrgCode(String orgCode) {
        OrgInfoDto orgInfoDto = query.from(orgEntity)
                .select(Projections.constructor(
                        OrgInfoDto.class,
                        orgEntity.orgCode,
                        orgEntity.orgType,
                        orgEntity.orgName,
                        orgEntity.domain,
                        orgEntity.relayOrgCode,
                        orgEntity.industry
                ))
                .where(orgEntity.orgCode.eq(orgCode))
                .fetchOne();

        return Optional.ofNullable(orgInfoDto);
    }

    @Override
    public Optional<String> findOrgCodeByCertIssuerDn(String certIssuerDn) {
        String orgCode = query.from(orgEntity)
                .select(orgEntity.orgCode)
                .where(orgEntity.certIssuerDn.eq(certIssuerDn))
                .fetchOne();
        return Optional.ofNullable(orgCode);
    }
}

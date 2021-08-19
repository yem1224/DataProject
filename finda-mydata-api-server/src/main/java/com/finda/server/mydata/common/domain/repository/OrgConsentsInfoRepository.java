package com.finda.server.mydata.common.domain.repository;

import com.finda.server.mydata.common.domain.entity.OrgConsentsInfoEntity;
import com.finda.server.mydata.common.domain.entity.id.OrgConsentsInfoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgConsentsInfoRepository extends JpaRepository<OrgConsentsInfoEntity, OrgConsentsInfoId> {
    @Query("select u from OrgConsentsInfoEntity u where u.orgConsentsInfoId.orgCode = ?1 and u.orgConsentsInfoId.userId = ?2")
    OrgConsentsInfoEntity findByUnikey(String orgCode, Long authUserId);
}

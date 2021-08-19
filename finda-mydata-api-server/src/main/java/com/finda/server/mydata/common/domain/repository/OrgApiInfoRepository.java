package com.finda.server.mydata.common.domain.repository;

import com.finda.server.mydata.common.domain.entity.OrgApiInfoEntity;
import com.finda.server.mydata.common.domain.entity.id.OrgApiInfoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgApiInfoRepository extends JpaRepository<OrgApiInfoEntity, OrgApiInfoId> {
    @Query("select u from OrgApiInfoEntity u where u.orgApiInfoId.orgCode = ?1 and u.orgApiInfoId.apiCode = ?2")
    OrgApiInfoEntity findUriByApiCode(String orgCode, String apiCode);
}

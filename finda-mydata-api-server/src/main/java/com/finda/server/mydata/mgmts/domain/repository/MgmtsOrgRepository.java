package com.finda.server.mydata.mgmts.domain.repository;

import com.finda.server.mydata.mgmts.domain.entity.OrgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MgmtsOrgRepository extends JpaRepository<OrgEntity, String>, MgmtsOrgCustomRepository {
    @Query("select u from OrgEntity u where u.orgCode = ?1")
    OrgEntity findByUniqueKey(String orgCode);

    Optional<OrgEntity> findOrgEntityByOrgCode(String orgCode);
}

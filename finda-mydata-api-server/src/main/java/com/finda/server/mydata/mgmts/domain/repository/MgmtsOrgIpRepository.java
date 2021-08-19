package com.finda.server.mydata.mgmts.domain.repository;

import com.finda.server.mydata.mgmts.domain.entity.OrgIpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MgmtsOrgIpRepository extends JpaRepository<OrgIpEntity, Long> {
}

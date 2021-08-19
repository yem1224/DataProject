package com.finda.server.mydata.mgmts.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finda.server.mydata.mgmts.domain.entity.MgmtTokenEntity;

@Repository
public interface MgmtTokenRepository extends JpaRepository<MgmtTokenEntity, String> {

	public MgmtTokenEntity findAccessTokenByOrderByInsertTimeDesc();
}

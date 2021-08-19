package com.finda.server.mydata.auth.domain.repository;

import com.finda.server.mydata.auth.domain.entity.MydataUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MydataUserRepository extends JpaRepository<MydataUserEntity, Long> {
	MydataUserEntity findByUserCi(String userCi);
}

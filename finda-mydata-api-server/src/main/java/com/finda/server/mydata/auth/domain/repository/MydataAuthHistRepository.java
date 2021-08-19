package com.finda.server.mydata.auth.domain.repository;

import com.finda.server.mydata.auth.domain.entity.MydataAuthBaseHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MydataAuthHistRepository extends JpaRepository<MydataAuthBaseHistoryEntity, Long> {
}

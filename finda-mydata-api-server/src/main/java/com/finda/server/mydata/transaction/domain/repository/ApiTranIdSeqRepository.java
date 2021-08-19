package com.finda.server.mydata.transaction.domain.repository;

import com.finda.server.mydata.transaction.domain.ApiTranIdSeqEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiTranIdSeqRepository extends JpaRepository<ApiTranIdSeqEntity, Long> {
}

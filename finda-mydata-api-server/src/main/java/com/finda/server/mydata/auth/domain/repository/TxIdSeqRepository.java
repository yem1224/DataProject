package com.finda.server.mydata.auth.domain.repository;

import com.finda.server.mydata.auth.domain.entity.TxIdSeqEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TxIdSeqRepository extends JpaRepository<TxIdSeqEntity, Long> {
}

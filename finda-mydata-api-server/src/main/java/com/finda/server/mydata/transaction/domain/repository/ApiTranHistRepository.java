package com.finda.server.mydata.transaction.domain.repository;

import com.finda.server.mydata.transaction.domain.ApiTranHistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ApiTranHistRepository extends JpaRepository<ApiTranHistEntity, Long> {

    @Query("select h from ApiTranHistEntity h where h.orgCode is not null and h.apiDomainCode.apiCode is not null and h.type = :type and (h.insertTime between :start and :end)")
    List<ApiTranHistEntity> findAllByOrgCodeIsNotNullAndApiCodeIsNotNullAndInsertTimeBetween(int type, LocalDateTime start, LocalDateTime end);
}

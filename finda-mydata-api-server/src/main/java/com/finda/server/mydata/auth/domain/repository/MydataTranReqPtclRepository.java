package com.finda.server.mydata.auth.domain.repository;

import com.finda.server.mydata.auth.domain.entity.MydataTranReqPtclEntity;
import com.finda.server.mydata.auth.domain.entity.id.MydataTranReqPtclId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MydataTranReqPtclRepository extends JpaRepository<MydataTranReqPtclEntity, MydataTranReqPtclId> {
    Optional<MydataTranReqPtclEntity> findByUserCiAndOrgCode(String userCi, String orgCode);

    List<MydataTranReqPtclEntity> findAllByUserCi(String userCi);

    @Query("select h from ApiTranHistEntity h where h.orgCode is not null and h.apiDomainCode.apiCode is not null and h.type = :type and (h.insertTime between :start and :end)")
    List<MydataTranReqPtclEntity> findAllByOrgCodeAndDeleteYnIsFalseAndInsertTimeBetween(int type, LocalDateTime start, LocalDateTime end);
}

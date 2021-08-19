package com.finda.server.mydata.auth.domain.repository;

import com.finda.server.mydata.auth.domain.entity.MydataTranReqPtclHistoryEntity;
import com.finda.server.mydata.auth.domain.entity.id.MydataTranReqPtclHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MydataTranReqPtclHistoryRepository
        extends JpaRepository<MydataTranReqPtclHistoryEntity, MydataTranReqPtclHistoryId> {

    @Query("select count(h.seqNo) from MydataTranReqPtclHistoryEntity h where h.userCi = :userCi and h.orgCode = :orgCode")
    Long findLastSeqNoByCiAndOrgCode(String userCi, String orgCode);
}

package com.finda.server.mydata.bank.domain.repository;

import com.finda.server.mydata.bank.domain.entity.LoanIntPtclEntity;
import com.finda.server.mydata.bank.domain.entity.id.LoanIntPtclId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoanIntPtclRepository extends JpaRepository<LoanIntPtclEntity, LoanIntPtclId> {
    @Query("select u from LoanIntPtclEntity u where u.loanIntPtclId.accountNum = ?1 and u.loanIntPtclId.seqno = ?2")
    List<LoanIntPtclEntity> findByAcctInfo(String acctNum, String seqNo);
}

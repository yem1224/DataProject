package com.finda.server.mydata.bank.domain.repository;

import com.finda.server.mydata.bank.domain.entity.LoanTranPtclEntity;
import com.finda.server.mydata.bank.domain.entity.id.LoanTranPtclId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoanTranPtclRepository extends JpaRepository<LoanTranPtclEntity, LoanTranPtclId> {
    @Query("select u from LoanTranPtclEntity u where u.loanTranPtclId.accountNum = ?1 and u.loanTranPtclId.seqno = ?2")
    List<LoanTranPtclEntity> findByAcctInfo(String acctNum, String seqNo);
}

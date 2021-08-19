package com.finda.server.mydata.bank.domain.repository;

import com.finda.server.mydata.bank.domain.entity.LoanBaseEntity;
import com.finda.server.mydata.bank.domain.entity.id.LoanBaseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LoanBaseRepository extends JpaRepository<LoanBaseEntity, LoanBaseId> {
    @Query("select u from LoanBaseEntity u where u.loanBaseId.accountNum = ?1 and u.loanBaseId.seqno = ?2 and u.loanBaseId.userId = ?3")
    LoanBaseEntity findByUniKey(String acctNum, String seqNo, long userId);
}

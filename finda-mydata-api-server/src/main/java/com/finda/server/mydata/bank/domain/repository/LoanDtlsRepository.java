package com.finda.server.mydata.bank.domain.repository;

import com.finda.server.mydata.bank.domain.entity.LoanDtlsEntity;
import com.finda.server.mydata.bank.domain.entity.id.LoanDtlsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LoanDtlsRepository extends JpaRepository<LoanDtlsEntity, LoanDtlsId> {
    @Query("select u from LoanDtlsEntity u where u.loanDtlsId.accountNum = ?1 and u.loanDtlsId.seqno = ?2")
    LoanDtlsEntity findByUniKey(String acctNum, String seqNo);
}

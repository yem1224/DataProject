package com.finda.server.mydata.bank.domain.repository;

import com.finda.server.mydata.bank.domain.entity.AccountBaseEntity;
import com.finda.server.mydata.bank.domain.entity.id.AccountBaseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountBaseRepository extends JpaRepository<AccountBaseEntity, AccountBaseId> {
    @Query("select u from AccountBaseEntity u where u.accountBaseId.accountNum = ?1 and u.accountBaseId.seqno = ?2 and u.accountBaseId.userId = ?3")
    AccountBaseEntity findByUniKey(String accountNum,String seqno,long userId );
}

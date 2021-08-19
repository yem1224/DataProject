package com.finda.server.mydata.auth.service;

import com.finda.server.mydata.auth.domain.entity.TxIdSeqEntity;
import com.finda.server.mydata.auth.domain.repository.TxIdSeqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
class TxIdSeqGenerator {
    private final TxIdSeqRepository txIdSeqRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String generate() {
        TxIdSeqEntity txIdSeqEntity = TxIdSeqEntity.next();
        txIdSeqRepository.save(txIdSeqEntity);
        return String.format("%012d", txIdSeqEntity.get());
    }
}



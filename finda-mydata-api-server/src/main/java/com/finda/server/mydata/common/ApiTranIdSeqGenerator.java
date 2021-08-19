package com.finda.server.mydata.common;

import com.finda.server.mydata.transaction.domain.ApiTranIdSeqEntity;
import com.finda.server.mydata.transaction.domain.repository.ApiTranIdSeqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
class ApiTranIdSeqGenerator {
    private final ApiTranIdSeqRepository apiTranIdSeqRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String generate() {
        ApiTranIdSeqEntity apiTranIdSeqEntity = ApiTranIdSeqEntity.next();
        apiTranIdSeqRepository.save(apiTranIdSeqEntity);
        return String.format("%09d", apiTranIdSeqEntity.get());
    }
}

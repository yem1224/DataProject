package com.finda.server.mydata.auth.domain.entity;

import com.finda.server.mydata.common.domain.entity.AuditingBaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 통합인증 절차의 트랜잭션 Id 일련번호.
 * 일련 번호는 단순 순차 증가한다.
 * 관련 자료는 아래를 참조한다.
 *
 * 마이데이터 통합인증 절차 및 규격안 첨부 5
 * https://findainc.atlassian.net/wiki/spaces/finda/pages/2136113225/ID%2Btx%2Bid
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "mydata_tx_id_seq")
@Entity
public class TxIdSeqEntity extends AuditingBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public static TxIdSeqEntity next() {
        return new TxIdSeqEntity();
    }

    public Long get() {
        return id;
    }
}

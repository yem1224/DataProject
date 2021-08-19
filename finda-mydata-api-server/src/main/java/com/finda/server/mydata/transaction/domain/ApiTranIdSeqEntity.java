package com.finda.server.mydata.transaction.domain;

import com.finda.server.mydata.common.domain.entity.AuditingBaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 마이데이터 API 통신 시, 부여하는 거래고유번호의 부여번호.
 * 부여번호는 단순 순차 증가한다.
 * 관련 자료는 아래를 참조한다.
 *
 * 마이데이터 표준 API 규격 첨부 14
 * https://findainc.atlassian.net/wiki/spaces/finda/pages/2135949454
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "mydata_api_tran_id_seq")
@Entity
public class ApiTranIdSeqEntity extends AuditingBaseEntity { // .. ApiTranIdEntity ( 항목참고 )

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public static ApiTranIdSeqEntity next() {
        return new ApiTranIdSeqEntity();
    }

    public Long get() {
        return id;
    }
}

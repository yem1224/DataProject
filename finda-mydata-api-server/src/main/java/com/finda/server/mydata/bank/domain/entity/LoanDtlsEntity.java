package com.finda.server.mydata.bank.domain.entity;

import com.finda.server.mydata.bank.domain.entity.id.LoanDtlsId;
import com.finda.server.mydata.common.domain.entity.AuditingBaseEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @since 2021. 06. 17
 * 대출추가정보 entity
 * 은행, 할부금융, 보험 대출 추가정보 테이블
 */
@Data
@Table(name = "mydata_loan_dtls")
@Entity
public class LoanDtlsEntity extends AuditingBaseEntity {

    @EmbeddedId
    LoanDtlsId loanDtlsId;

    private String industry; // 대출업권

    @Column(name = "org_code")
    private String orgCode; // 기관코드

    @Column(name = "org_name")
    private String orgName; // 기관명

    @Column(name = "currency_code")
    private String currencyCode; // 통화코드

    @Column(name = "balance_amt")
    private float balanceAmt; // 대출잔액

    @Column(name = "loan_principal")
    private float loanPrincipal; // 대출원금

    @Column(name = "next_repay_date")
    private String nextRepayDate; // 다음이자상환일

}

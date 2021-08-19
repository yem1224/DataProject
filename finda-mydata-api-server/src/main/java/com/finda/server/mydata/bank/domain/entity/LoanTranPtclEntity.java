package com.finda.server.mydata.bank.domain.entity;

import com.finda.server.mydata.bank.domain.entity.id.LoanTranPtclId;
import com.finda.server.mydata.common.domain.entity.AuditingBaseEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @since 2021. 06. 17
 * 대출거래내역 entity
 * 은행, 할부금융, 보험 대출거래내역 테이블
 */
@Data
@Table(name = "mydata_loan_tran_ptcl")
@Entity
public class LoanTranPtclEntity extends AuditingBaseEntity {

    @EmbeddedId
    LoanTranPtclId loanTranPtclId;

    @Column(name = "org_code")
    private String orgCode; // 기관코드

    @Column(name = "org_name")
    private String orgName; // 기관명

    @Column(name = "trans_dtime")
    private LocalDateTime transDtime; // 거래일시

    @Column(name = "trans_no")
    private String transNo; // 거래번호

    @Column(name = "trans_type")
    private String transType; //거래유형

    @Column(name = "currency_code")
    private String currencyCode; // 통화코드

    @Column(name = "trans_amt")
    private float transAmt; //거래금액

    @Column(name = "balance_amt")
    private float balanceAmt; // 거래 후 대출잔액

    @Column(name = "principal_amt")
    private float principalAmt; // 거래금액 중 원금

    @Column(name = "int_amt")
    private float intAmt; // 거래금액 중 이자

    @Column(name = "loan_paid_amt")
    private float loanPaidAmt; // 대출원금상환액

    @Column(name = "int_paid_amt")
    private float intPaidAmt; // 이자납입액

    @Column(name = "ret_int_amt")
    private float retIntAmt; // 환출이자
}


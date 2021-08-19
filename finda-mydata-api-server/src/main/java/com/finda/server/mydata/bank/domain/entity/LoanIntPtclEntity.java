package com.finda.server.mydata.bank.domain.entity;

import com.finda.server.mydata.bank.domain.entity.id.LoanIntPtclId;
import com.finda.server.mydata.common.domain.entity.AuditingBaseEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @since 2021. 06. 17
 * 대출이자내역 entity
 * 은행, 할부금융, 보험 대출 이자내역 테이블
 */
@Data
@Table(name = "mydata_loan_int_ptcl")
@Entity
public class LoanIntPtclEntity extends AuditingBaseEntity {

    @EmbeddedId
    LoanIntPtclId loanIntPtclId;

    @Column(name = "int_start_date")
    private String intStartDate; // 이자적용시작일

    @Column(name = "int_end_date")
    private String intEndDate; // 이자적용종료일

    @Column(name = "int_rate")
    private float intRate; // 적용이율

    @Column(name = "applied_int_amt")
    private float appliedIntAmt; // 이자금액

    @Column(name = "int_type")
    private String intType; // 이자종류코드

    @Column(name = "int_type_nm")
    private String intTypeNm; // 이자종류
}

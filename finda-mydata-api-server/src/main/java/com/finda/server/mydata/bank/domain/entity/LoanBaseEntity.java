package com.finda.server.mydata.bank.domain.entity;

import com.finda.server.mydata.bank.domain.entity.id.LoanBaseId;
import com.finda.server.mydata.common.domain.entity.AuditingBaseEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

/**
 * @since 2021. 06. 17
 * 대출기본정보 entity
 * 은행, 할부금융, 보험 대출기본정보 테이블
 */
@Data
@Table(name = "mydata_loan_base")
@Entity
public class LoanBaseEntity extends AuditingBaseEntity {

    @EmbeddedId
    LoanBaseId loanBaseId;

    private String industry; // 대출업권

    @Column(name = "org_code")
    private String orgCode; // 기관코드

    @Column(name = "org_name")
    private String orgName; // 기관명

    @Column(name = "issue_date")
    private String issueDate; // 대출일

    @Column(name = "exp_date")
    private String expDate; // 만기일

    @Column(name = "last_offered_rate")
    private float lastOfferedRate; //최종적용금리

    @Column(name = "repay_date")
    private String repayDate; // 월상환일

    @Column(name = "repay_method")
    private String repayMethod; // 상환방식코드

    @Column(name = "repay_method_nm")
    private String repayMethodNm; // 싱환방식명

    @Column(name = "repay_org_code")
    private String repayOrgCode; // 자동이체기관코드

    @Column(name = "repay_org_nm")
    private String repayOrgNm; //지동이체기관명

    @Column(name = "repay_account_num")
    private String repayAccountNum; //상환계좌번호

    @Column(name = "insu_num")
    private String insuNum; // 증권번호
}

package com.finda.server.mydata.bank.domain.entity;

import com.finda.server.mydata.bank.domain.entity.id.AccountBaseId;
import com.finda.server.mydata.common.code.AccountStatus;
import com.finda.server.mydata.common.code.AccountType;
import com.finda.server.mydata.common.domain.entity.AuditingBaseEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @since 2021. 06. 17
 * 계좌기본 entity
 * 업권별 계좌 공통 테이블
 */
@Data
@Table(name = "mydata_account_base")
@Entity
public class AccountBaseEntity extends AuditingBaseEntity {

    @EmbeddedId
    AccountBaseId accountBaseId;

    private String ci; // ci

    @Column(name = "org_code")
    private String orgCode; // 기관코드

    @Column(name = "org_name")
    private String orgName; // 기관명

    @Column(name = "is_consent")
    private boolean isConsent; // 전송요구여부

    @Column(name = "is_foreign_deposit")
    private boolean isForeignDeposit; // 외화계좌여부

    @Column(name = "prod_name")
    private String prodName; //상품명

    @Column(name = "is_minus")
    private boolean isMinus; // 마이너스 약정 여부

    @Column(name = "account_type")
    private String accountType; // 계좌구분코드

    @Column(name = "account_type_nm")
    private String accountTypeNm; // 계좌구분명

    @Column(name = "account_status")
    private String accountStatus; // 계좌상태코드

    @Column(name = "account_status_nm")
    private String accountStatusNm; // 계좌상태명
}

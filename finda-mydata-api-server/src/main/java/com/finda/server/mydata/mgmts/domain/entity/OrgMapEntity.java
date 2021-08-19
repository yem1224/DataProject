package com.finda.server.mydata.mgmts.domain.entity;

import com.finda.server.mydata.auth.domain.entity.AuthDv;
import com.finda.server.mydata.auth.domain.entity.MydataUserEntity;
import com.finda.server.mydata.common.domain.entity.AuditingBaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

/**
 * @since 2021. 06. 30
 * 기관 매핑 entity
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Data
@Table(name = "mydata_bank_code_map")
public class OrgMapEntity extends AuditingBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "BANK_ID")
    private String BankId;

    @Column(name = "DISPLAY_NAME")
    private String DisplayName;

    @Column(name = "KCB_BANK_NAME1")
    private String KcbBankName1;

    @Column(name = "KCB_BANK_NAME2")
    private String KcbBankName2;

    @Column(name = "KCB_BANK_NAME3")
    private String KcbBankName3;

    @Column(name = "KCB_BANK_NAME4")
    private String KcbBankName4;

    @Column(name = "KFTC_BANK_CODE")
    private String KftcBankCode;

    @Column(name = "BANK_LOGO_URL")
    private String BankLogUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="ORG_CODE",nullable = false)
    private OrgEntity orgEntity;


    @Builder
    public OrgMapEntity(   Long id
                         , OrgEntity orgEntity
                         , String BankId
                         , String DisplayName
                         , String KcbBankName1
                         , String KcbBankName2
                         , String KcbBankName3
                         , String KcbBankName4
                         , String KftcBankCode
                         , String BankLogUrl
                        ){
        this.id = id;
        this.orgEntity = orgEntity;
        this.BankId = BankId;
        this.DisplayName = DisplayName;
        this.KcbBankName1 = KcbBankName1;
        this.KcbBankName2 = KcbBankName2;
        this.KcbBankName3 = KcbBankName3;
        this.KcbBankName4 = KcbBankName4;
        this.KftcBankCode = KftcBankCode;
        this.BankLogUrl   = BankLogUrl;


    }

}

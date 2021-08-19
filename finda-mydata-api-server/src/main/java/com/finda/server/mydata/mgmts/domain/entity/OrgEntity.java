package com.finda.server.mydata.mgmts.domain.entity;

import com.finda.server.mydata.common.domain.entity.AuditingBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @since 2021. 06. 11
 * 기관정보 entity
 */
@Data
@Entity
@Table(name = "mydata_org_info")
public class OrgEntity extends AuditingBaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "org_code")
    private String orgCode; // 기관코드

    @Column(name = "org_type")
    private String orgType; // 기관구분

    @Column(name = "org_name")
    private String orgName; // 기관명

    @Column(name = "org_regno")
    private String orgRegno; // 사업자등록번호

    @Column(name = "corp_regno")
    private String corpRegno; // 법인등록번호

    @Column(name = "serial_num")
    private String serialNum; //TLS인증서 시리얼 넘버

    private String address; // 주소

    private String domain; // 도메인주소

    @Column(name = "relay_org_code")
    private String relayOrgCode; // 중계기관코드

    private String industry; // 업권

    @Column(name = "auth_type")
    private String authType; // 제공 인증방식

    @Column(name = "cert_issuer_dn")
    private String certIssuerDn; //통합인증기관의 DN값

    @Column(name = "cert_oid")
    private String certOid; //허용 통합인증서 OID
}

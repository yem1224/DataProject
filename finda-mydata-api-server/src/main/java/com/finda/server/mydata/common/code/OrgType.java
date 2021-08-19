package com.finda.server.mydata.common.code;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 기관유형 code
 */
@NoArgsConstructor
@Embeddable
public class OrgType {
    /**
     * 01: 정보 제공자(API 자체 구축)
     * 02: 정보 제공자(중계 기관 이용)
     * 03: 마이데이터 사업자
     * 04: 중계 기관
     * 05: 통합 인증 기관(인증서 본인 확인 기관)
     * 06: 통합 인증 기관(전자 서명 인증 사업자)
     * 99: 기타
     */
    @Column(name = "org_type")
    private String orgType;

    public OrgType(String code) {
        this.orgType = code;
    }

    public String getCode() {
        return orgType;
    }
}
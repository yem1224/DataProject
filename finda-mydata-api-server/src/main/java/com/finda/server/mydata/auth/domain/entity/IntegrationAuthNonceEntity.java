package com.finda.server.mydata.auth.domain.entity;

import com.finda.server.mydata.auth.domain.entity.id.IntegrationAuthNonceId;
import com.finda.server.mydata.common.domain.entity.AuditingBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

/**
 * 통합 인증 시, Signed 된 Nonce 값 외에도 Signed 되지 않은 Nonce 값을 정보제공자 측에 함께 제공하기 위해 저장
 * Id는 36길이의 UUID가 저장된다.
 */
@Getter
@NoArgsConstructor
@IdClass(IntegrationAuthNonceId.class)
@Table(name = "mydata_integration_auth_nonce")
@Entity
public class IntegrationAuthNonceEntity extends AuditingBaseEntity {

    @Id
    @Column(name = "session")
    private String session;

    @Id
    @Column(name = "user_ci")
    private String userCi;

    @Id
    @Column(name = "org_code")
    private String orgCode;

    @Column(name = "consent_nonce")
    private String consentNonce;

    @Column(name = "ucpid_nonce")
    private String ucpidNonce;

    @Column(name = "expired")
    private Boolean expired;

    @Builder
    public IntegrationAuthNonceEntity(String session,
                                      String userCi,
                                      String orgCode,
                                      String consentNonce,
                                      String ucpidNonce) {
        this.session = session;
        this.userCi = userCi;
        this.orgCode = orgCode;
        this.consentNonce = consentNonce;
        this.ucpidNonce = ucpidNonce;
        this.expired = Boolean.FALSE;
    }

    public void expire() {
        expired = Boolean.TRUE;
    }

    public boolean isExpired() {
        return expired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntegrationAuthNonceEntity that = (IntegrationAuthNonceEntity) o;
        return Objects.equals(session, that.session) && Objects.equals(userCi, that.userCi) && Objects.equals(orgCode, that.orgCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session, userCi, orgCode);
    }
}

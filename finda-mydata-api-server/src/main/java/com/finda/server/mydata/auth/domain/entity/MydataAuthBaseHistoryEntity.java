package com.finda.server.mydata.auth.domain.entity;

import com.finda.server.mydata.common.domain.entity.AuditingBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "mydata_auth_hist")
@Entity
public class MydataAuthBaseHistoryEntity extends AuditingBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_ci")
    private String userCi;

    @Column(name = "org_code")
    private String orgCode;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "auth_org_code")
    private String authOrgCode;

    @Column(name = "scope")
    private String scope;

    @Builder
    public MydataAuthBaseHistoryEntity(String userCi,
                                       String orgCode,
                                       String refreshToken,
                                       String authOrgCode,
                                       String scope) {
        this.userCi = userCi;
        this.orgCode = orgCode;
        this.refreshToken = refreshToken;
        this.authOrgCode = authOrgCode;
        this.scope = scope;
    }
}

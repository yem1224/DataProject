package com.finda.server.mydata.auth.domain.entity;

import com.finda.server.mydata.common.domain.entity.AuditingBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "mydata_integration_auth_task")
@Entity
public class IntegrationAuthTaskEntity extends AuditingBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "integration_auth_progress_id")
    private IntegrationAuthProgressEntity integrationAuthProgressEntity;

    @Column(name = "user_ci")
    private String userCi;

    @Column(name = "org_code")
    private String orgCode;

    @Builder
    public IntegrationAuthTaskEntity(IntegrationAuthProgressEntity integrationAuthProgressEntity,
                                     String userCi,
                                     String orgCode) {
        this.integrationAuthProgressEntity = integrationAuthProgressEntity;
        this.userCi = userCi;
        this.orgCode = orgCode;
    }

    public void registerProgress(IntegrationAuthProgressEntity integrationAuthProgressEntity) {
        this.integrationAuthProgressEntity = integrationAuthProgressEntity;
    }
}

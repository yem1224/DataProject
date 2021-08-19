package com.finda.server.mydata.auth.domain.entity;

import com.finda.server.mydata.common.domain.entity.AuditingBaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "mydata_integration_auth_progress")
@Entity
public class IntegrationAuthProgressEntity extends AuditingBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userCi;

    @OneToMany(mappedBy = "integrationAuthProgressEntity", cascade = {CascadeType.PERSIST})
    private List<IntegrationAuthTaskEntity> integrationAuthTaskEntities = new ArrayList<>();

    public IntegrationAuthProgressEntity(String userCi) {
        this.userCi = userCi;
    }

    public void addTask(IntegrationAuthTaskEntity integrationAuthTaskEntity) {
        integrationAuthTaskEntities.add(integrationAuthTaskEntity);
        integrationAuthTaskEntity.registerProgress(this);
    }

    public void addAllTasks(List<IntegrationAuthTaskEntity> integrationAuthTaskEntities) {
        for (IntegrationAuthTaskEntity integrationAuthTaskEntity : integrationAuthTaskEntities) {
            addTask(integrationAuthTaskEntity);
        }
    }
}

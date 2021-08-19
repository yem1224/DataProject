package com.finda.server.mydata.auth.domain.entity;

import com.finda.server.mydata.common.domain.entity.DeleteAttributeAuditingBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Table(name = "mydata_user")
@Entity
public class MydataUserEntity extends DeleteAttributeAuditingBaseEntity {

    @Id
    private Long userId;

    @Column(name = "user_ci")
    private String userCi;

    @Builder
    private MydataUserEntity(Long userId, String userCi) {
        this.userId = userId;
        this.userCi = userCi;
    }

    @Override
    protected void delete() {
        deleteYN = Boolean.TRUE;
    }
}

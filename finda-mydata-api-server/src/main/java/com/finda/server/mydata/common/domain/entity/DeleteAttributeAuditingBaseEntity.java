package com.finda.server.mydata.common.domain.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
public abstract class DeleteAttributeAuditingBaseEntity extends AuditingBaseEntity {
    @Column(name = "delete_yn")
    protected Boolean deleteYN = Boolean.FALSE;

    abstract protected void delete();
}

package com.finda.server.mydata.common.domain.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class AuditingBaseEntity {

    @CreatedDate
    @Column(name = "insert_time", updatable = false)
    protected LocalDateTime insertTime;

    @LastModifiedDate
    @Column(name = "update_time", updatable = true)
    protected LocalDateTime updateTime;
}
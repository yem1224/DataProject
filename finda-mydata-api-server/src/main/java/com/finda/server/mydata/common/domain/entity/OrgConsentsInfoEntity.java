package com.finda.server.mydata.common.domain.entity;

import com.finda.server.mydata.common.domain.entity.id.OrgConsentsInfoId;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "mydata_org_consent_info")
@Entity
@Data
public class OrgConsentsInfoEntity extends AuditingBaseEntity{

    @EmbeddedId
    OrgConsentsInfoId orgConsentsInfoId;

    @Column(name = "is_scheduled")
    private boolean isScheduled;

    private String cycle;

    @Column(name = "end_date")
    private String endDate;

    private String purpose;

    private int period;
}

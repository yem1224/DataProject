package com.finda.server.mydata.common.domain.entity;

import com.finda.server.mydata.common.domain.entity.id.OrgApiInfoId;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "mydata_org_api_info")
@Entity
@Data
public class OrgApiInfoEntity extends AuditingBaseEntity{

    @EmbeddedId
    OrgApiInfoId orgApiInfoId;

    private String version;

    @Column(name = "api_uri")
    private String apiUri;

    @Column(name = "min_version")
    private String minVersion;
}

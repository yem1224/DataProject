package com.finda.server.mydata.mgmts.dto;

import com.finda.server.mydata.common.code.OrgType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class OrgInfoDto {
    private String orgCode;
    private OrgType orgType;
    private String orgName;
    private String domain;
    private String relayOrgCode;
    private String industry;

    public OrgInfoDto(String orgCode,
                      OrgType orgType,
                      String orgName,
                      String domain,
                      String relayOrgCode,
                      String industry) {
        this.orgCode = orgCode;
        this.orgType = orgType;
        this.orgName = orgName;
        this.domain = domain;
        this.relayOrgCode = relayOrgCode;
        this.industry = industry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrgInfoDto that = (OrgInfoDto) o;
        return Objects.equals(orgCode, that.orgCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orgCode);
    }
}

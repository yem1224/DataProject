package com.finda.server.mydata.common.domain.entity.id;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Embeddable
public class OrgApiInfoId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "org_code")
    private String orgCode;

    @Column(name = "api_code")
    private String apiCode;

    public OrgApiInfoId(String orgCode, String apiCode) {
        this.orgCode = orgCode;
        this.apiCode = apiCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrgApiInfoId that = (OrgApiInfoId) o;
        return Objects.equals(orgCode, that.orgCode) && Objects.equals(apiCode, that.apiCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orgCode, apiCode);
    }
}

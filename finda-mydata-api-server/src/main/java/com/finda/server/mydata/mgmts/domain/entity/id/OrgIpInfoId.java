package com.finda.server.mydata.mgmts.domain.entity.id;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Data
@Embeddable
public class OrgIpInfoId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "org_code")
    private String orgCode;

    public OrgIpInfoId(Long id, String org_code) {
        this.id = id;
        this.orgCode = orgCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrgIpInfoId that = (OrgIpInfoId) o;
        return Objects.equals(id, that.id) && Objects.equals(orgCode, that.orgCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orgCode);
    }
}

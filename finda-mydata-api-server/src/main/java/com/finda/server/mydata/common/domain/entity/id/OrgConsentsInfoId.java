package com.finda.server.mydata.common.domain.entity.id;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Embeddable
public class OrgConsentsInfoId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "org_code")
    private String orgCode;

    @Column(name = "user_id")
    private Long userId;

    public OrgConsentsInfoId(String orgCode, Long userId) {
        this.orgCode = orgCode;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrgConsentsInfoId that = (OrgConsentsInfoId) o;
        return Objects.equals(orgCode, that.orgCode) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orgCode, userId);
    }
}

package com.finda.server.mydata.auth.domain.entity.id;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Embeddable
public class MydataAuthorizeId implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;
    private String orgCode;

    private MydataAuthorizeId(Long userId, String orgCode) {
        this.userId = userId;
        this.orgCode = orgCode;
    }

    public static MydataAuthorizeId create(Long userId, String orgCode) {
        return new MydataAuthorizeId(userId, orgCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MydataAuthorizeId that = (MydataAuthorizeId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(orgCode, that.orgCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, orgCode);
    }
}

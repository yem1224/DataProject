package com.finda.server.mydata.auth.domain.entity.id;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Embeddable
public class MydataAuthBaseId implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orgCode;
    private String userCi;

    private MydataAuthBaseId(String orgCode, String userCi) {
        this.orgCode = orgCode;
        this.userCi = userCi;
    }

    public static MydataAuthBaseId create(String orgCode, String userCi) {
        return new MydataAuthBaseId(orgCode, userCi);
    }

    public String orgCode() {
        return orgCode;
    }

    public String userCi() {
        return userCi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MydataAuthBaseId that = (MydataAuthBaseId) o;
        return Objects.equals(orgCode, that.orgCode) && Objects.equals(userCi, that.userCi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orgCode, userCi);
    }
}

package com.finda.server.mydata.auth.domain.entity.id;

import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
public class MydataTranReqPtclId implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userCi;
    private String orgCode;

    private MydataTranReqPtclId(String userCi, String orgCode) {
        this.userCi = userCi;
        this.orgCode = orgCode;
    }

    public static MydataTranReqPtclId create(String userCi, String orgCode) {
        return new MydataTranReqPtclId(userCi, orgCode);
    }

    public String getUserCi() {
        return userCi;
    }

    public String getOrgCode() {
        return orgCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MydataTranReqPtclId that = (MydataTranReqPtclId) o;
        return Objects.equals(userCi, that.userCi) && Objects.equals(orgCode, that.orgCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userCi, orgCode);
    }
}

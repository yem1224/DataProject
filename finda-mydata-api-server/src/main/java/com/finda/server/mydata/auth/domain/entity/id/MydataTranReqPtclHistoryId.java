package com.finda.server.mydata.auth.domain.entity.id;

import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
public class MydataTranReqPtclHistoryId implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long seqNo;
    private String userCi;
    private String orgCode;

    private MydataTranReqPtclHistoryId(Long seqNo, String userCi, String orgCode) {
        this.seqNo = seqNo;
        this.userCi = userCi;
        this.orgCode = orgCode;
    }

    public static MydataTranReqPtclHistoryId create(Long seqNo, String ci, String orgCode) {
        return new MydataTranReqPtclHistoryId(seqNo, ci, orgCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MydataTranReqPtclHistoryId that = (MydataTranReqPtclHistoryId) o;
        return Objects.equals(seqNo, that.seqNo) && Objects.equals(userCi, that.userCi) && Objects.equals(orgCode, that.orgCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seqNo, userCi, orgCode);
    }
}

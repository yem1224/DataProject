package com.finda.server.mydata.bank.domain.entity.id;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Embeddable
public class LoanBaseId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "account_num")
    private String accountNum;

    @Column(name = "seqno")
    private String seqno;

    @Column(name = "user_id")
    private Long userId;

    public LoanBaseId(String accountNum, String seqno, Long userId) {
        this.accountNum = accountNum;
        this.seqno = seqno;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanBaseId that = (LoanBaseId) o;
        return Objects.equals(accountNum, that.accountNum) && Objects.equals(seqno, that.seqno) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNum, seqno, userId);
    }
}

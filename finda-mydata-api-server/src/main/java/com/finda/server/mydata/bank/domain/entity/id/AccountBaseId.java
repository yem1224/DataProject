package com.finda.server.mydata.bank.domain.entity.id;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Embeddable
public class AccountBaseId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "account_num")
    private String accountNum;

    @Column(name = "seqno")
    private String seqno;

    @Column(name = "user_id")
    private long userId;


    public AccountBaseId(String accountNum, String seqno, long userId) {
        this.accountNum = accountNum;
        this.seqno = seqno;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountBaseId that = (AccountBaseId) o;
        return Objects.equals(accountNum, that.accountNum) && Objects.equals(seqno, that.seqno) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNum, seqno, userId);
    }
}

package com.finda.server.mydata.bank.domain.entity.id;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Embeddable
public class LoanDtlsId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "account_num")
    private String accountNum;

    @Column(name = "seqno")
    private String seqno;

    public LoanDtlsId(String accountNum, String seqno) {
        this.accountNum = accountNum;
        this.seqno = seqno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanDtlsId that = (LoanDtlsId) o;
        return Objects.equals(accountNum, that.accountNum) && Objects.equals(seqno, that.seqno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNum, seqno);
    }
}

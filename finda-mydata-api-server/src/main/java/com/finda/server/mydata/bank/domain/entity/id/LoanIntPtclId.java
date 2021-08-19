package com.finda.server.mydata.bank.domain.entity.id;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Embeddable
public class LoanIntPtclId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "int_ptcl_id")
    private Long intPtclId; // 이자내역 ID

    @Column(name = "tran_ptcl_id")
    private Long tranPtclId;

    @Column(name = "account_num")
    private String accountNum;

    @Column(name = "seqno")
    private String seqno;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanIntPtclId that = (LoanIntPtclId) o;
        return Objects.equals(intPtclId, that.intPtclId) && Objects.equals(tranPtclId, that.tranPtclId) && Objects.equals(accountNum, that.accountNum) && Objects.equals(seqno, that.seqno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(intPtclId, tranPtclId, accountNum, seqno);
    }
}

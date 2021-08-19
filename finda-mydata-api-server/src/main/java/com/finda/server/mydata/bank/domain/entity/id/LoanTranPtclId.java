package com.finda.server.mydata.bank.domain.entity.id;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Embeddable
public class LoanTranPtclId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "tran_ptcl_id")
    private Long tranPtclId; // 거래내역 ID

    @Column(name = "account_num")
    private String accountNum;

    @Column(name = "seqno")
    private String seqno;

    public LoanTranPtclId(Long tranPtclId, String accountNum, String seqno) {
        this.tranPtclId = tranPtclId;
        this.accountNum = accountNum;
        this.seqno = seqno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanTranPtclId that = (LoanTranPtclId) o;
        return Objects.equals(tranPtclId, that.tranPtclId) && Objects.equals(accountNum, that.accountNum) && Objects.equals(seqno, that.seqno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tranPtclId, accountNum, seqno);
    }
}

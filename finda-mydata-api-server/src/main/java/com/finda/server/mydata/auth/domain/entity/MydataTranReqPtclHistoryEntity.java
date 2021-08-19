package com.finda.server.mydata.auth.domain.entity;

import com.finda.server.mydata.auth.domain.entity.id.MydataTranReqPtclHistoryId;
import com.finda.server.mydata.common.domain.entity.AuditingBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@IdClass(MydataTranReqPtclHistoryId.class)
@Table(name = "mydata_tran_req_ptcl_hist")
@Entity
public class MydataTranReqPtclHistoryEntity extends AuditingBaseEntity {

    @Id
    @Column(name = "seq_no")
    private Long seqNo;

    @Id
    @Column(name = "user_ci")
    private String userCi;

    @Id
    @Column(name = "org_code")
    private String orgCode;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "is_scheduled")
    private Boolean isScheduled;

    private String cycle;

    private String purpose;

    private Integer period;

    @Builder
    public MydataTranReqPtclHistoryEntity(Long seqNo,
                                          String userCi,
                                          String orgCode,
                                          LocalDateTime endDate,
                                          String purpose,
                                          Boolean isScheduled,
                                          Integer period,
                                          String cycle) {
        this.seqNo = seqNo;
        this.userCi = userCi;
        this.orgCode = orgCode;
        this.isScheduled = isScheduled;
        this.cycle = cycle;
        this.endDate = endDate;
        this.purpose = purpose;
        this.period = period;
    }
}

package com.finda.server.mydata.auth.domain.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finda.server.mydata.auth.domain.entity.id.MydataTranReqPtclId;
import com.finda.server.mydata.common.domain.entity.DeleteAttributeAuditingBaseEntity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Table(name = "mydata_tran_req_ptcl")
@IdClass(MydataTranReqPtclId.class)
@Entity
public class MydataTranReqPtclEntity extends DeleteAttributeAuditingBaseEntity {

    @Id
    @Column(name = "user_ci")
    private String userCi;

    @Id
    @Column(name = "org_code")
    private String orgCode;

    @Column(name = "is_scheduled")
    private Boolean isScheduled;

    @Column(name = "cycle")
    private String cycle;

    @Column(name = "end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    private LocalDateTime endDate;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "period")
    private Integer period;

    @Builder
    private MydataTranReqPtclEntity(String userCi,
                                    String orgCode,
                                    LocalDateTime endDate,
                                    Boolean isScheduled,
                                    String cycle,
                                    String purpose,
                                    Integer period) {
        this.userCi = userCi;
        this.orgCode = orgCode;
        this.isScheduled = isScheduled;
        this.cycle = cycle;
        this.endDate = endDate;
        this.purpose = purpose;
        this.period = period;
    }

    @Override
    protected void delete() {
        this.deleteYN = Boolean.TRUE;
    }

    public void update(Boolean isScheduled,
                       String cycle,
                       LocalDateTime endDate,
                       String purpose,
                       Integer period) {
        this.isScheduled = isScheduled;
        this.cycle = cycle;
        this.endDate = endDate;
        this.purpose = purpose;
        this.period = period;
    }

    public MydataTranReqPtclHistoryEntity toHistory(Long seqNo) {
        return MydataTranReqPtclHistoryEntity.builder()
                .seqNo(seqNo)
                .userCi(userCi)
                .orgCode(orgCode)
                .isScheduled(isScheduled)
                .cycle(cycle)
                .endDate(endDate)
                .purpose(purpose)
                .period(period)
                .build();
    }
}

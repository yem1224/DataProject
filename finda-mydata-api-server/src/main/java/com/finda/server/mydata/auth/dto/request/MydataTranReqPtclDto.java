package com.finda.server.mydata.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.finda.server.mydata.auth.dto.TargetInfoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MydataTranReqPtclDto {

    @JsonProperty("snd_org_code")
    private String sndOrgCode;  // 정보제공자 기관코드(org_code)

    @JsonProperty("rcv_org_code")
    private String rcvOrgCode;  // 마이데이터 사업자(정보수신자) 기관코드(org_code)

    @JsonProperty("is_scheduled")
    private Boolean isScheduled;    // 정기적 전송 여부

    @JsonProperty("cycle")
    private CycleDto cycleDto;  //정기적 전송 주기, isScheduled가 false일 경우 본 항목 생략

    @JsonProperty("end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private LocalDateTime endDate;  // 전송요구의 종료시점

    private String purpose; // 전송을 요구하는 목적

    @JsonProperty("holding_period")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private LocalDateTime holdingPeriod;    // 전송을 요구하는 개인신용정보의 보유기간

    @JsonProperty("target_info")
    private TargetInfoDto targetInfoDtos;

    @Builder
    public MydataTranReqPtclDto(String sndOrgCode,
                                String rcvOrgCode,
                                Boolean isScheduled,
                                CycleDto cycleDto,
                                LocalDateTime endDate,
                                String purpose,
                                LocalDateTime holdingPeriod,
                                TargetInfoDto targetInfoDtos) {
        this.sndOrgCode = sndOrgCode;
        this.rcvOrgCode = rcvOrgCode;
        this.isScheduled = isScheduled;
        this.cycleDto = cycleDto;
        this.endDate = endDate;
        this.purpose = purpose;
        this.holdingPeriod = holdingPeriod;
        this.targetInfoDtos = targetInfoDtos;
    }

    @Getter
    @NoArgsConstructor
    public static class CycleDto {

        /**
         * 기본 정보의 정기적 전송 주기 단위
         * 규격(월1회) : "1(횟수)/m(기준)"
         * <p>
         * 기준
         * 월 기준 : "m" 고정값
         * 주 기준 : "w" 고정값
         * 일 기준 : "d" 고정값
         */
        @JsonProperty("fnd_cycle")
        private String fndCycle;

        @JsonProperty("add_cycle")
        private String addCycle;    // 추가 정보의 정기적 전송 주기 단위 (fndCycle 항목 생성 규칙 준용)
    }
}

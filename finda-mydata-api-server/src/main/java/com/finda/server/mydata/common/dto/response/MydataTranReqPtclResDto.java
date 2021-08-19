package com.finda.server.mydata.common.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MydataTranReqPtclResDto {

    @JsonProperty("rsp_code")
    private String rspCode;

    @JsonProperty("rsp_msg")
    private String rspMsg;

    @JsonProperty("is_scheduled")
    private Boolean isScheduled;

    @JsonProperty("cycle")
    private String cycle;

    @JsonProperty("end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    private LocalDateTime endDate;

    @JsonProperty("purpose")
    private String purpose;

    @JsonProperty("period")
    private Integer period;
}

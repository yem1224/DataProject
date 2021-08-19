package com.finda.server.mydata.common.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @since 2021. 06. 29
 * 공통 002 API 응답메시지
 */
@NoArgsConstructor
@Data
public class OrgConsentsResDto extends ResBodyDto{
    @JsonProperty("is_scheduled")
    private boolean isScheduled;

    private String cycle;

    @JsonProperty("end_date")
    private String endDate;

    private String purpose;

    private int period;
}

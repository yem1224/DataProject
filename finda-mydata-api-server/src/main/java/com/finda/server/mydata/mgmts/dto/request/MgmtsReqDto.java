package com.finda.server.mydata.mgmts.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @since 2021. 06. 11
 * 지원 002, 003 API 요청메시지 Body
 */
@NoArgsConstructor
@Data
public class MgmtsReqDto {
    @JsonProperty("search_timestamp")
    private int searchTimestamp;
}

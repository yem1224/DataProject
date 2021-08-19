package com.finda.server.mydata.common.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @since 2021. 06. 11
 * 응답메세지 body 공통부
 */
@NoArgsConstructor
@Data
public class ResBodyDto {
    //세부 응답코드
    @JsonProperty("rsp_code")
    private String rspCode;

    //세부 응답메세지
    @JsonProperty("rsp_msg")
    private String rspMsg;

    //조회 타임스탬프
    @JsonProperty("search_timestamp")
    private long searchTimestamp;
}

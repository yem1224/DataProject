package com.finda.server.mydata.bank.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @since 2021. 06. 18
 * 은행 001 API 요청메시지
 */
@NoArgsConstructor
@Data
public class BankAcctReqDto {

    @JsonProperty("org_code")
    private String orgCode;

    @JsonProperty("search_timestamp")
    private Long searchTimestamp;

    @JsonProperty("next_page")
    private String next_page;

    private int limit;
}

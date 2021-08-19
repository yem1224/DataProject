package com.finda.server.mydata.bank.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @since 2021. 06. 18
 * 은행 008, 009 API 요청메시지
 */
@NoArgsConstructor
@Data
public class BankLoanReqDto {
    @JsonProperty("org_code")
    private String orgCode;

    @JsonProperty("account_num")
    private String accountNum;

    private String seqno;

    @JsonProperty("search_timestamp")
    private Long searchTimestamp;
}

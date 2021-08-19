package com.finda.server.mydata.bank.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @since 2021. 06. 18
 * 은행 010 API 요청메시지
 */
@NoArgsConstructor
@Data
public class BankLoanTransReqDto {
    @JsonProperty("org_code")
    private String orgCode;

    @JsonProperty("account_num")
    private String accountNum;

    private String seqno;

    @JsonProperty("from_date")
    private String fromDate;

    @JsonProperty("to_date")
    private String toDate;

    @JsonProperty("next_page")
    private String nextPage;

    private int limit;
}

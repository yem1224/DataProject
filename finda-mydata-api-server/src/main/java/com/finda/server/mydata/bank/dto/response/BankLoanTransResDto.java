package com.finda.server.mydata.bank.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finda.server.mydata.bank.domain.entity.code.IntType;
import com.finda.server.mydata.common.dto.response.ResBodyDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @since 2021. 06. 18
 * 은행 010 API 응답메시지
 */
@NoArgsConstructor
@Data
public class BankLoanTransResDto extends ResBodyDto {

    @JsonProperty("next_page")
    private String nextPage;

    @JsonProperty("trans_cnt")
    private int transCnt;

    @JsonProperty("trans_list")
    private List<TransInfo> transList;

    @Data
    public static class TransInfo {
        @JsonProperty("trans_dtime")
        private String transDtime;

        @JsonProperty("trans_no")
        private int transNo;

        @JsonProperty("trans_type")
        private String transType;

        @JsonProperty("currency_code")
        private String currencyCode;

        @JsonProperty("trans_amt")
        private float transAmt;

        @JsonProperty("balance_amt")
        private float balanceAmt;

        @JsonProperty("principal_amt")
        private float principalAmt;

        @JsonProperty("int_amt")
        private float intAmt;

        @JsonProperty("ret_int_amt")
        private float retIntAmt;

        @JsonProperty("int_cnt")
        private int intCnt;

        @JsonProperty("int_list")
        private List<IntInfo> intList;
    }

    @Data
    public static class IntInfo {
        @JsonProperty("int_start_date")
        private String intStartDate;

        @JsonProperty("int_end_date")
        private String intEndDate;

        @JsonProperty("int_rate")
        private float intRate;

        @JsonProperty("applied_int_amt")
        private float appliedIntAmt;

        @JsonProperty("int_type")
        private IntType intType;
    }
}

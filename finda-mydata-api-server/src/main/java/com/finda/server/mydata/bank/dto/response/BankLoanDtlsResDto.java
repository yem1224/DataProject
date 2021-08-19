package com.finda.server.mydata.bank.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finda.server.mydata.common.dto.response.ResBodyDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @since 2021. 06. 18
 * 은행 009 API 응답메시지
 */
@NoArgsConstructor
@Data
public class BankLoanDtlsResDto extends ResBodyDto {
    @JsonProperty("currency_code")
    private String currencyCode;

    @JsonProperty("balance_amt")
    private float balanceAmt;

    @JsonProperty("loan_principal")
    private float loanPrincipal;

    @JsonProperty("next_repay_date")
    private String nextRepayDate;
}

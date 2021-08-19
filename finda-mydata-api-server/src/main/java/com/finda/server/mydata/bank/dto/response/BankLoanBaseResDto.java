package com.finda.server.mydata.bank.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finda.server.mydata.common.dto.response.ResBodyDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @since 2021. 06. 18
 * 은행 008 API 응답메시지
 */
@NoArgsConstructor
@Data
public class BankLoanBaseResDto extends ResBodyDto {
    @JsonProperty("issue_date")
    private String issueDate;

    @JsonProperty("exp_date")
    private String expDate;

    @JsonProperty("last_offered_rate")
    private float lastOfferedRate;

    @JsonProperty("repay_date")
    private String repayDate;

    @JsonProperty("repay_method")
    private String repayMethod;

    @JsonProperty("repay_org_code")
    private String repayOrgCode;

    @JsonProperty("repay_account_num")
    private String repayAccountNum;
}

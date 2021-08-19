package com.finda.server.mydata.bank.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finda.server.mydata.common.code.AccountStatus;
import com.finda.server.mydata.common.code.AccountType;
import com.finda.server.mydata.common.dto.response.ResBodyDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @since 2021. 06. 18
 * 은행 001 API 응답메시지
 */
@NoArgsConstructor
@Data
public class BankAcctResDto extends ResBodyDto {

    @JsonProperty("reg_date")
    private int regDate;

    @JsonProperty("next_page")
    private String next_page;

    @JsonProperty("account_cnt")
    private int accountCnt;

    @JsonProperty("account_list")
    private List<AcctInfo> accountList;

    @Data
    public static class AcctInfo {
        @JsonProperty("account_num")
        private String accountNum;

        @JsonProperty("is_consent")
        private boolean isConsent;

        private String seqno;

        @JsonProperty("is_foreign_deposit")
        private boolean isForeignDeposit;

        @JsonProperty("prod_name")
        private String prodName;

        @JsonProperty("is_minus")
        private boolean isMinus;

        @JsonProperty("account_type")
        private AccountType accountType;

        @JsonProperty("account_status")
        private AccountStatus accountStatus;
    }

}

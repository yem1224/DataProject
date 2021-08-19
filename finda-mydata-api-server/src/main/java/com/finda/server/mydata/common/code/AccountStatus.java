package com.finda.server.mydata.common.code;

/**
 * 계좌상태 code
 */
public enum AccountStatus {
    ACCOUNT_STATUS01("01","활동(사고 포함)"),
    ACCOUNT_STATUS02("02","비활동");

    private String accountStatus;
    private String accountStatusNm;

    AccountStatus(String code, String name) {
        this.accountStatus = code;
        this.accountStatusNm = name;
    }

    public String getCode() {
        return accountStatus;
    }
    public String getName(){ return accountStatusNm; }
}
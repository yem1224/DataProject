package com.finda.server.mydata.common.code;

/**
 * 계좌구분 code
 */
public enum AccountType {
    ACCOUNT_TYPE3100("3100","신용대출"),
    ACCOUNT_TYPE3150("3150","학자금대출"),
    ACCOUNT_TYPE3170("3170","전세자금대출"),
    ACCOUNT_TYPE3200("3200","예・적금담보대출"),
    ACCOUNT_TYPE3210("3210","유가증권(주식,채권, 펀드 등)담보대출"),
    ACCOUNT_TYPE3220("3220","주택담보대출"),
    ACCOUNT_TYPE3230("3230","주택외 부동산(토지, 상가등)담보대출"),
    ACCOUNT_TYPE3240("3240","지급보증(보증서) 담보대출"),
    ACCOUNT_TYPE3245("3245","보금자리론"),
    ACCOUNT_TYPE3250("3250","학자금(지급보증담보)대출"),
    ACCOUNT_TYPE3260("3260","주택연금대출"),
    ACCOUNT_TYPE3270("3270","전세자금(보증서, 질권 등)대출"),
    ACCOUNT_TYPE3271("3271","전세보증금 담보대출"),
    ACCOUNT_TYPE3290("3290","기타 담보대출"),
    ACCOUNT_TYPE3400("3400","보험계약대출"),
    ACCOUNT_TYPE3500("3500","신차 할부금융"),
    ACCOUNT_TYPE3510("3510","중고차 할부금융"),
    ACCOUNT_TYPE3590("3590","기타 할부금융"),
    ACCOUNT_TYPE3700("3700","금융리스"),
    ACCOUNT_TYPE3710("3710","운용리스"),
    ACCOUNT_TYPE3999("3999","채권인수전문기관 보유");

    private String accountType;
    private String accountTypeNm;

    AccountType(String code, String name) {
        this.accountType = code;
        this.accountTypeNm = name;
    }

    public String getCode() {
        return accountType;
    }
    public String getName(){ return accountTypeNm; }
}
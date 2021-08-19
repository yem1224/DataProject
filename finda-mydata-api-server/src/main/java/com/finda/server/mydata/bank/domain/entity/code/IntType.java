package com.finda.server.mydata.bank.domain.entity.code;

public enum IntType {
    INT_TYPE01("01","정상이자"),
    INT_TYPE02("02","지연이자"),
    INT_TYPE03("03","잔액연체이자"),
    INT_TYPE99("99","기타");

    private String intType;
    private String intTypeNm;

    IntType(String code, String name) {
        this.intType = code;
        this.intTypeNm = name;
    }

    public String getCode() {
        return intType;
    }
    public String getName() {
        return intTypeNm;
    }
}

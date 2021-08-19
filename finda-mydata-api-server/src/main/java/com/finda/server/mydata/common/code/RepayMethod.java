package com.finda.server.mydata.common.code;

/**
 * 상환방식 code
 */
public enum RepayMethod {
    REPAY_METHOD01("01","만기일시상환"),
    REPAY_METHOD02("02","원금균등분할상환"),
    REPAY_METHOD03("03","거치식-원금균등분할상환"),
    REPAY_METHOD04("04","원리금균등분할상환"),
    REPAY_METHOD05("05","거치식-원리금균등분할상환"),
    REPAY_METHOD06("06","만기지정상환-원금균등분할상환"),
    REPAY_METHOD07("07","만기지정상환-원리금균등분할상환"),
    REPAY_METHOD08("08","한도거래"),
    REPAY_METHOD09("09","기타(직접산정)"),
    REPAY_METHOD10("10","거치식 만기지정상환-원금균등분할상환"),
    REPAY_METHOD11("11","거치식 만기지정상환-원리금균등분할상환");

    private String repayMethod;
    private String repayMethodNm;

    RepayMethod(String code, String name) {
        this.repayMethod = code;
        this.repayMethodNm = name;
    }
    public String getCode(){ return repayMethod; }
    public String getName(){ return repayMethodNm; }
}
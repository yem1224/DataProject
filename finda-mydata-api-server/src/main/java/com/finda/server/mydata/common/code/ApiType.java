package com.finda.server.mydata.common.code;

/**
 * API 타입 code
 */
public enum ApiType {
    API_TYPE_AU01("AU01","/oauth/2.0/authorize"), // 개별인증 인가코드 발급
    API_TYPE_AU02("AU02","/oauth/2.0/token"), // 개별인증 접근토큰 발급,
    API_TYPE_AU03("AU03", "/oauth/2.0/token"), // 개별인증 접근토큰 갱신
    API_TYPE_AU04("AU04","/oauth/2.0/revoke"), // 개별인증 접근토큰 폐기
    API_TYPE_AU11("AU11","/oauth/2.0/token"), // 통합인증 접근토큰 발급 요청
    API_TYPE_CM01("CM01","/apis"), // 공통 API목록 조회
    API_TYPE_CM02("CM02","/consents"), // 공통 전송요구 내역조회
    API_TYPE_BA01("BA01","/bank/accounts"), // 은행 계좌목록 조회
    //API_TYPE_BA02("BA02","/bank/accounts/deposit/basic"), // 은행 수신계좌 기본정보
    //API_TYPE_BA03("BA03","/bank/accounts/deposit/detail"), // 은행 수신계좌 추가정보
    //API_TYPE_BA04("BA04","/bank/accounts/deposit/transactions"), // 은행 수신계좌 거래내역
    //API_TYPE_BA11("BA11","/bank/accounts/invest/basic"), // 은행 투자상품계좌 기본정보
    //API_TYPE_BA12("BA12","/bank/accounts/invest/detail"), // 은행 투자상품계좌 추가정보
    //API_TYPE_BA13("BA13","/bank/accounts/invest/transactions"), // 은행 투자상품계좌 거래내역
    API_TYPE_BA21("BA21","/bank/accounts/loan/basic"), // 은행 대출상품계좌 기본정보
    API_TYPE_BA22("BA22","/bank/accounts/loan/detail"), // 은행 대출상품계좌 추가정보
    API_TYPE_BA23("BA23","/bank/accounts/loan/transactions"), // 은행 대출상품계좌 거래내역
    API_TYPE_CD31("CD31","/card/loans"), // 카드 대출상품 목록 조회
    API_TYPE_CD32("CD32","/card/loans/short-term"), // 카드 단기대출 정보 조회
    API_TYPE_CD33("CD33","/card/loans/long-term"), // 카드 장기대출 정보 조회
    API_TYPE_IS11("IS11","/insu/loans"), // 보험 대출상품 목록 조회
    API_TYPE_IS12("IS12","/insu/loans/basic"), // 보험 대출상품 기본정보 조회
    API_TYPE_IS13("IS13","/insu/loans/detail"), // 보험 대출상품 추가정보 조회
    API_TYPE_IS14("IS14","/insu/loans/transactions"), // 보험 대출상품 거래내역 조회
    API_TYPE_CP01("CP01","/capital/loans"), // 할부금융업권
    API_TYPE_CP02("CP02","/capital/loans/basic"), // 할부금융업권
    API_TYPE_CP03("CP03","/capital/loans/detail"), // 할부금융업권
    API_TYPE_CP04("CP04","/capital/loans/transactions"); // 할부금융업권

    private String apiType;
    private String apiTypeNm;

    ApiType(String code, String name) {
        this.apiType = code;
        this.apiTypeNm = name;
    }
    public String getCode() {
        return apiType;
    }
    public String getName(){ return apiTypeNm; }
}

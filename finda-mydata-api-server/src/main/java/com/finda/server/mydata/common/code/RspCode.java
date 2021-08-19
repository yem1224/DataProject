package com.finda.server.mydata.common.code;

/**
 * 응답코드, 메세지
 */
public enum RspCode {

    INVALID_CLIENT_ID("invalid_request","client_id가 유효하지 않은 경우","400"),
    INVALID_REDIRECTION("invalid_request","redirect_uri가 유효하지 않은 경우","400"),
    INVALID_REQUEST302("invalid_request","client_id 및 redirect_uri가 유효하지만, 요청 파라미터에 문제가 있는 경우","302"),
    UNAUTHORIZED_CLIENT302("unauthorized_client","클라이언트가 권한이 없는 경우","302"),
    ACCESS_DENIED("access_denied","정보주체가 요청을 거부한 경우","302"),
    UNSUPPORTED_RESPONSE_TYPE("unsupported_response_type","response_type이 ‘code’가 아닌 경우","302"),
    SERVER_ERROR("server_error","서버 오류","302"),
    TEMPORARILY_UNAVAILABLE("temporarily_unavailable","서버가 일시적인 부하 등으로 서비스가 불가한 경우","302"),
    INVALID_REQUEST400("invalid_request","grant_type을 제외한 요청 파라미터에 문제가 있는 경우","400"),
    INVALID_CLIENT("invalid_client","클라이언트 인증이 실패한 경우","400"),
    INVALID_GRANT("invalid_grant","인가코드, refresh token이 틀리거나, 유효(만료)하지 않은 경우, redirect_uri가 일치하지 않는 경우 등","400"),
    UNAUTHORIZED_CLIENT400("unauthorized_client","클라이언트가 권한이 없는 경우","400"),
    UNSUPPORTED_GRANT_TYPE("unsupported_grant_type","grant_type 값이 잘못된 경우","400"),
    INVALID_SCOPE("invalid_scope","지정한 scope 값이 잘못된 경우","400"),
    CODE503("503","서버가 일시적인 부하 등으로 서비스가 불가","503"),

    CODE00000("00000","처리성공","200" ),
    CODE00001("00001","처리성공","200"),
    CODE40001("40001","요청 파라미터에 문제가 있는 경우","400"),
    CODE40002("40002","헤더 값 미존재","400"),
    CODE40003("40003","허용된 API 버전이 아님","400"),
    CODE40101("40101","유효하지 않은 접근토큰","401"),
    CODE40102("40102","허용되지 않은 원격지 IP","401"),
    CODE40103("40103","TLS 인증서 내 SERIALNUMBER 검증 실패","401"),
    CODE40104("40104","API 사용 권한 없음 (불충분한 scope 등)","401"),
    CODE40105("40105","자산(계좌, 카드 등)에 대한 정보조회 권한없음","401"),
    CODE40301("40301","올바르지 않은 API 호출","403"),
    CODE40302("40302","일시적으로 해당 클라이언트의 요청이 제한됨","403"),
    CODE40303("40303","기관코드 확인 불가","403"),
    CODE40304("40304","최대 보존기간을 초과한 데이터 요청","403"),
    CODE40305("40305","자산이 유효한 상태가 아님","403"),
    CODE40401("40401","요청한 엔드포인트는 존재하지 않음","404"),
    CODE40402("40402","요청한 정보에 대한 정보는 존재하지 않음","404"),
    CODE40403("40403","정보주체(고객) 미존재","404"),
    CODE42901("42901","정보제공 요청한도 초과","429"),
    CODE50001("50001","시스템장애","500"),
    CODE50002("50002","API 요청 처리 실패","500"),
    CODE50003("50003","처리시간 초과 에러","500"),
    CODE50004("50004","알 수 없는 에러","500"),
    CODE50005("50005","정보제공자 서비스 불가","500");

    private String rspCode;
    private String rspName;
    private String httpCode;

    RspCode(String code, String name, String httpCode) {
        this.rspCode = code;
        this.rspName = name;
        this.httpCode = httpCode;
    }

    public String getCode() {
        return rspCode;
    }
    public String getName(){ return rspName; }
    public String getHttpCode(){ return httpCode; }
}

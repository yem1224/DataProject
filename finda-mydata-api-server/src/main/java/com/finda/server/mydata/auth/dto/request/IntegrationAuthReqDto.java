package com.finda.server.mydata.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IntegrationAuthReqDto {

    @JsonProperty("tx_id")
    private String txId;

    @JsonProperty("org_code")
    private String orgCode;

    @JsonProperty("grant_type")
    private String grantType;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;

    @JsonProperty("ca_code")
    private String caCode;

    @JsonProperty("username")
    private String username;    // 고객 CI 정보

    @JsonProperty("password_len")
    private int passwordLen;

    @JsonProperty("password")
    private String password;

    @JsonProperty("auth_type")
    private String authType;

    @JsonProperty("consent_type")
    private String consentType;

    @JsonProperty("consent_len")
    private int consentLen;

    @JsonProperty("consent")
    private String consent;

    @JsonProperty("signed_person_info_req_len")
    private int signedPersonInfoReqLen;

    @JsonProperty("signed_person_info_req")
    private String signedPersonInfoReq;

    @JsonProperty("consent_nonce")
    private String consentNonce;

    @JsonProperty("ucpid_nonce")
    private String ucpidNonce;

    @JsonProperty("cert_tx_id")
    private String certTxId;

    @JsonProperty("service_id")
    private String serviceId;

    @Builder
    public IntegrationAuthReqDto(String txId,
                                 String orgCode,
                                 String grantType,
                                 String clientId,
                                 String clientSecret,
                                 String caCode,
                                 String username,
                                 int passwordLen,
                                 String password,
                                 String authType,
                                 String consentType,
                                 int consentLen,
                                 String consent,
                                 int signedPersonInfoReqLen,
                                 String signedPersonInfoReq,
                                 String consentNonce,
                                 String ucpidNonce,
                                 String certTxId,
                                 String serviceId) {
        this.txId = txId;
        this.orgCode = orgCode;
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.caCode = caCode;
        this.username = username;
        this.passwordLen = passwordLen;
        this.password = password;
        this.authType = authType;
        this.consentType = consentType;
        this.consentLen = consentLen;
        this.consent = consent;
        this.signedPersonInfoReqLen = signedPersonInfoReqLen;
        this.signedPersonInfoReq = signedPersonInfoReq;
        this.consentNonce = consentNonce;
        this.ucpidNonce = ucpidNonce;
        this.certTxId = certTxId;
        this.serviceId = serviceId;
    }
}

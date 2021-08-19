package com.finda.server.mydata.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class AuthTokenReqDto {
    @JsonProperty("org_code")
    private String orgCode;

    @JsonProperty("grant_type")
    private String grantType;

    @JsonProperty("code")
    private String code;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;

    @JsonProperty("redirect_uri")
    private String redirectUri;

    @Builder
    public AuthTokenReqDto(String orgCode,
                           String grantType,
                           String code,
                           String clientId,
                           String clientSecret,
                           String redirectUri) {
        Objects.requireNonNull(orgCode);
        Objects.requireNonNull(grantType);
        Objects.requireNonNull(code);
        Objects.requireNonNull(clientId);
        Objects.requireNonNull(clientSecret);
        Objects.requireNonNull(redirectUri);

        this.orgCode = orgCode;
        this.grantType = grantType;
        this.code = code;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }
}

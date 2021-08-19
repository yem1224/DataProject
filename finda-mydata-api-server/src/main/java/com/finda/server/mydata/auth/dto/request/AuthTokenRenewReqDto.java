package com.finda.server.mydata.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
public class AuthTokenRenewReqDto {

    @JsonProperty("org_code")
    private String orgCode;

    @JsonProperty("grant_type")
    private String grantType;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;

    @Builder
    public AuthTokenRenewReqDto(String orgCode,
                                String grantType,
                                String refreshToken,
                                String clientId,
                                String clientSecret) {
        Objects.requireNonNull(orgCode);
        Objects.requireNonNull(grantType);
        Objects.requireNonNull(refreshToken);
        Objects.requireNonNull(clientId);
        Objects.requireNonNull(clientSecret);

        this.orgCode = orgCode;
        this.grantType = grantType;
        this.refreshToken = refreshToken;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }
}

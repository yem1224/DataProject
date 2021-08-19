package com.finda.server.mydata.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AuthTokenRevokeReqDto {

    @JsonProperty("org_code")
    private String orgCode;

    @JsonProperty("token")
    private String token;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;
}

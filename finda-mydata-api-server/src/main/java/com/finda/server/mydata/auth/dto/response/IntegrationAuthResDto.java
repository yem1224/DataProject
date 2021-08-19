package com.finda.server.mydata.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finda.server.mydata.auth.dto.UnsuccessfulDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IntegrationAuthResDto extends UnsuccessfulDto {

    @JsonProperty("tx_id")
    private String txId;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private int expiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("refresh_token_expires_in")
    private int refreshTokenExpiresIn;

    @JsonProperty("scope")
    private String scope;
}

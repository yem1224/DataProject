package com.finda.server.mydata.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finda.server.mydata.auth.dto.UnsuccessfulDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthTokenDto extends UnsuccessfulDto {

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

    @Builder
    public AuthTokenDto(String tokenType, String accessToken, int expiresIn, String refreshToken, int refreshTokenExpiresIn, String scope) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
        this.scope = scope;
    }
}

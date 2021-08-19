package com.finda.server.mydata.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finda.server.mydata.auth.dto.UnsuccessfulDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRenewResDto extends UnsuccessfulDto {

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private int expiresIn;
}

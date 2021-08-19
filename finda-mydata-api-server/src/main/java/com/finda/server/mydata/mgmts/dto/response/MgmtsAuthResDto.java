package com.finda.server.mydata.mgmts.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MgmtsAuthResDto {

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private int expiresIn;

    private String scope;
    
    public void setResponseToken(String accessToken, int expiresIn) {
    	this.setAccessToken(accessToken);
		this.setTokenType("Bearer");
		this.setExpiresIn(expiresIn);
		this.setScope("manage");
    }
}

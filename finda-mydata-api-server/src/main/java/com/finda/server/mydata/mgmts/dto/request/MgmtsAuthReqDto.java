package com.finda.server.mydata.mgmts.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MgmtsAuthReqDto {

    @JsonProperty("grant_type")
    private String grantType;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;

    private String scope;

    @Builder
    private MgmtsAuthReqDto(String grantType,
                            String clientId,
                            String clientSecret,
                            String scope) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scope = scope;
    }
    
    public void setRequest(String clientId, String clientSecret) {
    	this.setGrantType("client_credentials");
    	this.setClientId(clientId);
    	this.setClientSecret(clientSecret);
    	this.setScope("manage");
    }
}

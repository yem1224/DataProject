package com.finda.server.mydata.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizeReqDto {

    @JsonProperty("org_code")
    private String orgCode;

    @JsonProperty("response_type")
    private String responseType;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("redirect_uri")
    private String redirectUri;

    @JsonProperty("app_scheme")
    private String appScheme;

    @JsonProperty("state")
    private String state;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorizeReqDto that = (AuthorizeReqDto) o;
        return Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }
}

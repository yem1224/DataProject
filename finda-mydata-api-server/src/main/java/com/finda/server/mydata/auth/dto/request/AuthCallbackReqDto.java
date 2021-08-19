package com.finda.server.mydata.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthCallbackReqDto {
    private String code;
    private String state;
    private String error;

    @JsonProperty("error_description")
    private String errorDescription;

    public boolean isSuccess() {
        return Objects.isNull(error);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthCallbackReqDto that = (AuthCallbackReqDto) o;
        return Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }
}

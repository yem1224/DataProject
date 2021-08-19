package com.finda.server.mydata.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class UnsuccessfulDto {

    @JsonProperty("error")
    private String error;

    @JsonProperty("error_description")
    private String errorDescription;
}

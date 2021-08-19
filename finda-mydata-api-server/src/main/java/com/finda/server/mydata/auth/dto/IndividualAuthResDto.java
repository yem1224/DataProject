package com.finda.server.mydata.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URI;

@Getter
@NoArgsConstructor
public class IndividualAuthResDto {
    private URI redirectUri;
    private String state;

    @Builder
    public IndividualAuthResDto(URI redirectUri, String state) {
        this.redirectUri = redirectUri;
        this.state = state;
    }
}

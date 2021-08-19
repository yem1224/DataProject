package com.finda.server.mydata.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class AuthSignatureDto {

    private String caOrg;
    private String authSession;

    @JsonProperty("signedDataList")
    private List<SignedSignatureDto> signedSignatureDtoList = new ArrayList<>();
}

package com.finda.server.mydata.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignedSignatureDto {
    private String orgCode;
    private String signedPersonInfoReq;
    private String signedConsent;
}

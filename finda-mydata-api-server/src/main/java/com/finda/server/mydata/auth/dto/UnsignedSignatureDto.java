package com.finda.server.mydata.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UnsignedSignatureDto {
    private String orgCode;
    private UcpidRequestInfo ucpidRequestInfo;
    private ConsentInfo consentInfo;

    @Builder
    public UnsignedSignatureDto(String orgCode,
                                UcpidRequestInfo ucpidRequestInfo,
                                ConsentInfo consentInfo) {
        this.orgCode = orgCode;
        this.ucpidRequestInfo = ucpidRequestInfo;
        this.consentInfo = consentInfo;
    }
}

package com.finda.server.mydata.auth.dto;

import com.finda.server.mydata.auth.dto.request.MydataTranReqPtclDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConsentInfo {
    private MydataTranReqPtclDto consent;
    private String consentNonce;

    @Builder
    public ConsentInfo(MydataTranReqPtclDto consent, String consentNonce) {
        this.consent = consent;
        this.consentNonce = consentNonce;
    }
}

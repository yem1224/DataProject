package com.finda.server.mydata.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UcpidRequestInfo {
    private String userAgreement;
    private UserAgreeInfo userAgreeInfo;
    private String ispUrlInfo;
    private String ucpidNonce;

    @Builder
    public UcpidRequestInfo(String userAgreement,
                            String ispUrlInfo,
                            String ucpidNonce) {
        this.userAgreement = userAgreement;
        this.userAgreeInfo = new UserAgreeInfo();
        this.ispUrlInfo = ispUrlInfo;
        this.ucpidNonce = ucpidNonce;
    }

    @Getter
    @NoArgsConstructor
    public static class UserAgreeInfo {
        private Boolean realName = Boolean.TRUE;
        private Boolean gender = Boolean.TRUE;
        private Boolean nationalInfo = Boolean.TRUE;
        private Boolean birthDate = Boolean.TRUE;
        private Boolean ci = Boolean.TRUE;
    }
}

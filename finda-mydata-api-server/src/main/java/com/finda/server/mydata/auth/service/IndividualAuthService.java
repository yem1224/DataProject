package com.finda.server.mydata.auth.service;

import com.finda.server.mydata.auth.dto.IndividualAuthResDto;
import com.finda.server.mydata.auth.dto.request.*;

public interface IndividualAuthService {
    IndividualAuthResDto authorize(Long userId, AuthorizeReqDto authorizeReqDto);

    void issueToken(AuthCallbackReqDto authCallbackReqDto);

    void renew(Long userId, AuthTokenRenewReqDto authTokenRenewReqDto);

    void revoke(Long userId, AuthTokenRevokeReqDto authTokenRevokeReqDto);
}

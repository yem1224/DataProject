package com.finda.server.mydata.auth.controller;

import com.finda.server.mydata.auth.AuthConstants;
import com.finda.server.mydata.auth.dto.IndividualAuthResDto;
import com.finda.server.mydata.auth.dto.request.AuthCallbackReqDto;
import com.finda.server.mydata.auth.dto.request.AuthTokenRenewReqDto;
import com.finda.server.mydata.auth.dto.request.AuthorizeReqDto;
import com.finda.server.mydata.auth.dto.request.AuthTokenRevokeReqDto;
import com.finda.server.mydata.auth.service.IndividualAuthService;
import com.finda.services.finda.common.log.WebLogUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "인증 API", description = "고객이 개인신용정보 전송요구 및 본인인증을 수행하기 위해 필요한 API", consumes = "application/json", produces = "application/json")
@RequiredArgsConstructor
@RestController
@RequestMapping("/mydata/oauth/2.0")
public class IndividualAuthController {
    private final IndividualAuthService individualAuthService;

    @ApiOperation(value = "개별인증 요청", notes = "리다이렉트할 위치를 302 code로 반환한다.")
    @GetMapping("/authorize")
    public ResponseEntity<Void> authorize(
            @ApiParam(value = "사용자 인증토큰", required = true) @RequestHeader(value = "X-Auth-Token") String authToken,
            @RequestBody AuthorizeReqDto authorizeReqDto) {
        Long authUserId = WebLogUtils.getAuthUserId();
        IndividualAuthResDto individualAuthResDto = individualAuthService.authorize(authUserId, authorizeReqDto);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(individualAuthResDto.getRedirectUri());
        responseHeaders.set(AuthConstants.STATE, individualAuthResDto.getState());
        return new ResponseEntity<>(responseHeaders, HttpStatus.FOUND);
    }

    @ApiOperation(value = "개별인증 콜백 요청", notes = "개별 인증이 완료되고, AuthorizationCode를 받고, 접근 토큰과 리프레시 토큰 발급받는다.")
    @GetMapping("/authorize/callback")
    public ResponseEntity<Void> authorizeCallback(AuthCallbackReqDto authCallbackReqDto) {
        individualAuthService.issueToken(authCallbackReqDto);

        if (!authCallbackReqDto.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "접근토큰 갱신", notes = "리프레시 토큰을 이용하여 접근 토큰을 갱신한다.")
    @PostMapping("/token")
    public ResponseEntity<Void> issueToken(
            @ApiParam(value = "사용자 인증토큰", required = true) @RequestHeader(value = "X-Auth-Token") String authToken,
            @RequestBody AuthTokenRenewReqDto authTokenRenewReqDto) {
        Long authUserId = WebLogUtils.getAuthUserId();
        individualAuthService.renew(authUserId, authTokenRenewReqDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "접근토큰 폐기", notes = "접근 토큰 및 리프레시 토큰을 폐기한다.")
    @PostMapping("/revoke")
    public ResponseEntity<Void> revoke(
            @ApiParam(value = "사용자 인증토큰", required = true) @RequestHeader(value = "X-Auth-Token") String authToken,
            @RequestBody AuthTokenRevokeReqDto authTokenRevokeReqDto) {
        Long authUserId = WebLogUtils.getAuthUserId();
        individualAuthService.revoke(authUserId, authTokenRevokeReqDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

package com.finda.server.mydata.auth.controller;

import com.finda.server.mydata.auth.dto.AuthProgressDto;
import com.finda.server.mydata.auth.dto.AuthSignatureDto;
import com.finda.server.mydata.auth.dto.UnsignedSignatureDto;
import com.finda.server.mydata.auth.service.IntegrationAuthService;
import com.finda.services.finda.common.log.WebLogUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Api(tags = "통합인증 API", description = "정보주체가 마이데이터사업자에게 개인신용정보를 전송을 요구를 위해 필요한 인증 API", consumes = "application/json", produces = "application/json")
@RequiredArgsConstructor
@RestController
@RequestMapping("/mydata/auth")
public class IntegrationAuthController {

    private final IntegrationAuthService integrationAuthService;

    @ApiOperation(value = "통합인증 전자서명 요청", notes = "백엔드 팀에서 채워줄 수 있는 전자서명 데이터만 생성하여 반환한다.")
    @PostMapping("/org/consent-info")
    public ResponseEntity<Map<String, Object>> requestUnsignedSignatureData(
            @ApiParam(value = "사용자 인증토큰", required = true) @RequestHeader(value = "X-Auth-Token") String authToken,
            @RequestBody List<OrgRequest> orgRequests) {
        Long authUserId = WebLogUtils.getAuthUserId();
        Map<String, Object> resultMap = new HashMap<>();

        List<String> orgCodes = parseOrgCodes(orgRequests);

        String session = UUID.randomUUID().toString();
        List<UnsignedSignatureDto> signedSignatures = integrationAuthService.createUnsignedSignature(session, authUserId, orgCodes);

        resultMap.put("status", "success");
        resultMap.put("message", "");
        resultMap.put("data", signedSignatures);
        resultMap.put("authSession", session);

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    private List<String> parseOrgCodes(List<OrgRequest> orgRequests) {
        return orgRequests.stream()
                .map(OrgRequest::getOrgCode)
                .collect(toList());
    }

    @ApiOperation(value = "통합인증 인증 요청", notes = "전자서명을 이용하여 통합인증을 병렬로 수행한다.")
    @PostMapping("/org/consent-info/authentication")
    public ResponseEntity<AuthProgressDto> authenticate(@ApiParam(value = "사용자 인증토큰", required = true) @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
                                                        @RequestBody AuthSignatureDto authSignatureDto) {
        Long authUserId = WebLogUtils.getAuthUserId();
        if (Objects.isNull(authSignatureDto.getAuthSession())) {
            throw new IllegalArgumentException(String.format("AuthSession is Required"));
        }
        AuthProgressDto authProgressDto = integrationAuthService.authenticate(authSignatureDto.getAuthSession(), authUserId, authSignatureDto);
        return new ResponseEntity<>(authProgressDto, HttpStatus.OK);
    }

    @ApiOperation(value = "통합인증 진행률 체크", notes = "현재 진행중인 통합인증 프로세스의 진행상황을 반환해준다.")
    @GetMapping("/org/consent-info/authentication/progress/{progressId}")
    public ResponseEntity<AuthProgressDto> checkProgress(@ApiParam(value = "사용자 인증토큰", required = true) @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
                                                         @PathVariable Long progressId) {
        Long authUserId = WebLogUtils.getAuthUserId();
        AuthProgressDto authProgressDto = integrationAuthService.getProgress(authUserId, progressId);
        return new ResponseEntity<>(authProgressDto, HttpStatus.OK);
    }

    @Getter
    @NoArgsConstructor
    public static class OrgRequest {
        private String orgCode;
    }
}

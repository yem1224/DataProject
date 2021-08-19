package com.finda.server.mydata.common.controller;

import com.finda.server.annotation.custom.RequestParamToDto;
import com.finda.server.mydata.common.dto.request.OrgReqDto;
import com.finda.server.mydata.common.dto.response.*;
import com.finda.server.mydata.common.service.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "공통 API", description = "정보제공자가 마이데이터사업자에게 API, 전송요구내역 전송을 위해 필요한 API", consumes = "application/json", produces = "application/json")
@RequiredArgsConstructor
@RestController
@RequestMapping("/mydata/common")
public class CommonController {

    private final CommonService commonService;

    @ApiOperation(value = "정보제공 공통001 API", notes = "정보제공자가 제공하는 정보제공 API 목록회신")
    @RequestMapping(value = "/{industry}/api", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<OrgApiResDto> searchApiList(@PathVariable("industry") String industry,
                                                      @ApiParam(value = "정보제공자기관코드", required = false) @RequestParamToDto OrgReqDto orgReqDto) {
        OrgApiResDto orgApiResDto = commonService.searchApiList(industry, orgReqDto);
        return new ResponseEntity<>(orgApiResDto,HttpStatus.OK);
    }

    @ApiOperation(value = "정보제공 공통002 API", notes = "정보주체가 특정한 전송요구내역 조회")
    @RequestMapping(value = "/{version}/{industry}/consents", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<OrgConsentsResDto> searchConsents(@PathVariable("industry") String industry,
                                                            @RequestHeader(value = "X-Auth-Token", required = true) String authToken,
                                                            @ApiParam(value = "정보제공자기관코드", required = false) @RequestParamToDto OrgReqDto orgReqDto ) {
        Long authUserId = Long.valueOf(00000123); // WebLogUtils.getAuthUserId();
        OrgConsentsResDto orgConsentsResDto = commonService.searchConsents(industry,authUserId,orgReqDto);
        return new ResponseEntity<>(orgConsentsResDto,HttpStatus.OK);
    }
}
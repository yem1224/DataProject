package com.finda.server.mydata.mgmts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.finda.server.annotation.custom.RequiredToken;
import com.finda.server.mydata.common.dto.response.ResBodyDto;
import com.finda.server.mydata.mgmts.dto.request.MgmtsAuthReqDto;
import com.finda.server.mydata.mgmts.dto.request.MgmtsConsentsReqDto;
import com.finda.server.mydata.mgmts.dto.response.MgmtsAuthResDto;
import com.finda.server.mydata.mgmts.dto.response.MgmtsConsentsResDto;
import com.finda.server.mydata.mgmts.dto.response.MgmtsOrgResDto;
import com.finda.server.mydata.mgmts.service.MgmtsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = "지원 API", description = "종합포털이 마이데이터 산업을 지원하기 위해 필요한 API", consumes = "application/json", produces = "application/json")
@RequiredArgsConstructor
@RestController
public class MgmtsController {

    private final MgmtsService mgmtsService;

    @ApiOperation(value = "지원001 API", notes = "종합포털이 사전에 발급해준 지원 API 호출용 자격증명을 이용하여 접근토큰 발급을 위한 API")
    @RequestMapping(value = "/mgmts/oauth/2.0/token/finda", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<MgmtsAuthResDto> getMgmtsAuthToken001() {
        MgmtsAuthResDto dto = mgmtsService.getMgmtsAuthToken001();
        return new ResponseEntity<MgmtsAuthResDto>(dto, HttpStatus.OK);
    }

    @ApiOperation(value = "지원002 API", notes = "타기관의 기관정보를 종합포털로부터 제공 받기 위한 API")
    @RequestMapping(value = "/v1/mgmts/orgs", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<MgmtsOrgResDto> insertOrgsInfo() {
        MgmtsOrgResDto mgmtsOrgResDto = mgmtsService.insertOrgsInfo();
        return new ResponseEntity<>(mgmtsOrgResDto,HttpStatus.OK);
    }

    @ApiOperation(value = "지원004 API", notes = "정기적 전송과 비정기적 전송을 구분(type parameter)하여 API 통계 자료를 전송")
    @RequestMapping(value = "/v1/mgmts/statistics/mydata", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ResBodyDto> sendStatistics() {
        ResBodyDto resBodyDto = mgmtsService.sendStatistics(2);
        return new ResponseEntity<>(resBodyDto, HttpStatus.OK);
    }

    @ApiOperation(value = "지원101 API", notes = "종합포털이 마이데이터사업자, 정보제공자 또는 중계기관에게 접근토큰 발급을 요청하기 위한 API")
    @RequestMapping(value = "/mgmts/oauth/2.0/token", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<MgmtsAuthResDto> getMgmtsOauthToken(
            @ApiParam(name = "Header", required = true) @RequestHeader(value = "x-api-tran-id", required = true) String apiTranId,
            @ApiParam(name = "Body", required = true) @RequestBody MgmtsAuthReqDto mgmtsAuthReqDto) {
        MgmtsAuthResDto mgmtsAuthResDto = mgmtsService.getMgmtsAuthToken(mgmtsAuthReqDto);
        return new ResponseEntity<>(mgmtsAuthResDto, HttpStatus.OK);
    }

    @RequiredToken
    @ApiOperation(value = "지원103 API", notes = "정보주체가 종합포털에서 요청 시, 종합포털은 본 API를 호출하여 마이데이터사업자로부 터 전송 요구 내역을 조회 API")
    @RequestMapping(value = "/v1/mgmts/consents", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<MgmtsConsentsResDto> getMgmtsConsents(
    		@ApiParam(name = "Header", required = true) @RequestHeader (value = "Authorization", required = true) String authorization,
            @ApiParam(name = "Header", required = true) @RequestHeader (value = "x-api-tran-id", required = true) String apiTranId,
            @ApiParam(name = "Body", required = true) @RequestBody MgmtsConsentsReqDto mgmtsConsentsReqDto) {
        MgmtsConsentsResDto mgmtsConsentsResDto = mgmtsService.getMgmtsConsents(mgmtsConsentsReqDto);
        return new ResponseEntity<MgmtsConsentsResDto>(mgmtsConsentsResDto, HttpStatus.OK);
    }
/*

    @ApiOperation(value = "지원104 API", notes = "종합포털에서 통계자료 재전송을 요청하기 위한 API")
    @RequestMapping(value = "/req-statistics", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Void> resendStatistics() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
*/

}
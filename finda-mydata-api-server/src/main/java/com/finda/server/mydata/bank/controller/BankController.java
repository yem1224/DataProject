package com.finda.server.mydata.bank.controller;

import com.finda.server.annotation.custom.RequestParamToDto;
import com.finda.server.mydata.bank.dto.request.*;
import com.finda.server.mydata.bank.dto.response.*;
import com.finda.server.mydata.bank.service.BankService;
import com.finda.services.finda.common.log.WebLogUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "은행 API", description = "은행업권 정보제공자가 마이데이터사업자에게 개인신용정보를 전송하기 위해 필요한 API", consumes = "application/json", produces = "application/json")
@RequiredArgsConstructor
@RestController
@RequestMapping("/mydata/bank")
public class BankController {

    private final BankService bankService;

    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<BankAcctResDto> searchLoanAccountList(@RequestHeader(value = "X-Auth-Token", required = true) String authToken,
                                                                @RequestParamToDto BankAcctReqDto bankAcctReqDto) {
        Long authUserId = WebLogUtils.getAuthUserId();
        BankAcctResDto bankAcctResDto = bankService.searchLoanAccountList(authUserId,bankAcctReqDto);
        return new ResponseEntity<>(bankAcctResDto,HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts/loan/basic", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<BankLoanBaseResDto> searchLoanAccountBasic(@RequestHeader(value = "X-Auth-Token", required = true) String authToken,
                                                                     @RequestParamToDto BankLoanReqDto bankLoanReqDto) {
        Long authUserId = Long.valueOf(00000123); // WebLogUtils.getAuthUserId();
        BankLoanBaseResDto bankLoanBaseResDto = bankService.searchLoanAccountBasic(authUserId,bankLoanReqDto);
        return new ResponseEntity<>(bankLoanBaseResDto,HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts/loan/detail", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<BankLoanDtlsResDto> searchLoanAccountDetail(@RequestHeader(value = "X-Auth-Token", required = true) String authToken,
                                                                      @RequestParamToDto BankLoanReqDto bankLoanReqDto) {
        Long authUserId = Long.valueOf(00000123); // WebLogUtils.getAuthUserId();
        BankLoanDtlsResDto bankLoanDtlsResDto = bankService.searchLoanAccountDetail(authUserId,bankLoanReqDto);
        return new ResponseEntity<>(bankLoanDtlsResDto,HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts/loan/transactions", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<BankLoanTransResDto> searchLoanAccountTranPtcl(@RequestHeader(value = "X-Auth-Token", required = true) String authToken,
                                                                         @RequestParamToDto BankLoanTransReqDto bankLoanTransReqDto) {
        Long authUserId = Long.valueOf(00000123); // WebLogUtils.getAuthUserId();
        BankLoanTransResDto bankLoanTransResDto = bankService.searchLoanAccountTranPtcl(authUserId,bankLoanTransReqDto);
        return new ResponseEntity<>(bankLoanTransResDto,HttpStatus.OK);
    }
}
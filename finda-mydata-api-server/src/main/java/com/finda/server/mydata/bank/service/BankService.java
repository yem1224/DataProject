package com.finda.server.mydata.bank.service;

import com.finda.server.mydata.bank.dto.request.*;
import com.finda.server.mydata.bank.dto.response.*;

public interface BankService {
    BankAcctResDto searchLoanAccountList(Long authUserId,BankAcctReqDto bankAcctReqDto);

    BankLoanBaseResDto searchLoanAccountBasic(Long authUserId,BankLoanReqDto bankLoanReqDto);

    BankLoanDtlsResDto searchLoanAccountDetail(Long authUserId,BankLoanReqDto bankLoanReqDto);

    BankLoanTransResDto searchLoanAccountTranPtcl(Long authUserId,BankLoanTransReqDto bankLoanTransReqDto);
}

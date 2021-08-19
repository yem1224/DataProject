package com.finda.server.mydata.bank.service.impl;

import com.finda.server.mydata.transaction.ApiTransactionExecutor;
import com.finda.server.mydata.transaction.ApiTransactionRequest;
import com.finda.server.mydata.bank.domain.entity.*;
import com.finda.server.mydata.bank.domain.repository.*;
import com.finda.server.mydata.bank.dto.request.*;
import com.finda.server.mydata.bank.dto.response.*;
import com.finda.server.mydata.bank.service.BankService;
import com.finda.server.mydata.common.code.ApiType;
import com.finda.server.mydata.common.service.ApiUrlGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
public class BankServiceImpl implements BankService {

    private final ApiTransactionExecutor apiTransactionExecutor;
    private final ApiUrlGenerator apiUrlGenerator;
    private final AccountBaseRepository accountBaseRepository;
    private final LoanBaseRepository loanBaseRepository;
    private final LoanDtlsRepository loanDtlsRepository;
    private final LoanTranPtclRepository loanTranPtclRepository;
    private final LoanIntPtclRepository loanIntPtclRepository;

    @Override
    public BankAcctResDto searchLoanAccountList(Long authUserId, BankAcctReqDto reqDto) {
        ApiTransactionRequest<Void> req = new ApiTransactionRequest<>(
                apiUrlGenerator.getHeaders(authUserId)
                , HttpMethod.GET
                , apiUrlGenerator.getUrl(reqDto.getOrgCode(), ApiType.API_TYPE_BA01.getCode())
                , reqDto);

        ResponseEntity<BankAcctResDto> res = null;
        BankAcctResDto resDto = null;
        try {
            res = apiTransactionExecutor.execute(req, BankAcctResDto.class).get();
            if (res.getStatusCode().is2xxSuccessful()) {
                resDto = res.getBody();
            }
            this.setAccountBase(authUserId,resDto);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resDto;
    }

    @Override
    public BankLoanBaseResDto searchLoanAccountBasic(Long authUserId,BankLoanReqDto reqDto) {
        ApiTransactionRequest<Void> req = new ApiTransactionRequest<>(
                 apiUrlGenerator.getHeaders(authUserId)
                , HttpMethod.POST
                , apiUrlGenerator.getUrl(reqDto.getOrgCode(), ApiType.API_TYPE_BA21.getCode())
                , reqDto);

        ResponseEntity<BankLoanBaseResDto> res = null;
        BankLoanBaseResDto resDto = null;
        try {
            res = apiTransactionExecutor.execute(req, BankLoanBaseResDto.class).get();
            if (res.getStatusCode().is2xxSuccessful()) {
                resDto = res.getBody();
            } else {
                throw new RuntimeException("");
            }
            this.setLoanBase(authUserId,reqDto, resDto);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resDto;
    }

    @Override
    public BankLoanDtlsResDto searchLoanAccountDetail(Long authUserId, BankLoanReqDto reqDto) {
        ApiTransactionRequest<Void> req = new ApiTransactionRequest<>(
                apiUrlGenerator.getHeaders(authUserId)
                , HttpMethod.POST
                , apiUrlGenerator.getUrl(reqDto.getOrgCode(), ApiType.API_TYPE_BA22.getCode())
                , reqDto);
        ResponseEntity<BankLoanDtlsResDto> res = null;
        BankLoanDtlsResDto resDto = null;
        try {
            res = apiTransactionExecutor.execute(req, BankLoanDtlsResDto.class).get();
            if (res.getStatusCode().is2xxSuccessful()) {
                resDto = res.getBody();
            } else {
                throw new RuntimeException("");
            }
            this.setLoanDtls(reqDto, resDto);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resDto;
    }

    @Override
    public BankLoanTransResDto searchLoanAccountTranPtcl(Long authUserId, BankLoanTransReqDto reqDto) {
        ApiTransactionRequest<Void> req = new ApiTransactionRequest<>(
                 apiUrlGenerator.getHeaders(authUserId)
                , HttpMethod.POST
                , apiUrlGenerator.getUrl(reqDto.getOrgCode(), ApiType.API_TYPE_BA23.getCode())
                , reqDto);
        ResponseEntity<BankLoanTransResDto> res = null;
        BankLoanTransResDto resDto = null;
        try {
            res = apiTransactionExecutor.execute(req, BankLoanTransResDto.class).get();
            if (res.getStatusCode().is2xxSuccessful()) {
                resDto = res.getBody();
            } else {
                throw new RuntimeException("");
            }
            this.setLoanTrans(reqDto, resDto);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resDto;
    }


    /**
     * 계좌정보 setting
     *
     * @param resDto
     */
    private void setAccountBase(Long userId, BankAcctResDto resDto) {
        int acctCnt = resDto.getAccountCnt();
        if (acctCnt != 0) {
            List<BankAcctResDto.AcctInfo> accountList = resDto.getAccountList();
            for (int i = 0; i < acctCnt; i++) {
                BankAcctResDto.AcctInfo acctInfo = accountList.get(i);
                String acctNum = acctInfo.getAccountNum();
                String seqNo = acctInfo.getSeqno();
                AccountBaseEntity accountBaseEntity = accountBaseRepository.findByUniKey(acctNum, seqNo, userId);
                if (accountBaseEntity == null) {
                    BeanUtils.copyProperties(acctInfo, accountBaseEntity);
                    accountBaseRepository.save(accountBaseEntity);
                } else {

                }
            }
        }
    }

    /**
     * 대출기본정보 setting
     *
     * @param reqDto,resDto
     */
    private void setLoanBase(Long userId,BankLoanReqDto reqDto, BankLoanBaseResDto resDto) {
        String acctNum = reqDto.getAccountNum();
        String seqNo = reqDto.getSeqno();

        LoanBaseEntity loanBaseEntity = loanBaseRepository.findByUniKey(acctNum, seqNo, userId);
        if (loanBaseEntity == null) {
            BeanUtils.copyProperties(resDto, loanBaseEntity);
            loanBaseRepository.save(loanBaseEntity);
        } else {
        }
    }

    /**
     * 대출상세정보 setting
     *
     * @param reqDto, esDto
     */
    private void setLoanDtls(BankLoanReqDto reqDto, BankLoanDtlsResDto resDto) {
        String acctNum = reqDto.getAccountNum();
        String seqNo = reqDto.getSeqno();

        LoanDtlsEntity loanDtlsEntity = loanDtlsRepository.findByUniKey(acctNum, seqNo);
        if (loanDtlsEntity == null) {
            BeanUtils.copyProperties(resDto, loanDtlsEntity);
            loanDtlsRepository.save(loanDtlsEntity);
        } else {
        }
    }

    /**
     * 대출 거래내역 및 이자내역 setting
     *
     * @param reqDto, resDto
     */
    private void setLoanTrans(BankLoanTransReqDto reqDto, BankLoanTransResDto resDto) {
        String acctNum = reqDto.getAccountNum();
        String seqNo = reqDto.getSeqno();
        int transCnt = resDto.getTransCnt();

        List<LoanTranPtclEntity> loanTranPtclList = loanTranPtclRepository.findByAcctInfo(acctNum, seqNo);
        List<LoanIntPtclEntity> loanIntPtclList = loanIntPtclRepository.findByAcctInfo(acctNum, seqNo);
        LoanTranPtclEntity loanTranPtclEntity = null;
        LoanIntPtclEntity loanIntPtclEntity = null;

        if (transCnt != 0 && loanTranPtclList == null) {
            List<BankLoanTransResDto.TransInfo> transList = resDto.getTransList();
            for (int i = 0; i < transCnt; i++) {
                BankLoanTransResDto.TransInfo transInfo = transList.get(i);
                BeanUtils.copyProperties(transInfo, loanTranPtclEntity);
                loanTranPtclRepository.save(loanTranPtclEntity);

                int intCnt = transInfo.getIntCnt();
                if (intCnt != 0 && loanIntPtclList == null) {
                    List<BankLoanTransResDto.IntInfo> intList = transInfo.getIntList();
                    for (int j = 0; j < intCnt; j++) {
                        BankLoanTransResDto.IntInfo intInfo = intList.get(j);
                        BeanUtils.copyProperties(intInfo, loanIntPtclEntity);
                        loanIntPtclRepository.save(loanIntPtclEntity);
                    }
                }
            }
        }
    }
}

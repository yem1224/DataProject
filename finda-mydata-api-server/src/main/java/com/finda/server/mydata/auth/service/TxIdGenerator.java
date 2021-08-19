package com.finda.server.mydata.auth.service;

import com.finda.server.mydata.mgmts.dto.OrgInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Component
public class TxIdGenerator {
    private static final String IDENTIFIER = "MD";
    private static final String DELIMITER = "_";
    private static final DateTimeFormatter YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Value("${mydata.code.findaOrgCode}")
    private String findaOrgCode;

    private final TxIdSeqGenerator txIdSeqGenerator;

    public String issuedAt(LocalDateTime dateTime, String orgCode, String authOrgCode) {
        final String transactionSerialNumber = txIdSeqGenerator.generate();
        final String requestTime = dateTime.format(YYYYMMDDHHMMSS);

        return String.join(DELIMITER,
                IDENTIFIER,
                findaOrgCode,
                orgCode,
                authOrgCode,
                requestTime,
                transactionSerialNumber);
    }
}

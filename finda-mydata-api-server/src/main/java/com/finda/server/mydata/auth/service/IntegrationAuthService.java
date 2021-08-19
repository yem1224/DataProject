package com.finda.server.mydata.auth.service;

import com.finda.server.mydata.auth.dto.AuthProgressDto;
import com.finda.server.mydata.auth.dto.AuthSignatureDto;
import com.finda.server.mydata.auth.dto.UnsignedSignatureDto;

import java.util.List;

public interface IntegrationAuthService {
    List<UnsignedSignatureDto> createUnsignedSignature(String session, Long userId, List<String> orgCodes);

    AuthProgressDto authenticate(String session, Long userId, AuthSignatureDto authSignatureDto);

    AuthProgressDto getProgress(Long userId, Long progressId);
}

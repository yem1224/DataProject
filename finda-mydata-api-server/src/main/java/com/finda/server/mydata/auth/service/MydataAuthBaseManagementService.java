package com.finda.server.mydata.auth.service;

import com.finda.server.mydata.auth.domain.entity.MydataAuthBaseEntity;
import com.finda.server.mydata.auth.dto.response.AuthTokenDto;
import com.finda.server.mydata.auth.dto.response.AuthRenewResDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MydataAuthBaseManagementService {

    Optional<MydataAuthBaseEntity> findByUserIdAndOrgCode(String userId, String orgCode);

    List<MydataAuthBaseEntity> findAllByUserCiAndOrgCodes(String userCi, List<String> orgCodes);

    void generate(String userCi, String orgCode);

    void generate(String userCi, List<String> orgCodes);

    void issueToken(String userCi, String orgCode, AuthTokenDto authTokenDto, LocalDateTime authTime, String caOrgCode);

    void failedIssueToken(String userCi, String orgCode);

    void deleteByAccessToken(String accessToken);

    void deleteByRefreshToken(String refreshToken);

    void renew(MydataAuthBaseEntity mydataAuthBaseEntity, AuthRenewResDto authRenewResDto);
}

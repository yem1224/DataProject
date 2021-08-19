package com.finda.server.mydata.auth.domain.repository;

import com.finda.server.mydata.auth.domain.entity.MydataAuthBaseEntity;

import java.util.List;

public interface MydataAuthBaseCustomRepository {
    List<MydataAuthBaseEntity> findAllByUserCiAndOrgCodes(String userCi, List<String> orgCodes);
}

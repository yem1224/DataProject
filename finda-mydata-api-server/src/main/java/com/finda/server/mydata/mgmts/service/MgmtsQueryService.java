package com.finda.server.mydata.mgmts.service;

import com.finda.server.mydata.mgmts.dto.OrgInfoDto;

import java.util.List;

public interface MgmtsQueryService {
    String findDomainByOrgCode(String orgCode);
    List<OrgInfoDto> findAllOrgInfoByOrgCode(List<String> orgCodes);
}

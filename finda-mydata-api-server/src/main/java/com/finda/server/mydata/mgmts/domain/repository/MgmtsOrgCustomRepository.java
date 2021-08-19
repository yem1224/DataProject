package com.finda.server.mydata.mgmts.domain.repository;

import com.finda.server.mydata.mgmts.dto.OrgInfoDto;

import java.util.List;
import java.util.Optional;

public interface MgmtsOrgCustomRepository {
    List<OrgInfoDto> findOrgInfosByOrgCodes(List<String> orgCodes);

    Optional<OrgInfoDto> findOrgInfoByOrgCode(String orgCode);

    Optional<String> findOrgCodeByCertIssuerDn(String certIssuerDn);
}

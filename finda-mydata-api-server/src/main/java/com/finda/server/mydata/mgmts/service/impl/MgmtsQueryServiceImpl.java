package com.finda.server.mydata.mgmts.service.impl;

import com.finda.server.mydata.mgmts.domain.entity.OrgEntity;
import com.finda.server.mydata.mgmts.domain.repository.MgmtsOrgRepository;
import com.finda.server.mydata.mgmts.dto.OrgInfoDto;
import com.finda.server.mydata.mgmts.service.MgmtsQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
class MgmtsQueryServiceImpl implements MgmtsQueryService {

    private final MgmtsOrgRepository mgmtsOrgRepository;

    @Override
    public String findDomainByOrgCode(String orgCode) {
        OrgEntity orgEntity = mgmtsOrgRepository.findOrgEntityByOrgCode(orgCode)
                .orElseThrow(() -> new EntityNotFoundException("OrgCode is not exists."));

        return orgEntity.getDomain();
    }

    @Override
    public List<OrgInfoDto> findAllOrgInfoByOrgCode(List<String> orgCodes) {
        return mgmtsOrgRepository.findOrgInfosByOrgCodes(orgCodes);
    }
}

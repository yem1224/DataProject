package com.finda.server.mydata.common.service;

import com.finda.server.mydata.common.domain.entity.ApiDomainCode;
import com.finda.server.mydata.common.domain.entity.OrgApiInfoEntity;
import com.finda.server.mydata.common.domain.repository.OrgApiInfoRepository;
import com.finda.server.mydata.mgmts.domain.entity.OrgEntity;
import com.finda.server.mydata.mgmts.domain.repository.MgmtsOrgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.net.URI;

@RequiredArgsConstructor
@Component
public class ApiUrlGenerator {
    //final private MydataUserRepository mydataUserRepository;
    final private OrgApiInfoRepository orgApiInfoRepository;
    final private MgmtsOrgRepository mgmtsOrgRepository;

    public MultiValueMap<String, String> getHeaders(Long authUserId) {
        String authorization = "ddddddddddd"; //mydataUserRepository.findByUserId(authUserId);
        MultiValueMap<String, String> header = new HttpHeaders();
        header.add("Authorization", authorization);
        return header;
    }

    /**
     * 정보제공자 도메인 및 uri 정보 생성
     * @param orgCode, apiCode, industry
     * @return url
     */
    public ApiDomainCode getUrl(String orgCode,String apiCode){
        OrgEntity orgEntity =  mgmtsOrgRepository.findByUniqueKey(orgCode);
        String domain = orgEntity.getDomain();

        OrgApiInfoEntity orgApiInfoEntity = orgApiInfoRepository.findUriByApiCode(orgCode,apiCode);
        String uri = orgApiInfoEntity.getApiUri();
        String version = "/"+ orgApiInfoEntity.getVersion();

        return new ApiDomainCode(domain,version+uri,apiCode);
    }
}

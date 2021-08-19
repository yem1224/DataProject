package com.finda.server.mydata.mgmts.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finda.server.mydata.auth.domain.repository.MydataTranReqPtclRepository;
import com.finda.server.mydata.auth.domain.repository.MydataUserRepository;
import com.finda.server.mydata.mgmts.domain.repository.MgmtOrgMapRepository;
import com.finda.server.mydata.mgmts.domain.repository.MgmtTokenRepository;
import com.finda.server.mydata.mgmts.domain.repository.MgmtsOrgIpRepository;
import com.finda.server.mydata.mgmts.domain.repository.MgmtsOrgRepository;
import com.finda.server.mydata.mgmts.dto.response.MgmtsOrgResDto;
import com.finda.server.mydata.transaction.ApiTransactionExecutor;
import com.finda.server.mydata.transaction.domain.repository.ApiTranHistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MgmtsServiceImplTest {

        MgmtsServiceImpl mgmtsService;

        @Test
        public void testInsert(){
                MgmtsOrgResDto resDto = new MgmtsOrgResDto();
               // mgmtsService.setOrgIpInfo();
        }

}
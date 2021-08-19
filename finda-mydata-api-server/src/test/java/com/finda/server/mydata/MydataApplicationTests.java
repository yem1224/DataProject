package com.finda.server.mydata;

import com.finda.server.mydata.mgmts.service.impl.MgmtsServiceImplTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MydataApplicationTests {

    @Test
    void contextLoads() {
        MgmtsServiceImplTest m = new MgmtsServiceImplTest();
        m.testInsert();
    }

}

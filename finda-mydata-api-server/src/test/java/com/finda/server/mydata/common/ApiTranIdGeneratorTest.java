package com.finda.server.mydata.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ApiTranIdGeneratorTest {

    @Autowired
    private ApiTranIdGenerator apiTranIdGenerator;

    @Value("${mydata.code.findaOrgCode}")
    private String findaOrgCode;

    @Test
    @DisplayName("한 건의 ApiTranId 를 발급 테스트")
    void issueTest() {
        String apiTranId = apiTranIdGenerator.issue();
        assertNotNull(apiTranId);
    }

    @Test
    @DisplayName("여러 건의 ApiTranId 를 발급하면 중복되지 않은 값이 순차적으로 반환된다")
    void issueListTest() {
        //given
        List<String> expectedApiTranIds = Arrays.asList(
                findaOrgCode + "M" + "000000001",
                findaOrgCode + "M" + "000000002",
                findaOrgCode + "M" + "000000003",
                findaOrgCode + "M" + "000000004"
        );

        //when
        List<String> apiTranIds = apiTranIdGenerator.issue(4);

        //then
        assertArrayEquals(expectedApiTranIds.toArray(), apiTranIds.toArray());
    }
}

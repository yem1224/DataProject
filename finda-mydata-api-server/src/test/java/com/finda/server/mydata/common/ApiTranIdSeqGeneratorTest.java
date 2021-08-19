package com.finda.server.mydata.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApiTranIdSeqGeneratorTest {

    @Autowired
    ApiTranIdSeqGenerator apiTranIdSeqGenerator;

    @Test
    @DisplayName("부여번호는 9자리이다.")
    void generateTest() {
        int expectedLength = 9;
        String one = apiTranIdSeqGenerator.generate();
        assertThat(one.length()).isEqualTo(expectedLength);
    }
}
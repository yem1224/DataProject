package com.finda.server.mydata.auth.service;

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
class TxIdSeqGeneratorTest {

    @Autowired
    TxIdSeqGenerator txIdSeqGenerator;

    @Test
    @DisplayName("일련번호의 길이는 12자리이다.")
    void generateTest() {
        int expectedLength = 12;
        String one = txIdSeqGenerator.generate();
        assertThat(one.length()).isEqualTo(expectedLength);
    }
}
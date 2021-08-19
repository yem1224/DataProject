package com.finda.server.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

class MultiValueMapConverterTest {

    ObjectMapper objectMapper;
    MultiValueMap<String, String> result;

    @BeforeEach
    void setUp() {
        objectMapper = new Jackson2ObjectMapperBuilder()
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .modules(new JavaTimeModule())
                .timeZone("Asia/Seoul")
                .build();
    }

    @AfterEach
    void after() {
        result = null;
    }

    @Test
    @DisplayName("String/int/boolean/LocalDateTime/enum 변환 테스트")
    void multiValueMapConvertTest() {
        //given
        String expectedName = "hello";
        int expectedAmount = 1000;
        boolean expectedCheck = true;
        LocalDateTime expectedLocalDateTime = LocalDateTime.of(2021, 5, 13, 19, 42, 5);
        MultiValueMapConvertTestDto.Status expectedStatus = MultiValueMapConvertTestDto.Status.SUCCESS;

        MultiValueMapConvertTestDto dto = MultiValueMapConvertTestDto.builder()
                .name(expectedName)
                .amount(expectedAmount)
                .check(expectedCheck)
                .localDateTime(expectedLocalDateTime)
                .status(expectedStatus)
                .build();

        //when
        result = MultiValueMapConverter.convert(objectMapper, dto);

        //then
        assertThat(result.size()).isEqualTo(5);
        assertValue("name", expectedName);
        assertValue("amount", String.valueOf(expectedAmount));
        assertValue("check", String.valueOf(expectedCheck));
        assertValue("localDateTime", toStringDateTime(expectedLocalDateTime));
        assertValue("status", expectedStatus.name());
    }

    @Test
    @DisplayName("JsonProperty 필드명 변경 테스트")
    void multiValueMapPropertyNameTest() {
        //given
        String expectedName = "hello";
        int expectedAmount = 1000;
        boolean expectedCheck = true;
        LocalDateTime expectedLocalDateTime = LocalDateTime.of(2021, 5, 13, 19, 42, 5);
        MultiValueMapPropertyNameTestDto.Status expectedStatus = MultiValueMapPropertyNameTestDto.Status.SUCCESS;

        MultiValueMapPropertyNameTestDto dto = MultiValueMapPropertyNameTestDto.builder()
                .name(expectedName)
                .amount(expectedAmount)
                .check(expectedCheck)
                .localDateTime(expectedLocalDateTime)
                .status(expectedStatus)
                .build();

        //when
        result = MultiValueMapConverter.convert(objectMapper, dto);

        //then
        assertThat(result.size()).isEqualTo(5);
        assertValue("na", expectedName);
        assertValue("amount", String.valueOf(expectedAmount));
        assertValue("check", String.valueOf(expectedCheck));
        assertValue("lo", toStringDateTime(expectedLocalDateTime));
        assertValue("st", expectedStatus.name());
    }

    private void assertValue(String name, String expectedName) {
        assertThat(getValue(result, name)).isEqualTo(expectedName);
    }

    private String getValue(MultiValueMap<String, String> result, String name) {
        return result.get(name).get(0);
    }

    private String toStringDateTime(LocalDateTime localDateTime) {
        String DEFAULT_PATTERN = "yyyyMMddHHmmss";
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_PATTERN);
        try {
            return FORMATTER.format(localDateTime);
        } catch (Exception e) {
            String message = "입력 받은 LocalDateTime parse에 실패했습니다. dateTime=" + localDateTime;
            throw new IllegalArgumentException(message);
        }
    }
}
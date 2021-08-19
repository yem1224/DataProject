package com.finda.server.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MultiValueMapConverter {

    public static MultiValueMap<String, String> convert(ObjectMapper objectMapper, Object dto) {
        try {
            MultiValueMap<String, String> formsData = new LinkedMultiValueMap<>();
            Map<String, String> map = objectMapper.convertValue(dto, new TypeReference<Map<String, String>>() {
            });
            formsData.setAll(map);

            return formsData;
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed Object convert", e);
        }
    }
}

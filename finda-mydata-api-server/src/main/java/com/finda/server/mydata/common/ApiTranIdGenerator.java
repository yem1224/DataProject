package com.finda.server.mydata.common;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

import static java.lang.String.join;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Component
public class ApiTranIdGenerator {
    private static final String CREATION_CLASSIFICATION_CODE = "M";

    @Value("${mydata.code.findaOrgCode}")
    private String findaOrgCode;

    private final ApiTranIdSeqGenerator apiTranIdSeqGenerator;

    public String issue() {
        return join("", findaOrgCode, CREATION_CLASSIFICATION_CODE, apiTranIdSeqGenerator.generate());
    }

    public List<String> issue(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> issue())
                .collect(toList());
    }
}

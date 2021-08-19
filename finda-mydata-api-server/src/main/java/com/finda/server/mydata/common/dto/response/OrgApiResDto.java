package com.finda.server.mydata.common.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @since 2021. 06. 29
 * 공통 001 API 응답메시지
 */
@NoArgsConstructor
@Data
public class OrgApiResDto extends ResBodyDto{

    private String version;

    @JsonProperty("min_version")
    private String minVersion;

    @JsonProperty("api_cnt")
    private int apiCnt;

    @JsonProperty("api_list")
    private List<OrgApiResDto.ApiInfo> apiList;

    @Data
    public static class ApiInfo {
        @JsonProperty("api_code")
        private String apiCode;

        @JsonProperty("api_uri")
        private String apiUri;
    }
}

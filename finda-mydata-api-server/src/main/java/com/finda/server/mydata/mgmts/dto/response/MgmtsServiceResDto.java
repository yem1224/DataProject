package com.finda.server.mydata.mgmts.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finda.server.mydata.common.dto.response.ResBodyDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @since 2021. 06. 11
 * 지원 003 API 응답메시지
 */
@NoArgsConstructor
@Data
public class MgmtsServiceResDto extends ResBodyDto {
    @JsonProperty("org_cnt")
    private int orgCnt;

    @JsonProperty("org_list")
    private List<ServiceOrgInfo> orgList;

    @Data
    public static class ServiceOrgInfo {
        @JsonProperty("op_type")
        private String opType;

        @JsonProperty("org_code")
        private String orgCode;

        @JsonProperty("service_cnt")
        private int serviceCnt;

        @JsonProperty("service_list")
        private List<ServiceInfo> serviceList;
    }

    @Data
    public static class ServiceInfo {
        @JsonProperty("service_name")
        private String serviceName;

        @JsonProperty("op_type")
        private String opType;

        @JsonProperty("cliend_id")
        private String cliendId;

        @JsonProperty("cliend_secret")
        private String cliendSecret;

        @JsonProperty("redirect_uri_cnt")
        private int redirectUriCnt;

        @JsonProperty("redirect_uri_list")
        private List<RedirectUriInfo> redirectUriList;

        @JsonProperty("app_scheme_cnt")
        private int appSchemeCnt;

        @JsonProperty("app_scheme_list")
        private List<AppSchemeInfo> appSchemeList;
    }

    @Data
    public class RedirectUriInfo {
        @JsonProperty("redirect_uri")
        private String redirectUri;
    }

    @Data
    public class AppSchemeInfo {
        @JsonProperty("app_scheme")
        private String appScheme;
    }
}

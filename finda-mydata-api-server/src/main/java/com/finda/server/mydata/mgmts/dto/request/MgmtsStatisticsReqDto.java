package com.finda.server.mydata.mgmts.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @since 2021. 06. 11
 * 지원 004 API 요청메시지
 */
@NoArgsConstructor
@Data
public class MgmtsStatisticsReqDto {

    private int type;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("inquiry_date")
    private String inquiryDate;

    @JsonProperty("stat_date_cnt")
    private int statDateCnt;

    @JsonProperty("stat_date_list")
    private List<StatDateInfo> statDateList;

    @Data
    public static class StatDateInfo {
        @JsonProperty("stat_date")
        private String statDate;

        @JsonProperty("org_cnt")
        private int orgCnt;

        @JsonProperty("org_list")
        private List<StatOrgInfo> orgList;
    }

    @Data
    public static class StatOrgInfo {
        @JsonProperty("org_code")
        private String orgCode;

        @JsonProperty("consent_new")
        private int consentNew;

        @JsonProperty("consent_revoke")
        private int consentRevoke;

        @JsonProperty("consent_own")
        private int consentOwn;

        @JsonProperty("api_type_cnt")
        private int apiTypeCnt;

        @JsonProperty("api_type_list")
        private List<ApiTypeInfo> apiTypeList;
    }

    @Data
    public static class ApiTypeInfo {
        @JsonProperty("api_type")
        private String apiType;

        @JsonProperty("tm_slot_cnt")
        private int tmSlotCnt;

        @JsonProperty("tm_slot_list")
        private List<TmSlotInfo> tmSlotList;
    }

    @Data
    public static class TmSlotInfo {
        @JsonProperty("tm_slot")
        private String tmSlot;

        @JsonProperty("rsp_avg")
        private String rspAvg;

        @JsonProperty("rsp_total")
        private String rspTotal;

        @JsonProperty("rsp_stdev")
        private String rspStdev;

        @JsonProperty("success_api_cnt")
        private String successApiCnt;

        @JsonProperty("fail_api_cnt")
        private String failApiCnt;
    }
}

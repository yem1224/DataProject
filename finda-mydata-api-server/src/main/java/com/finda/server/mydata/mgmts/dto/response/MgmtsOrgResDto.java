package com.finda.server.mydata.mgmts.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finda.server.mydata.common.code.OrgType;
import com.finda.server.mydata.common.dto.response.ResBodyDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @since 2021. 06. 11
 * 지원 002 API 응답메시지
 */
@NoArgsConstructor
@Data
public class MgmtsOrgResDto extends ResBodyDto {
    @JsonProperty("org_cnt")
    private int orgCnt;

    @JsonProperty("org_list")
    private List<OrgInfo> orgList;

    @Data
    public static class OrgInfo {
        @JsonProperty("op_type")
        private String opType;

        @JsonProperty("org_code")
        private String orgCode;

        @JsonProperty("org_type")
        private String orgType;

        @JsonProperty("is_rcv_org")
        private boolean isRcvOrg;

        @JsonProperty("org_name")
        private String orgName;

        @JsonProperty("org_regno")
        private String orgRegno;

        @JsonProperty("corp_regno")
        private String corpRegno;

        @JsonProperty("serial_num")
        private String serialNum;

        private String address;

        private String domain;

        @JsonProperty("domain_ip_cnt")
        private int domainIpCnt;

        @JsonProperty("domain_ip_list")
        private List<DomainIpInfo> domainIpList;

        @JsonProperty("relay_org_code")
        private String relayOrgCode;

        private String industry;

        @JsonProperty("auth_type")
        private String authType;

        @JsonProperty("cert_issuer_dn")
        private String certIssuerDn;

        @JsonProperty("cert_oid")
        private String certOid;

        @JsonProperty("ip_cnt")
        private int ipCnt;

        @JsonProperty("ip_list")
        private List<IpInfo> ipList;
    }

    @Data
    public static class DomainIpInfo {
        @JsonProperty("domain_ip")
        private String domainIp;
    }

    @Data
    public static class IpInfo {
        private String ip;
    }
}

package com.finda.server.mydata.mgmts.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finda.server.mydata.common.code.OrgType;
import com.finda.server.mydata.mgmts.domain.entity.OrgEntity;
import com.finda.server.mydata.mgmts.domain.entity.OrgMapEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Data
public class MgmtsInterworkListResDto {


    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private List<orgData> orgData = new ArrayList<>();

    @AllArgsConstructor
    @Data
    private static class orgData{
        @JsonProperty("industry")
        @ApiModelProperty(notes = "업권구분", example = "bank")
        private String industry;

        @JsonProperty("orglist")
        private List<OrgInfo> orgList;
    }

    @AllArgsConstructor
    @Data
    private static class OrgInfo {

        @JsonProperty("orgCode")
        @ApiModelProperty(notes = "금융기관코드", example = "A1AAAD0000")
        private String orgCode;

        @JsonProperty("orgName")
        @ApiModelProperty(notes = "금융기관명", example = "한국산업은행")
        private String DisplayName;

        @JsonProperty("orgLogoUrl")
        @ApiModelProperty(notes = "로고url", example = "www.logo.com")
        private String BankLogUrl;

    }


    public void setOrgData(List<OrgMapEntity> listOrgMapEntity,String industry) {

        if (!listOrgMapEntity.isEmpty()) {
            List<OrgInfo> newlist = new ArrayList<>();
            listOrgMapEntity.forEach(s -> {
                OrgInfo orgInfo = new OrgInfo(s.getOrgEntity().getOrgCode(), s.getDisplayName(), s.getBankLogUrl());
                newlist.add(orgInfo);
            });
            this.orgData.add(new orgData(industry, newlist));
        }

    }
}

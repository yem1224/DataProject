package com.finda.server.mydata.common.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrgReqDto {

    @JsonProperty("org_code")
    private String orgCode;
}

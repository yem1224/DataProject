package com.finda.server.mydata.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finda.server.mydata.auth.dto.UnsuccessfulDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IndividualAuthRevokeResDto extends UnsuccessfulDto {
    private static final String SUCCESS_CODE = "00000";

    @JsonProperty("rsp_code")
    private String rspCode;

    @JsonProperty("rsp_msg")
    private String rspMsg;

    public boolean isSuccess() {
        if (SUCCESS_CODE.equals(rspCode)) {
            return true;
        }
        return false;
    }
}

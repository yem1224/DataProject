package com.finda.server.mydata.common.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @since 2021. 06. 11
 * 응답메세지 header 공통부
 */
@NoArgsConstructor
@Getter
public class ResHearderDto {
    //거래고유번호
    @JsonProperty("x-api-tran-id")
    private String apiTranId;
}

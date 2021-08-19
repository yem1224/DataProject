package com.finda.server.mydata.mgmts.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MgmtsConsentsReqDto {

	@JsonProperty("org_code")
	private String orgCode;
	
	@JsonProperty("user_ci")
	private String userCi;
}

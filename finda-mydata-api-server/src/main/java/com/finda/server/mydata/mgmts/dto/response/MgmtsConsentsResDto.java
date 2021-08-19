package com.finda.server.mydata.mgmts.dto.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finda.server.mydata.auth.domain.entity.MydataTranReqPtclEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MgmtsConsentsResDto {
	
	@JsonProperty("rsp_code")
	private String rspCode;
	
	@JsonProperty("rsp_msg")
	private String rspMsg;
	
	@JsonProperty("is_member")
	private boolean isMember;
	
	@JsonProperty("service_cnt")
	private Integer serviceCnt;
	
	@JsonProperty("service_list")
	private List<Service> serviceList;
	
	public void setDtoIsMemberTrue(String clientId, List<MydataTranReqPtclEntity> list) {
		this.setMember(true);
		this.setServiceCnt(1);
		List<Service> services = this.setServices(list);
		this.setServiceList(services);
	}
	
	private List<Service> setServices(List<MydataTranReqPtclEntity> list) {
		List<Service> services = new ArrayList<Service>();
		List<Consent> consents = this.setConsents(list);
		Service service = new Service();
		service.setClientId(rspCode);
		service.setConsentCnt(list.size());
		service.setConsentList(consents);
		services.add(service);
		return services;
	}
	
	private List<Consent> setConsents(List<MydataTranReqPtclEntity> list) {
		List<Consent> consents = new ArrayList<Consent>();
		BeanUtils.copyProperties(list, consents);
		return consents;
	}
	
	public void setDtoIsMemberFalse() {
		this.setMember(false);
		this.setServiceCnt(null);
		this.setServiceList(null);
	}
	
	@Data
	private static class Service {
		
		@JsonProperty("client_id")
		private String clientId;
		
		@JsonProperty("consent_cnt")
		private Integer consentCnt;
		
		@JsonProperty("consent_list")
		private List<Consent> consentList;
	}
	
	@Data
	private static class Consent {
		
		@JsonProperty("org_code")
		private String orgCode;
		
		@JsonProperty("scope")
		private String scope;
		
		@JsonProperty("consent_date")
		private Date consentDate;
		
		@JsonProperty("consent_end_date")
		private Date consentEndDate;
	}
}

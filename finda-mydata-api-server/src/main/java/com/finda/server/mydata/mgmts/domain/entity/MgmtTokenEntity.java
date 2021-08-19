package com.finda.server.mydata.mgmts.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.finda.server.mydata.common.domain.entity.AuditingBaseEntity;

import lombok.Data;

@Data
@Entity
@Table(name = "mydata_mgmt_token")
public class MgmtTokenEntity extends AuditingBaseEntity{

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "access_token", columnDefinition = "TEXT")
	private String accessToken;
	
	@Column(name = "token_type")
	private String tokenType;
	
	@Column(name = "expires_in")
	private Integer expiresIn;
	
	@Column(name = "scope")
	private String scope;

}

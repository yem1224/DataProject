package com.finda.server.mydata.mgmts.domain.entity;

import com.finda.server.mydata.bank.domain.entity.id.LoanBaseId;
import com.finda.server.mydata.common.domain.entity.AuditingBaseEntity;
import com.finda.server.mydata.mgmts.domain.entity.id.OrgIpInfoId;
import lombok.*;
import javax.persistence.*;

/**
 * @since 2021. 06. 15
 * 기관IP정보 entity
 */
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Data
@Table(name = "mydata_org_ip_info")
public class OrgIpEntity extends AuditingBaseEntity {

    @EmbeddedId
    private OrgIpInfoId orgIpInfoId;

    @Column(name = "send_recv_dv")
    private String sendRecvDv; //요청,제공자 구분

    @Column(name = "ip")
    private String ip; //IP및 PORT

    
    public OrgIpEntity() {
    	this.orgIpInfoId = new OrgIpInfoId();
    }

    public void setOrgCode(String orgCode){
        orgIpInfoId.setOrgCode(orgCode);
    }
}

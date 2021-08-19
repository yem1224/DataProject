package com.finda.server.mydata.transaction.domain;

import com.finda.server.mydata.common.domain.entity.ApiDomainCode;
import com.finda.server.mydata.common.domain.entity.AuditingBaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "mydata_api_tran_hist")
@Entity
public class ApiTranHistEntity extends AuditingBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_ci")
    private String userCi;

    @Column(name = "api_tran_id")
    private String apiTranId;

    @Column(name = "tx_id")
    private String txId;

    @Column(name = "org_code")
    private String orgCode; // 기관코드

    @Column(name = "type")
    private Integer type;   // 정기 전송 구분, 1: 정기적 전송, 2: 비정기적 전송

    @Embedded
    private ApiDomainCode apiDomainCode; // api 도메인 구분 코드

    @Column(name = "response_time")
    private Long responseTime; // 응답 시간

    @Column(name = "method")
    private String method;  // HttpMethod

    @Column(name = "status_code")
    private Integer statusCode; // 응답 코드

    @Column(name = "rsp_code")
    private String rspCode; // 세부 응답 코드

    @Column(name = "rsp_msg")
    private String rspMsg;  // 세부 응답 메시지

    @Column(name = "error_code")
    private String errorCode;

    @Column(name = "error_description")
    private String errorDescription;

    @Builder
    public ApiTranHistEntity(String userCi,
                             String apiTranId,
                             String txId,
                             String orgCode,
                             Integer type,
                             ApiDomainCode apiDomainCode,
                             Long responseTime,
                             HttpMethod method,
                             int statusCode,
                             String rspCode,
                             String rspMsg,
                             String errorCode,
                             String errorDescription) {
        this.userCi = userCi;
        this.apiTranId = apiTranId;
        this.txId = txId;
        this.orgCode = orgCode;
        this.type = type;
        this.apiDomainCode = apiDomainCode;
        this.responseTime = responseTime;
        this.method = method.name();
        this.statusCode = statusCode;
        this.rspCode = rspCode;
        this.rspMsg = rspMsg;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public String getTmSlot() {
        LocalTime baseTime = insertTime.toLocalTime();
        if (baseTime.isAfter(LocalTime.of(9,0, 0).minusNanos(1L))
                && baseTime.isBefore(LocalTime.of(18, 0, 0))) {
            return "00";
        }
        return "01";
    }
}

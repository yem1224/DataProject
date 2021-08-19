package com.finda.server.mydata.auth.domain.entity;

import com.finda.server.mydata.auth.domain.entity.id.MydataAuthBaseId;
import com.finda.server.mydata.common.domain.entity.AuditingBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Table(name = "mydata_auth_base")
@IdClass(MydataAuthBaseId.class)
@Entity
public class MydataAuthBaseEntity extends AuditingBaseEntity {

    @Id
    @Column(name = "org_code")
    private String orgCode;

    @Id
    @Column(name = "user_ci")
    private String userCi;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "access_token")
    private String accessToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_dv")
    private AuthDv authDv;

    @Column(name = "auth_org_code")
    private String authOrgCode;

    @Column(name = "auth_time")
    private LocalDateTime authTime;

    @Column(name = "scope")
    private String scope;

    @Column(name = "access_token_expire_time")
    private LocalDateTime accessTokenExpireTime;

    @Column(name = "refresh_token_expire_time")
    private LocalDateTime refreshTokenExpireTime;

    @Column(name = "auth_status")
    @Enumerated(EnumType.STRING)
    private AuthStatus authStatus;

    @Builder
    private MydataAuthBaseEntity(String orgCode,
                                 String userCi,
                                 String refreshToken,
                                 String accessToken,
                                 AuthDv authDv,
                                 String authOrgCode,
                                 LocalDateTime authTime,
                                 String scope,
                                 LocalDateTime accessTokenExpireTime,
                                 LocalDateTime refreshTokenExpireTime,
                                 AuthStatus authStatus) {
        this.orgCode = orgCode;
        this.userCi = userCi;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.authDv = authDv;
        this.authOrgCode = authOrgCode;
        this.authTime = authTime;
        this.scope = scope;
        this.accessTokenExpireTime = accessTokenExpireTime;
        this.refreshTokenExpireTime = refreshTokenExpireTime;
        this.authStatus = authStatus;
    }

    public MydataAuthBaseHistoryEntity toMydataAuthHist() {
        return MydataAuthBaseHistoryEntity.builder()
                .userCi(userCi)
                .orgCode(orgCode)
                .refreshToken(refreshToken)
                .authOrgCode(authOrgCode)
                .scope(scope)
                .build();
    }

    public void renewAccessToken(String accessToken, LocalDateTime accessTokenExpireTime) {
        this.accessToken = accessToken;
        this.accessTokenExpireTime = accessTokenExpireTime;
        this.authStatus = AuthStatus.AUTH01;
    }

    public void registerToken(String refreshToken,
                              String accessToken,
                              AuthDv authDv,
                              String authOrgCode,
                              LocalDateTime authTime,
                              String scope,
                              LocalDateTime accessTokenExpireTime,
                              LocalDateTime refreshTokenExpireTime,
                              AuthStatus authStatus) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.authDv = authDv;
        this.authOrgCode = authOrgCode;
        this.authTime = authTime;
        this.scope = scope;
        this.accessTokenExpireTime = accessTokenExpireTime;
        this.refreshTokenExpireTime = refreshTokenExpireTime;
        this.authStatus = authStatus;
    }

    public void updateAuthStatus(AuthStatus authStatus) {
        this.authStatus = authStatus;
    }

    public enum AuthStatus {
        AUTH00, // 인증 중, 인증 요청은 하였으나 응답은 받지 못한 상태
        AUTH01, // 신규, 최초 발급 완료 및 발급 후 갱신거래(개별인증003)을 통한 변경 포함
        AUTH98, // 인증 실패, 인증 요청 후 금융사 인증 실패 응답
        AUTH99, // 폐기, 전체 해지 (계좌 전체 해지, 금융사 해지)
        AUTH02, // 리프레쉬 토큰 만료 5일전, 리프레쉬 토큰 유효기간 1년 중 만료일까지 남은 기간 5일 -> 만료, 신규 발급 대상
        AUTH03, // 리프레쉬 토큰 만료 6일전, 리프레쉬 토큰 유효기간 1년 중 만료일까지 남은 기간 6일 -> 예비 만료 대상
        AUTH04, // 리프레쉬 토큰 만료 7일전, 리프레쉬 토큰 유효기간 1년 중 만료일까지 남은 기간 7일 -> 예비 만료 대상
        AUTH05, // 접근 토큰 유효기간 90일 중, 만료일까지 남은 기간 5일 -> 갱신 대상
        AUTH06, // 접근 토큰 유효기간 90일 중, 만료일까지 남은 기간 6일 -> 예비 갱신 대상
        AUTH07  // 접근 토큰 유효기간 90일 중, 만료일까지 남은 기간 7일 -> 예비 갱신 대상
    }
}

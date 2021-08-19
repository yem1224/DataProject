package com.finda.server.mydata.auth.domain.entity;

import com.finda.server.mydata.common.domain.entity.AuditingBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 개별인증-001 에서 CSRF 보안위협에 대응하기 위한 임의 설정 데이터.
 * 개별인증-001 에서 요청메시지 상에서 state 값이 Entity 의 key 값인 id.
 */
@Getter
@NoArgsConstructor
@Table(name = "mydata_state_session")
@Entity
public class StateSessionEntity extends AuditingBaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "user_ci")
    private String userCi;

    @Column(name = "org_code")
    private String orgCode;

    @Builder
    public StateSessionEntity(String userCi, String orgCode) {
        this.id = id;
        this.userCi = userCi;
        this.orgCode = orgCode;
    }

    public boolean isExpired() {
        return LocalDateTime.now().minusMinutes(10L).isAfter(insertTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateSessionEntity that = (StateSessionEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package com.finda.server.mydata.auth.domain.entity.id;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Embeddable
public class IntegrationAuthNonceId implements Serializable {
    private static final long serialVersionUID = 1L;

    private String session;
    private String userCi;
    private String orgCode;

    private IntegrationAuthNonceId(String session, String userCi, String orgCode) {
        this.session = session;
        this.userCi = userCi;
        this.orgCode = orgCode;
    }

    public static IntegrationAuthNonceId create(String session, String userCi, String orgCode) {
        return new IntegrationAuthNonceId(session, userCi, orgCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntegrationAuthNonceId that = (IntegrationAuthNonceId) o;
        return Objects.equals(session, that.session) && Objects.equals(userCi, that.userCi) && Objects.equals(orgCode, that.orgCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session, userCi, orgCode);
    }
}

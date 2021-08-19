package com.finda.server.mydata.common.domain.entity;

import com.finda.server.mydata.auth.AuthConstants;
import com.finda.server.mydata.common.code.ApiType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.net.URI;
import java.util.Map;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Embeddable
public class ApiDomainCode {

    @Column(name = "api_code")
    private String apiCode;
    private String domain;
    private String resource;

    public ApiDomainCode(String domain, ApiType apiType) {
        this.apiCode = apiType.getCode();
        this.domain = domain;
        this.resource = apiType.getName();
    }

    public ApiDomainCode(String domain, String resource, String apiCode) {
        this.domain = domain;
        this.resource = resource;
        this.apiCode = apiCode;
    }

    public URI toURI(@Nullable Map<String, String> requestParameters) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(getProxy() + resource);
        addAllRequestParameters(uriComponentsBuilder, requestParameters);
        return URI.create(uriComponentsBuilder.toUriString());
    }

    private String getProxy() {
        return AuthConstants.PROXY_URI + ":" + AuthConstants.PROXY_PORT;
    }

    private void addAllRequestParameters(UriComponentsBuilder uriComponentsBuilder, Map<String, String> requestParameters) {
        if (requestParameters == null || requestParameters.isEmpty()) {
            return;
        }
        for (Map.Entry<String, String> entry : requestParameters.entrySet()) {
            uriComponentsBuilder.queryParam(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiDomainCode that = (ApiDomainCode) o;
        return Objects.equals(apiCode, that.apiCode) && Objects.equals(domain, that.domain) && Objects.equals(resource, that.resource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiCode, domain, resource);
    }
}

package com.finda.server.mydata.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finda.server.mydata.auth.AuthConstants;
import com.finda.server.mydata.common.domain.entity.ApiDomainCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ApiTransactionRequest<T> {
    private static final String SCHEDULED = "scheduled";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Nullable
    private final T body;
    private final MultiValueMap<String, String> headers;
    private final HttpMethod method;
    private final ApiDomainCode apiDomainCode;

    @Nullable
    private final Map<String, String> requestParameters;

    public ApiTransactionRequest(HttpMethod method,
                                 ApiDomainCode apiDomainCode,
                                 @Nullable Map<String, String> requestParameters) {
        this(null, null, method, apiDomainCode, requestParameters);
    }

    public ApiTransactionRequest(@Nullable T body,
                                 HttpMethod method,
                                 ApiDomainCode apiDomainCode,
                                 @Nullable Map<String, String> requestParameters) {
        this(body, null, method, apiDomainCode, requestParameters);
    }

    public ApiTransactionRequest(@Nullable MultiValueMap<String, String> headers,
                                 HttpMethod method,
                                 ApiDomainCode apiDomainCode,
                                 @Nullable Map<String, String> requestParameters) {
        this(null, headers, method, apiDomainCode, requestParameters);
    }

    public ApiTransactionRequest(@Nullable MultiValueMap<String, String> headers,
                                 HttpMethod method,
                                 ApiDomainCode apiDomainCode,
                                 @Nullable Object requestParameter) {
        this(null, headers, method, apiDomainCode, objectToMap(requestParameter));
    }

    public ApiTransactionRequest(@Nullable T body,
                                 @Nullable MultiValueMap<String, String> headers,
                                 HttpMethod method,
                                 ApiDomainCode apiDomainCode,
                                 @Nullable Map<String, String> requestParameters) {
        Objects.requireNonNull(method);
        Objects.requireNonNull(apiDomainCode);
        this.body = body;
        HttpHeaders tempHttpHeaders = new HttpHeaders();
        if (Objects.nonNull(headers)) {
            tempHttpHeaders.putAll(headers);
        }
        this.headers = HttpHeaders.writableHttpHeaders(tempHttpHeaders);
        this.method = method;
        this.apiDomainCode = apiDomainCode;

        if (Objects.isNull(requestParameters)) {
            requestParameters = new HashMap<>();
        }
        this.requestParameters = requestParameters;
    }

    /**
     * Dto odject -> map parameter로 변경
     * @param requestParameter
     * @return
     */
    private static Map<String, String> objectToMap(Object requestParameter) {
        if (Objects.isNull(requestParameter)) {
            return null;
        }
        Map<String, String> requestParameters = new HashMap<>();
        Map<String, Object> map = (Map<String, Object>) objectMapper.convertValue(requestParameter, Map.class);
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                final String key = entry.getKey();
                final Object value = entry.getValue();
                if (value != null) {
                    requestParameters.put(key, value.toString());
                }
            }
        }
        return requestParameters;
    }

    public void addHeader(String key, String value) {
        headers.add(key, value);
    }

    public boolean hasKeyInHeaders(String key) {
        return headers.containsKey(key);
    }

    public boolean isScheduledRequest() {
        if (hasKeyInHeaders(AuthConstants.X_API_TYPE)) {
            return headers.get(AuthConstants.X_API_TYPE).contains(SCHEDULED);
        }
        return false;
    }

    public String getHeaderFirst(String key) {
        return headers.getFirst(key);
    }

    public String getOrgCode() {
        if (HttpMethod.GET.equals(method)) {
            return getOrgCodeFromRequestParameters(AuthConstants.ORG_CODE);
        }
        return reflectFieldFromBody(AuthConstants.ORG_CODE);
    }

    @Nullable
    public String getOrgCodeFromRequestParameters(String parameterName) {
        if (Objects.isNull(requestParameters)) {
            return null;
        }
        return requestParameters.get(parameterName);
    }

    @Nullable
    public String reflectFieldFromBody(String fieldName) {
        if (Objects.isNull(body)) {
            return null;
        }
        Object obj = body;
        Class<?> objClass = obj.getClass();
        try {
            Field field = objClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            Object o = field.get(obj);
            if (o instanceof String) {
                return (String) o;
            }
            return null;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

    public RequestEntity<T> toRequestEntity() {
        return new RequestEntity<>(body, headers, method, apiDomainCode.toURI(requestParameters));
    }

    public ApiDomainCode getApiDomainCode() {
        return new ApiDomainCode(apiDomainCode.getDomain(), apiDomainCode.getResource(), apiDomainCode.getApiCode());
    }

    public HttpMethod getMethod() {
        return method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiTransactionRequest<?> that = (ApiTransactionRequest<?>) o;
        return method == that.method && Objects.equals(apiDomainCode, that.apiDomainCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, apiDomainCode);
    }
}

package com.finda.server.mydata.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthConstants {
    public static final String X_USER_CI = "x-user-ci";
    public static final String X_API_TRAN_ID = "x-api-tran-id";
    public static final String X_API_TYPE = "x-api-type";
    public static final String STATE = "state";
    public static final String ORG_CODE = "org_code";
    public static final String RESPONSE_TYPE = "response_type";
    public static final String CLIENT_ID = "client_id";
    public static final String REDIRECT_URI = "redirect_uri";
    public static final String APP_SCHEME = "app_scheme";
    public static final String PASSWORD = "password";
    public static final String AUTHORIZATION = "Authorization";

    public static String PROXY_URI;
    public static String PROXY_PORT;

    @Value("${mydata.proxy.uri}")
    public void setProxyUri(String proxyUri) {
        PROXY_URI = proxyUri;
    }

    @Value("${mydata.proxy.port}")
    public void setProxyPort(String proxyPort) {
        PROXY_PORT = proxyPort;
    }
}

package com.finda.server.config;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
public class RestTemplateConnectionPoolConfig {
    private static final int TIMEOUT_MILLISECONDS_UNIT = 5 * 1000;

    @Value("${mydata.proxy.uri}")
    private String PROXY_URI;

    @Value("${mydata.proxy.port}")
    private int PROXY_PORT;

    @Value("${mydata.restTemplate.maxConnectionPool}")
    private int MAX_CONNECTION_POOL;

    @Value("${mydata.restTemplate.maxConnectionPerRoute}")
    private int MAX_CONNECTION_PER_ROUTE;

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = createHttpComponentsClientHttpRequestFactory();

        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        restTemplate.setMessageConverters(
                Arrays.asList(
                        new FormHttpMessageConverter(),
                        new MappingJackson2HttpMessageConverter()
                )
        );

        return new RestTemplate();
    }

    private HttpComponentsClientHttpRequestFactory createHttpComponentsClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(TIMEOUT_MILLISECONDS_UNIT);
        clientHttpRequestFactory.setConnectionRequestTimeout(TIMEOUT_MILLISECONDS_UNIT);
        clientHttpRequestFactory.setReadTimeout(TIMEOUT_MILLISECONDS_UNIT);
        clientHttpRequestFactory.setHttpClient(createHttpClient());
        return clientHttpRequestFactory;
    }

    private HttpClient createHttpClient() {
        return HttpClients.custom()
                .setMaxConnTotal(MAX_CONNECTION_POOL)
                .setMaxConnPerRoute(MAX_CONNECTION_PER_ROUTE)
                .build();
    }

    private HttpHost httpHost() {
        return new HttpHost(PROXY_URI, PROXY_PORT);
    }
}

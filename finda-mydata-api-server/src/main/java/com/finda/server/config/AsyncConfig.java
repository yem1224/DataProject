package com.finda.server.config;

import com.finda.services.finda.common.log.ExternalLogUtils;
import org.slf4j.event.Level;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@ConditionalOnProperty(
        prefix = "myData.threadPool",
        name = {"corePoolSize", "maxPoolSize", "queueCapacity", "keepAliveSeconds"}
)
@EnableAsync
@Configuration
public class AsyncConfig extends AsyncConfigurerSupport {
    private static final String MYDATA_AUTHENTICATION_THREAD = "mydata-async-thread";

    @Value("${myData.threadPool.corePoolSize}")
    private int corePoolSize;

    @Value("${myData.threadPool.maxPoolSize}")
    private int maxPoolSize;

    @Value("${myData.threadPool.queueCapacity}")
    private int queueCapacity;

    @Value("${myData.threadPool.keepAliveSeconds}")
    private int keepAliveSeconds;

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix(MYDATA_AUTHENTICATION_THREAD);
        executor.initialize();

        ExternalLogUtils.writeSystemLog(
                Level.INFO,
                String.format(
                        "Initialized authenticationThreadPoolTaskExecutor. [ corePoolSize: %d, maxPoolSize: %d, queueCapacity: %d, keepaliveSeconds: %d ]",
                        corePoolSize,
                        maxPoolSize,
                        queueCapacity,
                        keepAliveSeconds
                )
        );
        return executor;
    }
}

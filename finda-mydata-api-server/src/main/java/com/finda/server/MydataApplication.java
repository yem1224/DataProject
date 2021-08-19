package com.finda.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.finda.server" , "com.finda.services"})
@EnableJpaRepositories(basePackages = {"com.finda.server", "com.finda.services"})
@SpringBootApplication(scanBasePackages = {"com.finda.server" , "com.finda.services"})
@EnableAspectJAutoProxy
public class MydataApplication {

    public static void main(String[] args) {
        try
        {
            SpringApplication.run(MydataApplication.class, args);
        }
        catch (Throwable throwable)
        {
            System.out.println(throwable.toString());
            throwable.printStackTrace();
        }
    }

}

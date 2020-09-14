package com.github.lyrric.tcc.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * Created on 2018/6/6.
 *
 * @author wangxiaodong
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class BusinessApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BusinessApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);
    }
}

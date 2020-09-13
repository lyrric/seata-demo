package com.github.lyrric.tcc.account;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created on 2018/6/6.
 *
 * @author wangxiaodong
 */
@SpringBootApplication
@MapperScan(basePackages = "com.github.lyrric.tcc.account.mapper")
@EnableEurekaClient
public class AccountApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AccountApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}

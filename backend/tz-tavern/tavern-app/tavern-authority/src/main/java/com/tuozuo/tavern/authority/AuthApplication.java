package com.tuozuo.tavern.authority;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by 刘悦之 on 2020/7/17.
 */
@SpringBootApplication
@MapperScan(basePackages = "com.tuozuo.tavern.authority.mapper")
@ImportAutoConfiguration(classes = {com.tuozuo.tavern.libs.auth.AuthConfiguration.class})
public class AuthApplication {
    public static void main(String[] args) {

        SpringApplication.run(AuthApplication.class, args);
    }
}

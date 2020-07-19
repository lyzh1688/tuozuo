package com.tuozuo.tavern.gw;

import com.tuozuo.tavern.libs.auth.AuthConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by 刘悦之 on 2020/7/17.
 */
@SpringBootApplication
@EnableZuulProxy
@ImportAutoConfiguration(classes = {AuthConfiguration.class})
public class GWApplication {

    public static void main(String[] args) {

        SpringApplication.run(GWApplication.class, args);
    }
}

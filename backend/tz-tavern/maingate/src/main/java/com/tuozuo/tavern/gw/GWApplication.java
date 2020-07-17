package com.tuozuo.tavern.gw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by 刘悦之 on 2020/7/17.
 */
@SpringBootApplication
@EnableZuulProxy
public class GWApplication {

    public static void main(String[] args) {

        SpringApplication.run(GWApplication.class, args);
    }
}

package com.tuozuo.tavern.libs.auth;

import org.springframework.context.annotation.Bean;

/**
 * Created by 刘悦之 on 2019/6/27.
 */
@org.springframework.context.annotation.Configuration
public class AuthConfiguration {
    @Bean
    public JwtAuthenticationProperty jwtAuthenticationProperty(){
        return new JwtAuthenticationProperty();
    }
}

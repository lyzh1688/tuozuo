package com.tuozuo.tavern.libs.auth;

import com.tuozuo.tavern.libs.auth.jwt.JwtAuthenticationProperty;
import com.tuozuo.tavern.libs.auth.session.RedisSessionManager;
import com.tuozuo.tavern.libs.auth.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by 刘悦之 on 2019/6/27.
 */
@org.springframework.context.annotation.Configuration
public class AuthConfiguration {

    @Bean
    public JwtAuthenticationProperty jwtAuthenticationProperty(){
        return new JwtAuthenticationProperty();
    }

    @Bean
    public SessionManager redisSessionManager(@Autowired StringRedisTemplate redisTemplate){
        return new RedisSessionManager(redisTemplate);
    }
}

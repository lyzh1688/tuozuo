package com.tuozuo.tavern.libs.auth.session;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Created by 刘悦之 on 2020/7/18.
 */
public class RedisSessionManager implements SessionManager {

    public RedisSessionManager(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    RedisTemplate redisTemplate;

    @Override
    public boolean valid(Session session) {
        if (session instanceof RedisSession) {
            if (this.check((RedisSession) session)) {
                return this.renewal((RedisSession) session);
            } else {
                return false;
            }
        } else {
            throw new RuntimeException("session is not instance of RedisSession");
        }
    }

    protected boolean renewal(RedisSession session) {
        return this.redisTemplate.expire(session.getSessionKey(), session.getTimeout(), TimeUnit.MINUTES);
    }

    protected boolean check(RedisSession session) {
        return this.redisTemplate.hasKey(session.getSessionKey());
    }

}

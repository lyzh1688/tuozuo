package com.tuozuo.tavern.libs.auth.session;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;

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

    @Override
    public void createOrRefreshSession(Session session) {
        if (session instanceof RedisSession) {
            RedisSession redisSession = (RedisSession) session;
            Boolean ret = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    redisConnection.set(redisSession.getSessionKey().getBytes(),
                            redisSession.getToken().getBytes(),
                            Expiration.from(30, TimeUnit.MINUTES),
                            RedisStringCommands.SetOption.SET_IF_ABSENT);
                    return true;
                }
            });
        } else {

        }
    }

    @Override
    public boolean dispose(Session session) {
        if (session instanceof RedisSession) {
            RedisSession redisSession = (RedisSession) session;
            redisTemplate.delete(redisSession.getSessionKey());
            return true;
        } else {
            return false;
        }
    }

    protected boolean renewal(RedisSession session) {
        return this.redisTemplate.expire(session.getSessionKey(), session.getTimeout(), TimeUnit.MINUTES);
    }

    protected boolean check(RedisSession session) {
        return this.redisTemplate.hasKey(session.getSessionKey());
    }

}

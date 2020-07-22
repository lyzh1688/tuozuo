package com.tuozuo.tavern.libs.auth.session;

import io.jsonwebtoken.Claims;

/**
 * Created by 刘悦之 on 2020/7/18.
 */
public class RedisSession extends Session {

    public RedisSession(String userId, String systemId, String roleId, String token) {
        super(userId, systemId, roleId,token);
    }

    public RedisSession(String token, Claims claims, long timeout) {
        super(token, claims, timeout);
    }

    public String getSessionKey() {
        return String.join(":", "JWT", this.systemId, this.roleId, this.userId, this.token);
    }
}

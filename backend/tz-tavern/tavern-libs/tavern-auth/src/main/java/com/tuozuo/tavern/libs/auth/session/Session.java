package com.tuozuo.tavern.libs.auth.session;

import com.tuozuo.tavern.libs.auth.jwt.AuthTokenFactor;
import io.jsonwebtoken.Claims;

/**
 * Created by 刘悦之 on 2020/7/18.
 */
public abstract class Session {


    String userId;
    String systemId;
    String roleId;
    String token;
    long timeout;

    public Session(String token, Claims claims, long timeout) {
        this.token = token;
        this.userId = claims.get(AuthTokenFactor.Factor.USER_ID, String.class);
        this.roleId = claims.get(AuthTokenFactor.Factor.ROLE_GROUP, String.class);
        this.systemId = claims.get(AuthTokenFactor.Factor.SYSTEM_ID, String.class);
        this.timeout = timeout;
    }

    public String getUserId() {
        return userId;
    }

    public String getSystemId() {
        return systemId;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getToken() {
        return token;
    }

    public long getTimeout() {
        return timeout;
    }
}

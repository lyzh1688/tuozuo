package com.tuozuo.tavern.libs.auth.jwt;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by 刘悦之 on 2019/6/27.
 */
@ConfigurationProperties(prefix = "tavern.security.jwt")
public class JwtAuthenticationProperty {

    private String authPrefix = "auth";

    private String accessToken = "access-token";

    private String prefix = "Bearer";

    private int expiration = 8 * 60 * 60; // default 8 hours

    private long tokenTimeout = 15;     //分钟

    private String secret;

    public String getAuthPrefix() {
        return authPrefix;
    }

    public void setAuthPrefix(String authPrefix) {
        this.authPrefix = authPrefix;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getTokenTimeout() {
        return tokenTimeout;
    }

    public void setTokenTimeout(long tokenTimeout) {
        this.tokenTimeout = tokenTimeout;
    }
}

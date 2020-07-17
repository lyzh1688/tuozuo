package com.tuozuo.tavern.authority.model;

/**
 * Created by 刘悦之 on 2020/7/17.
 */
public class TokenAuthority {
    String accessToken;
    String authority;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}

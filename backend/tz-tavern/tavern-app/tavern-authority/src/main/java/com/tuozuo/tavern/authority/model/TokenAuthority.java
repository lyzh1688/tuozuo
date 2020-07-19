package com.tuozuo.tavern.authority.model;

/**
 * Created by 刘悦之 on 2020/7/17.
 */
public class TokenAuthority {
    String accessToken;
    String authority;
    Boolean loginSuccess;
    String loginMessage;

    public TokenAuthority() {
    }

    public TokenAuthority(Boolean loginSuccess, String loginMessage) {
        this.loginSuccess = loginSuccess;
        this.loginMessage = loginMessage;
    }

    public String getLoginMessage() {
        return loginMessage;
    }

    public void setLoginMessage(String loginMessage) {
        this.loginMessage = loginMessage;
    }

    public Boolean getLoginSuccess() {
        return loginSuccess;
    }

    public void setLoginSuccess(Boolean loginSuccess) {
        this.loginSuccess = loginSuccess;
    }

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

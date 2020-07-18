package com.tuozuo.tavern.authority.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tuozuo.tavern.libs.auth.jwt.AuthTokenFactor;
import com.tuozuo.tavern.libs.auth.jwt.JwtAuthenticationProperty;
import com.tuozuo.tavern.libs.auth.jwt.TokenHelper;

/**
 * Created by 刘悦之 on 2020/7/18.
 */
@TableName("AUTH_USER")
public class User {
    static int maxFailedTimes = 5;
    static String AUTHORITY = "normal";
    @TableId
    String userId;
    @TableId
    String systemId;
    @TableId
    String roleGroup;
    String userPswd;
    int failedTimes;
    TokenAuthority tokenAuthority = new TokenAuthority();

    public User(String userId, String systemId, String roleGroup, String userPswd) {
        this.userId = userId;
        this.systemId = systemId;
        this.roleGroup = roleGroup;
        this.userPswd = userPswd;
    }

    public TokenAuthority login(String inputMD5Pswd, JwtAuthenticationProperty config) {
        if (failedTimes >= maxFailedTimes) {
            this.tokenAuthority.setLoginSuccess(false);
            this.tokenAuthority.setLoginMessage("密码错误次数达到上限");
        } else {
            if (this.userPswd.equals(inputMD5Pswd)) {
                this.tokenAuthority.setLoginSuccess(true);
                this.tokenAuthority.setAuthority(String.join(".", this.systemId, this.roleGroup, AUTHORITY));
                AuthTokenFactor tokenFactor = new AuthTokenFactor(this.userId, this.systemId, this.roleGroup);
                String accessToken = TokenHelper.createToken(config, tokenFactor);
                this.tokenAuthority.setAccessToken(accessToken);
            } else {
                this.tokenAuthority.setLoginSuccess(false);
                this.tokenAuthority.setLoginMessage("密码错误,您还可以尝试" + (maxFailedTimes - failedTimes) + "次");
            }
        }
        return this.tokenAuthority;
    }

    public static int getMaxFailedTimes() {
        return maxFailedTimes;
    }

    public static void setMaxFailedTimes(int maxFailedTimes) {
        User.maxFailedTimes = maxFailedTimes;
    }

    public static String getAUTHORITY() {
        return AUTHORITY;
    }

    public static void setAUTHORITY(String AUTHORITY) {
        User.AUTHORITY = AUTHORITY;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getRoleGroup() {
        return roleGroup;
    }

    public void setRoleGroup(String roleGroup) {
        this.roleGroup = roleGroup;
    }

    public String getUserPswd() {
        return userPswd;
    }

    public void setUserPswd(String userPswd) {
        this.userPswd = userPswd;
    }

    public int getFailedTimes() {
        return failedTimes;
    }

    public void setFailedTimes(int failedTimes) {
        this.failedTimes = failedTimes;
    }

    public TokenAuthority getTokenAuthority() {
        return tokenAuthority;
    }

    public void setTokenAuthority(TokenAuthority tokenAuthority) {
        this.tokenAuthority = tokenAuthority;
    }
}

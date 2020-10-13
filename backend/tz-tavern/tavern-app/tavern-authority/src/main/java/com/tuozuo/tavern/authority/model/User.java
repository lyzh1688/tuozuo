package com.tuozuo.tavern.authority.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.google.common.base.Strings;
import com.tuozuo.tavern.libs.auth.jwt.AuthTokenFactor;
import com.tuozuo.tavern.libs.auth.jwt.JwtAuthenticationProperty;
import com.tuozuo.tavern.libs.auth.jwt.TokenHelper;

/**
 * Created by 刘悦之 on 2020/7/18.
 */
@TableName("AUTH_USER")
public class User {
    static int maxFailedTimes = 5;
    @TableId
    String userId;
    @TableId
    String systemId;
    @TableId
    String roleGroup;
    String userPswd;
    int failedTimes;
    //    @TableField(exist = false)
//    TokenAuthority tokenAuthority = new TokenAuthority();
    @TableField(exist = false)
    Privilege privilege;

    public User() {
    }

    public User(String userId, String systemId, String roleGroup, String userPswd, Privilege privilege) {
        this.userId = userId;
        this.systemId = systemId;
        this.roleGroup = roleGroup;
        this.userPswd = userPswd;
        this.privilege = privilege;
    }

    public User(String userId, String systemId, String roleGroup, String userPswd) {
        this.userId = userId;
        this.systemId = systemId;
        this.roleGroup = roleGroup;
        this.userPswd = userPswd;
    }

    private String createAccessToken(JwtAuthenticationProperty config) {
        AuthTokenFactor tokenFactor = new AuthTokenFactor(this.userId, this.systemId, this.roleGroup);
        String accessToken = TokenHelper.createToken(config, tokenFactor);
        return accessToken;
    }

    private String createTokenAuthority() {
        return String.join(".", this.systemId, this.roleGroup, this.privilege.getPrivilege());
    }

    public WXTokenAuthority wxLogin(String openID, String systemId, String roleGroup, JwtAuthenticationProperty config) {
        WXTokenAuthority tokenAuthority = new WXTokenAuthority();
        if (Strings.isNullOrEmpty(this.userId)) {
            this.userId = openID;
            this.roleGroup = roleGroup;
            this.systemId = systemId;
            tokenAuthority.setLoginSuccess(false);
        } else {
            tokenAuthority.setLoginSuccess(true);
        }
        tokenAuthority.setAuthority(this.createTokenAuthority());
        String accessToken = this.createAccessToken(config);
        tokenAuthority.setAccessToken(accessToken);
        tokenAuthority.openID = openID;
        return tokenAuthority;
    }

    public TokenAuthority login(String inputMD5Pswd, JwtAuthenticationProperty config) {
        TokenAuthority tokenAuthority = new TokenAuthority();
        if (failedTimes >= maxFailedTimes) {
            tokenAuthority.setLoginSuccess(false);
            tokenAuthority.setLoginMessage("密码错误次数达到上限");
        } else {
            if (this.userPswd.equals(inputMD5Pswd)) {
                tokenAuthority.setLoginSuccess(true);
                tokenAuthority.setAuthority(this.createTokenAuthority());
                String accessToken = this.createAccessToken(config);
                tokenAuthority.setAccessToken(accessToken);
            } else {
                tokenAuthority.setLoginSuccess(false);
                tokenAuthority.setLoginMessage("密码错误,您还可以尝试" + (maxFailedTimes - failedTimes) + "次");
            }
        }
        return tokenAuthority;
    }

    public static int getMaxFailedTimes() {
        return maxFailedTimes;
    }

    public static void setMaxFailedTimes(int maxFailedTimes) {
        User.maxFailedTimes = maxFailedTimes;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
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

    public void incFailedTimes() {
        this.failedTimes++;
    }
}

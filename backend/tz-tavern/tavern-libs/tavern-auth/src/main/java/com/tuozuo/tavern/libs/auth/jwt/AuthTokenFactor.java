package com.tuozuo.tavern.libs.auth.jwt;

/**
 * Created by 刘悦之 on 2019/6/30.
 */
public class AuthTokenFactor {

    public static class Factor{
        public static final String USER_ID = "USER_ID";
        public static final String SYSTEM_ID = "SYSTEM_ID";
        public static final String ROLE_GROUP = "ROLE_GROUP";
    }

    private String userId;
    private String systemId;
    private String roleGroup;

    public AuthTokenFactor(){}

    public AuthTokenFactor(String userId, String systemId, String roleGroup) {
        this.userId = userId;
        this.systemId = systemId;
        this.roleGroup = roleGroup;
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
}

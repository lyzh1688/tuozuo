package com.tuozuo.tavern.authority.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * Created by 刘悦之 on 2020/8/29.
 */
@TableName("AUTH_USER_PRIVILEGE")
public class Privilege {
    @TableId
    String userId;
    @TableId
    String systemId;
    @TableId
    String roleGroup;
    @TableField
    String privilege;

    public static String AUTHORITY_NORMAL = "normal";
    public static String AUTHORITY_ADMIN = "admin";
    public static String AUTHORITY_VISITOR = "visitor";

    public static Privilege createDefaultPrivilege(String userId, String systemId, String roleGroup) {
        return new Privilege(userId,systemId,roleGroup, AUTHORITY_NORMAL);
    }

    public Privilege(){}

    public Privilege(String userId, String systemId, String roleGroup, String privilege) {
        this.userId = userId;
        this.systemId = systemId;
        this.roleGroup = roleGroup;
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

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }
}

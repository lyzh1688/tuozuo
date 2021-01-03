package com.tuozuo.tavern.corp.assist.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
@TableName("corporation_client_group_rel")
public class CorporationClientGroupRel extends Model<CorporationClientGroupRel> {
    private String clientId;

    private String groupId;

    public CorporationClientGroupRel(String clientId, String groupId) {
        this.clientId = clientId;
        this.groupId = groupId;
    }

    public static CorporationClientGroupRel create(String clientId, String groupId){
        return new CorporationClientGroupRel(clientId, groupId);
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }
}
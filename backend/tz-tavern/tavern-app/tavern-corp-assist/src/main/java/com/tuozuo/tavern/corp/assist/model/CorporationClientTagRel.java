package com.tuozuo.tavern.corp.assist.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
@TableName("corporation_client_tag_rel")
public class CorporationClientTagRel extends Model<CorporationClientTagRel> {
    private String clientId;

    private String tagId;

    public CorporationClientTagRel(String clientId, String tagId) {
        this.clientId = clientId;
        this.tagId = tagId;
    }

    public static CorporationClientTagRel create(String clientId, String tagId) {
        return new CorporationClientTagRel(clientId, tagId);
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId == null ? null : tagId.trim();
    }
}
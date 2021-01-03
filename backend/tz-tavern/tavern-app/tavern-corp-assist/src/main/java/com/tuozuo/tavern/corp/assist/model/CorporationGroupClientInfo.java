package com.tuozuo.tavern.corp.assist.model;

import com.google.common.collect.Lists;

import java.util.List;

public class CorporationGroupClientInfo {

    private String groupId;

    private String groupName;

    private String groupNotice;

    private String createTime;

    private List<String> tags = Lists.newArrayList();

    private List<CorporationClientInfo> clients = Lists.newArrayList();

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupNotice() {
        return groupNotice;
    }

    public void setGroupNotice(String groupNotice) {
        this.groupNotice = groupNotice;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<CorporationClientInfo> getClients() {
        return clients;
    }

    public void setClients(List<CorporationClientInfo> clients) {
        this.clients = clients;
    }
}


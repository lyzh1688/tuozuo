package com.tuozuo.tavern.corp.assist.vo;

import com.google.common.collect.Lists;

import java.util.List;

public class CorporationGroupClientVO {

    private String groupId;

    private List<String> clients = Lists.newArrayList();

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<String> getClients() {
        return clients;
    }

    public void setClients(List<String> clients) {
        this.clients = clients;
    }
}
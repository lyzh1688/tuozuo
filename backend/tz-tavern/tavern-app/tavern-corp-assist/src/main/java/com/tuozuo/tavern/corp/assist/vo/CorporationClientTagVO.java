package com.tuozuo.tavern.corp.assist.vo;

import com.google.common.collect.Lists;

import java.util.List;

public class CorporationClientTagVO {

    private String clientId;

    private List<String> tags = Lists.newArrayList();

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
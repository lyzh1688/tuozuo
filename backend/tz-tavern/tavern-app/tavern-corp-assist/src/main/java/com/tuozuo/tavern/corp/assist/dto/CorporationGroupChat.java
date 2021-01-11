package com.tuozuo.tavern.corp.assist.dto;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/9 <br>
 */
public class CorporationGroupChat {

    private String chatId;//	客户群ID(企业微信)
    private String chatIdBackend;//	客户群id（后端，未关联则为空）
    private String name;
    private String nameBackend;
    private List<CorporationGroupMember> memberList = Lists.newArrayList();


    public String getNameBackend() {
        return nameBackend;
    }

    public void setNameBackend(String nameBackend) {
        this.nameBackend = nameBackend;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getChatIdBackend() {
        return chatIdBackend;
    }

    public void setChatIdBackend(String chatIdBackend) {
        this.chatIdBackend = chatIdBackend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CorporationGroupMember> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<CorporationGroupMember> memberList) {
        this.memberList = memberList;
    }
}

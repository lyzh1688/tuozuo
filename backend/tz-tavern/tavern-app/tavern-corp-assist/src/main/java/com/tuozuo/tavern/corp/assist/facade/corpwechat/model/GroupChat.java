package com.tuozuo.tavern.corp.assist.facade.corpwechat.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/9 <br>
 */
public class GroupChat {
    @JsonProperty("chat_id")
    private String chatId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("create_time")
    private String createTime;
    @JsonProperty("notice")
    private String notice;
    @JsonProperty("member_list")
    private List<MemberList> memberList;

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public List<MemberList> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<MemberList> memberList) {
        this.memberList = memberList;
    }
}

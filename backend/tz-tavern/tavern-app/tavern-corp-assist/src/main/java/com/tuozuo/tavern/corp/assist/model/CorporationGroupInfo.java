package com.tuozuo.tavern.corp.assist.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tuozuo.tavern.corp.assist.utils.UUIDUtil;

import java.time.LocalDateTime;

@TableName("corporation_group_info")
public class CorporationGroupInfo extends Model<CorporationGroupInfo> {


    @TableId
    private String groupId;
    private String chatId;

    private String groupName;

    private String groupNotice;

    private LocalDateTime createTime;

    private String valid;

    public CorporationGroupInfo(String groupId, String valid) {
        this.groupId = groupId;
        this.valid = valid;
    }

    public CorporationGroupInfo() {
    }

    public CorporationGroupInfo(String groupId, String chatId, String groupName, String groupNotice, LocalDateTime createTime) {
        this.groupId = groupId;
        this.chatId = chatId;
        this.groupName = groupName;
        this.groupNotice = groupNotice;
        this.createTime = createTime;
    }

    public CorporationGroupInfo(String groupId, String chatId, String groupName, String groupNotice) {
        this.groupId = groupId;
        this.chatId = chatId;
        this.groupName = groupName;
        this.groupNotice = groupNotice;
    }

    public static CorporationGroupInfo create(String groupId, String valid) {
        return new CorporationGroupInfo(groupId, valid);
    }

    public static CorporationGroupInfo create(String chatId, String groupName, String groupNotice) {
        return new CorporationGroupInfo(UUIDUtil.randomUUID32(), chatId, groupName, groupNotice, LocalDateTime.now());
    }

    public static CorporationGroupInfo create(String groupId, String chatId, String groupName, String groupNotice) {
        return new CorporationGroupInfo(groupId, chatId, groupName, groupNotice);
    }


    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getGroupNotice() {
        return groupNotice;
    }

    public void setGroupNotice(String groupNotice) {
        this.groupNotice = groupNotice == null ? null : groupNotice.trim();
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
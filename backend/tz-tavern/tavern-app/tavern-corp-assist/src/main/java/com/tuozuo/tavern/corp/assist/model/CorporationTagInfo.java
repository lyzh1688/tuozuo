package com.tuozuo.tavern.corp.assist.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tuozuo.tavern.corp.assist.utils.UUIDUtil;

import java.util.UUID;

@TableName("corporation_tag_info")
public class CorporationTagInfo extends Model<CorporationTagInfo> {
    @TableId
    private String tagId;

    private String tagName;

    public CorporationTagInfo() {
    }

    public CorporationTagInfo(String tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

    public static CorporationTagInfo create(String tagName) {
        return new CorporationTagInfo(UUIDUtil.randomUUID32(), tagName);
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId == null ? null : tagId.trim();
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName == null ? null : tagName.trim();
    }
}
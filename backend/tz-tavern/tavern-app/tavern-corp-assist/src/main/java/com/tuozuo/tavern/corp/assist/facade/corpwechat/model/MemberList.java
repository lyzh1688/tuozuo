package com.tuozuo.tavern.corp.assist.facade.corpwechat.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/9 <br>
 */
public class MemberList {

    @JsonProperty("userid")
    private String userId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("unionid")
    private String unionId;
    @JsonProperty("join_time")
    private String joinTime;
    @JsonProperty("join_scene")
    private String joinScene;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public String getJoinScene() {
        return joinScene;
    }

    public void setJoinScene(String joinScene) {
        this.joinScene = joinScene;
    }
}

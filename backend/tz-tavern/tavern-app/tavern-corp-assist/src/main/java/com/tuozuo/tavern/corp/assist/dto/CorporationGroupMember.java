package com.tuozuo.tavern.corp.assist.dto;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/9 <br>
 */
public class CorporationGroupMember {
    private String userId;          //	群成员id（企业微信，仅后端维护则为空）
    private String userIdBackend;   //	群成员id（后端，未关联则为空）
    private String status;          //	状态：1仅后端维护，2、仅实际群有，3、已关联双端数据
    private String name;            //	外部联系人的名称
    private String avatar;          //	外部联系人头像，第三方不可获取
    private String type;            //	外部联系人的类型，1表示该外部联系人是微信用户，2表示该外部联系人是企业微信用户
    private String gender;          //	外部联系人性别 0-未知 1-男性 2-女性
    private String position;        //	外部联系人的职位，如果外部企业或用户选择隐藏职位，则不返回，仅当联系人类型是企业微信用户时有此字段
    private String corpName;        //	外部联系人所在企业的简称，仅当联系人类型是企业微信用户时有此字段
    private String corpFullName;    //	外部联系人所在企业的主体名称，仅当联系人类型是企业微信用户时有此字段

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserIdBackend() {
        return userIdBackend;
    }

    public void setUserIdBackend(String userIdBackend) {
        this.userIdBackend = userIdBackend;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getCorpFullName() {
        return corpFullName;
    }

    public void setCorpFullName(String corpFullName) {
        this.corpFullName = corpFullName;
    }
}

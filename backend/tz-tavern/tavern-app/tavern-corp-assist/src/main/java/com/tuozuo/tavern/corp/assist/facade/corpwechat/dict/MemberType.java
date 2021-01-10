package com.tuozuo.tavern.corp.assist.facade.corpwechat.dict;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/9 <br>
 */
public enum MemberType {
    CORP_USER("1"), EXTERNAL_USER("2");

    private String type;

    MemberType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}

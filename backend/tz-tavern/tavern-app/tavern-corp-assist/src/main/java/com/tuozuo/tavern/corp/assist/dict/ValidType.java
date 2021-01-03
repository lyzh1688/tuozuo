package com.tuozuo.tavern.corp.assist.dict;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/3 <br>
 */
public enum ValidType {


    VALID("1"), INVALID("0");

    private String type;

    public String getType() {
        return type;
    }

    ValidType(String type) {
        this.type = type;
    }
}

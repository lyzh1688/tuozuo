package com.tuozuo.tavern.corp.assist.dict;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/20 <br>
 */
public enum CorpStatus {

    Company("1", "公司"),
    Independent("2", "个独"),
    Self("3", "个体户");

    private String type;
    private String name;

    CorpStatus(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static CorpStatus getCorpType(String type) {

        for (CorpStatus t : CorpStatus.values()) {
            if (type.equals(t.getType())) {
                return t;
            }
        }
        return null;

    }


}

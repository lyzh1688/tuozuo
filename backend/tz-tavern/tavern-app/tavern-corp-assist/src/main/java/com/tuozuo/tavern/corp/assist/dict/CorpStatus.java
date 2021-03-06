package com.tuozuo.tavern.corp.assist.dict;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/20 <br>
 */
public enum CorpStatus {

//    1、注册中；2、正常；3、转出中；4、已转出；5、注销中；6、已注销

    Registering("1", "注册中"),
    Normal("2", "正常"),
    Outing("3", "转出中"),
    Out("4", "已转出"),
    Canceling("5", "注销中"),
    Cancel("6", "已注销");

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

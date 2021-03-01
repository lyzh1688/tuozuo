package com.tuozuo.tavern.corp.assist.dict;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/20 <br>
 */
public enum Gender {

    Man("1", "男"),
    Female("2", "女");

    private String type;
    private String name;

    Gender(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static Gender getGender(String type) {

        for (Gender t : Gender.values()) {
            if (type.equals(t.getType())) {
                return t;
            }
        }
        return null;

    }


}

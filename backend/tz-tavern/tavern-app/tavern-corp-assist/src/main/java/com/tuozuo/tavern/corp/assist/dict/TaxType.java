package com.tuozuo.tavern.corp.assist.dict;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/20 <br>
 */
public enum TaxType {

    GENERAL("1", "一般纳税人"),
    SMALL("2", "小规模纳税人");

    private String type;
    private String name;

    TaxType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static TaxType getTaxType(String type) {

        for (TaxType t : TaxType.values()) {
            if (type.equals(t.getType())) {
                return t;
            }
        }
        return null;

    }


}

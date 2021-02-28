package com.tuozuo.tavern.corp.assist.dict;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/28 <br>
 */
public enum ContractStatus {
//    0:未审核,1:审核完成,2:审核失败

    AUDIT("0", "未审核"),
    PAY("1", "审核完成"),
    FAILURE("2", "审核失败");

    private String type;
    private String name;

    ContractStatus(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static ContractStatus getInvoiceStatus(String type) {

        for (ContractStatus t : ContractStatus.values()) {
            if (type.equals(t.getType())) {
                return t;
            }
        }
        return null;

    }
}


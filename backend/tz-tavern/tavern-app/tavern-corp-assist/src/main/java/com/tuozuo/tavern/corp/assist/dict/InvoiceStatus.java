package com.tuozuo.tavern.corp.assist.dict;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/28 <br>
 */
public enum InvoiceStatus {
//    0:审核中,1:审核打款,2:审核失败,3:已开具发票

    AUDIT("0", "审核中"),
    PAY("1", "审核打款"),
    FAILURE("2", "审核失败"),
    FINISH("3", "已开具发票");

    private String type;
    private String name;

    InvoiceStatus(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static InvoiceStatus getInvoiceStatus(String type) {

        for (InvoiceStatus t : InvoiceStatus.values()) {
            if (type.equals(t.getType())) {
                return t;
            }
        }
        return null;

    }
}


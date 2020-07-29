package com.tuozuo.tavern.shuiruyi.dict;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/28 <br>
 */
public enum InvoicePattern {
//   1:按收款金额开票,2:按合同金额开票

    CASH("1", "按收款金额开票"),
    CONTRACT("2", "按合同金额开票");

    private String type;
    private String name;

    InvoicePattern(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static InvoicePattern getInvoicePattern(String type) {

        for (InvoicePattern t : InvoicePattern.values()) {
            if (type.equals(t.getType())) {
                return t;
            }
        }
        return null;

    }
}


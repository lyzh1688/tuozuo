package com.tuozuo.tavern.xinruyi.dict;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/19 <br>
 */
public enum PaymentStatus {

//    1:申请中，2:待发放，3:已发放，4审核失败
    APPLYING("1"), TO_PAYOFF("2"), RELEASED("3"), FAILED("4");
    private String status;

    public String getStatus() {
        return status;
    }

    PaymentStatus(String status) {
        this.status = status;
    }


}

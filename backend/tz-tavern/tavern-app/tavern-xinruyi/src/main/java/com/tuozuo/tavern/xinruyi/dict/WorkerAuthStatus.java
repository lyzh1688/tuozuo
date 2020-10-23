package com.tuozuo.tavern.xinruyi.dict;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/19 <br>
 */
public enum WorkerAuthStatus {

    //    0,未实名,1,认证中，2已认证，3认证失败
    UNREGISTER("0"), REGISTERING("1"), REGISTERED("2"),FAILED("3"),;
    private String status;

    public String getStatus() {
        return status;
    }

    WorkerAuthStatus(String status) {
        this.status = status;
    }


}

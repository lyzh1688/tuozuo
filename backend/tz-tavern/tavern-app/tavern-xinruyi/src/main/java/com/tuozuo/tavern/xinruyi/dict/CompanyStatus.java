package com.tuozuo.tavern.xinruyi.dict;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/9 <br>
 */
public enum CompanyStatus {
    //    1:入住申请中,2:已经入住,3:不入住,4:认证申请中,5：认证失败,6:认证完成
    APPLYING("1"), APPLY_SUCCESS("2"), APPLY_FAILED("3"), AUTH_APPLYING("4"), AUTH_FAILED("5"), DONE("6");
    private String status;

    public String getStatus() {
        return status;
    }

    CompanyStatus(String status) {
        this.status = status;
    }
}

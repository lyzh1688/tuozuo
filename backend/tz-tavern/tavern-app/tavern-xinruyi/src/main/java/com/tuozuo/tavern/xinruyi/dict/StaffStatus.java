package com.tuozuo.tavern.xinruyi.dict;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/19 <br>
 */
public enum StaffStatus {

//    0:离职,1:冻结2:在职
    LEAVE("0"), FROZEN("1"), IN_SERVICE("2");
    private String status;

    public String getStatus() {
        return status;
    }

    StaffStatus(String status) {
        this.status = status;
    }


}

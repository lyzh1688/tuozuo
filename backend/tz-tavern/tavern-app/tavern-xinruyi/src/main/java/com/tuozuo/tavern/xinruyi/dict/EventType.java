package com.tuozuo.tavern.xinruyi.dict;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/19 <br>
 */
public enum EventType {
/*9	发放工资客户确认	eventType
2	企业认证	eventType
7	裁员变动	eventType
6	人员实名	eventType
3	项目发布	eventType
4	项目完成	eventType
1	企业入驻	eventType
5	人员加入	eventType
8	发放工资申请	eventType
10	发放工资事件	eventType*/

    ENTERPRISE_APPLY("1"), ENTERPRISE_AUTH("2"), PROJECT_RELEASE("3"), PROJECT_DONE("4"),
    STAFF_JOIN("5"), STAFF_AUTH("6"), STAFF_FIRE("7"), SALARY_RELEASE_APPLY("8"),
    SALARY_RELEASE_CONFIRM("9"), SALARY_RELEASE("10");
    private String status;

    public String getStatus() {
        return status;
    }

    EventType(String status) {
        this.status = status;
    }


}

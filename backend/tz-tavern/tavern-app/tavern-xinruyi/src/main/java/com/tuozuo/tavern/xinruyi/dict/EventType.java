package com.tuozuo.tavern.xinruyi.dict;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/19 <br>
 */
public enum EventType {
/*5	人员加入	eventType
7	裁员变动	eventType
2	企业认证	eventType
3	项目发布	eventType
4	项目完成	eventType
6	人员实名	eventType
1	企业入驻	eventType*/

    ENTERPISE_APPLY("1"), ENTERPISE_AUTH("2"), PROJECT_RELEASE("3"), PROJECT_DONE("4"), STAFF_JOIN("5"), STAFF_AUTH("6"), STAFF_FIRE("7");
    private String status;

    public String getStatus() {
        return status;
    }

    EventType(String status) {
        this.status = status;
    }


}

package com.tuozuo.tavern.corp.assist.dict;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
public enum BusinessStatus {

    REG_AUDIT_NAME("注册核名中"),
    REG_AUDIT_TAX("注册核税种"),
    REG_DONE("注册完成"),
    SERVICE("服务中"),
    ARREARAGE("欠费中"),
    TAX_CANCEL("税务注销中"),
    BIZ_CANCEL("工商注销"),
    FINISH("服务结束");

    private String status;

    BusinessStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

   public static BusinessStatus getBusinessStatus(String status){
       for (BusinessStatus t : BusinessStatus.values()) {
           if (status.equals(t.name())) {
               return t;
           }
       }
       return null;
   }
}

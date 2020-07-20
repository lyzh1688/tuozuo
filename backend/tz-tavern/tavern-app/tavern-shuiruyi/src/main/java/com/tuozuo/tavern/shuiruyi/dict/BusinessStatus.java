package com.tuozuo.tavern.shuiruyi.dict;

/**
 * 功能说明: <br>
 * 系统说明: <br>
 * 模块说明: <br>
 * 功能描述: <br>
 * <br>
 *
 * @author hebiao@orientsec.com.cn
 * <br>
 * 软件著作: 东方证券 版权所有
 * @since 1.0
 */
public enum BusinessStatus {

    REG_AUDIT_NAME("注册核名中"),
    REG_AUDIT_TAX("注册核税种"),
    REG_DOND("注册完成"),
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

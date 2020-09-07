package com.tuozuo.tavern.xinruyi.dict;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/19 <br>
 */
public enum ProjectStatus {

//    1:申请中，2:申请失败,3:申请成功,4:发布中,5:进行中6:完成审核中7:完成
    APPLYING("1"), APPLY_FAILUER("2"), APPLY_SUCCESS("3"), RELEASE("4"), PROCESSING("5"), AUDITED("6"), DONE("7");
    private String status;

    public String getStatus() {
        return status;
    }

    ProjectStatus(String status) {
        this.status = status;
    }


}

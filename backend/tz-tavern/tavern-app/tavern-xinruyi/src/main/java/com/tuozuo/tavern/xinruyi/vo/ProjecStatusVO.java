package com.tuozuo.tavern.xinruyi.vo;

import javax.validation.constraints.NotNull;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/1 <br>
 */
public class ProjecStatusVO {
    @NotNull(message = "status is not null")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

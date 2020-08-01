package com.tuozuo.tavern.shuiruyi.vo;

import javax.validation.constraints.NotNull;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
public class CustomAddInfoVO extends CustomInfoVO {

    @NotNull(message = "customId is not null")
    private String customId;

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }
}

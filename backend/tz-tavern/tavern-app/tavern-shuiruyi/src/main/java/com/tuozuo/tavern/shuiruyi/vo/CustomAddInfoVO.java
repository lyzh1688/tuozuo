package com.tuozuo.tavern.shuiruyi.vo;

import javax.validation.constraints.NotNull;

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

package com.tuozuo.tavern.shuiruyi.vo;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/08/10 <br>
 */
public class CfoInfoVO {
    @NotNull(message = "cfoName is not null")
    private String cfoName;
    @NotNull(message = "cfoId is not null")
    private String cfoId;
    @NotNull(message = "cfoContact is not null")
    private String cfoContact;
    @NotNull(message = "cfoIdPicUp is not null")
    private MultipartFile cfoIdPicUp;
    @NotNull(message = "cfoIdPicBack is not null")
    private MultipartFile cfoIdPicBack;

    public String getCfoName() {
        return cfoName;
    }

    public void setCfoName(String cfoName) {
        this.cfoName = cfoName;
    }

    public String getCfoId() {
        return cfoId;
    }

    public void setCfoId(String cfoId) {
        this.cfoId = cfoId;
    }

    public String getCfoContact() {
        return cfoContact;
    }

    public void setCfoContact(String cfoContact) {
        this.cfoContact = cfoContact;
    }

    public MultipartFile getCfoIdPicUp() {
        return cfoIdPicUp;
    }

    public void setCfoIdPicUp(MultipartFile cfoIdPicUp) {
        this.cfoIdPicUp = cfoIdPicUp;
    }

    public MultipartFile getCfoIdPicBack() {
        return cfoIdPicBack;
    }

    public void setCfoIdPicBack(MultipartFile cfoIdPicBack) {
        this.cfoIdPicBack = cfoIdPicBack;
    }
}

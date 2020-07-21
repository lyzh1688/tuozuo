package com.tuozuo.tavern.shuiruyi.vo;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/08/10 <br>
 */
public class BossInfoVO {
    @NotNull(message = "bossName is not null")
    private String bossName;
    @NotNull(message = "bossId is not null")
    private String bossId;
    @NotNull(message = "bossContact is not null")
    private String bossContact;
    @NotNull(message = "bossIdPicUp is not null")
    private MultipartFile bossIdPicUp;
    @NotNull(message = "bossIdPicBack is not null")
    private MultipartFile bossIdPicBack;

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public String getBossId() {
        return bossId;
    }

    public void setBossId(String bossId) {
        this.bossId = bossId;
    }

    public String getBossContact() {
        return bossContact;
    }

    public void setBossContact(String bossContact) {
        this.bossContact = bossContact;
    }

    public MultipartFile getBossIdPicUp() {
        return bossIdPicUp;
    }

    public void setBossIdPicUp(MultipartFile bossIdPicUp) {
        this.bossIdPicUp = bossIdPicUp;
    }

    public MultipartFile getBossIdPicBack() {
        return bossIdPicBack;
    }

    public void setBossIdPicBack(MultipartFile bossIdPicBack) {
        this.bossIdPicBack = bossIdPicBack;
    }
}

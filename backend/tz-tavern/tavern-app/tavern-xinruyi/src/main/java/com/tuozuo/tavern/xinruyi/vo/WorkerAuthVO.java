package com.tuozuo.tavern.xinruyi.vo;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

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
public class WorkerAuthVO {

    private String registerId;
    @NotNull(message = "name is not null")
    private String name;
    @NotNull(message = "idNo is not null")
    private String idNo;
    private MultipartFile video;
    private MultipartFile idPicUp;
    private MultipartFile idPicDown;
    @NotNull(message = "idNo is not null")
    private String contact;

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public MultipartFile getVideo() {
        return video;
    }

    public void setVideo(MultipartFile video) {
        this.video = video;
    }

    public MultipartFile getIdPicUp() {
        return idPicUp;
    }

    public void setIdPicUp(MultipartFile idPicUp) {
        this.idPicUp = idPicUp;
    }

    public MultipartFile getIdPicDown() {
        return idPicDown;
    }

    public void setIdPicDown(MultipartFile idPicDown) {
        this.idPicDown = idPicDown;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}

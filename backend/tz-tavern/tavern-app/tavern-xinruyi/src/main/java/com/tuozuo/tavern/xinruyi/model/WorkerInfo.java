package com.tuozuo.tavern.xinruyi.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("worker_info")
public class WorkerInfo {

    @TableId
    private String registerId;

    private String idNumber;

    private String name;

    private String isCertificate;

    private String video;
    private String idPicUp;
    private String idPicBack;
    private String contact;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getIdPicUp() {
        return idPicUp;
    }

    public void setIdPicUp(String idPicUp) {
        this.idPicUp = idPicUp;
    }

    public String getIdPicBack() {
        return idPicBack;
    }

    public void setIdPicBack(String idPicBack) {
        this.idPicBack = idPicBack;
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId == null ? null : registerId.trim();
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIsCertificate() {
        return isCertificate;
    }

    public void setIsCertificate(String isCertificate) {
        this.isCertificate = isCertificate == null ? null : isCertificate.trim();
    }
}
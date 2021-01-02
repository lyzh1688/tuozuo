package com.tuozuo.tavern.corp.assist.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

public class CorporationClientInfo extends Model<CorporationClientInfo> {
    @TableId
    private String clientId;

    private String clientName;

    private String clientNumber;

    private String clientGender;

    private String contact;

    private String contactNumber;

    private String contactGender;

    private String alitalk;

    private String operator;

    private Date createTime;

    private String valid;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName == null ? null : clientName.trim();
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber == null ? null : clientNumber.trim();
    }

    public String getClientGender() {
        return clientGender;
    }

    public void setClientGender(String clientGender) {
        this.clientGender = clientGender == null ? null : clientGender.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber == null ? null : contactNumber.trim();
    }

    public String getContactGender() {
        return contactGender;
    }

    public void setContactGender(String contactGender) {
        this.contactGender = contactGender == null ? null : contactGender.trim();
    }

    public String getAlitalk() {
        return alitalk;
    }

    public void setAlitalk(String alitalk) {
        this.alitalk = alitalk == null ? null : alitalk.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid == null ? null : valid.trim();
    }
}
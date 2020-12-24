package com.tuozuo.tavern.corp.assist.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;
@TableName("company_contract_flow")
public class CompanyContractFlow extends Model<CompanyContractFlow> {
    private String flowId;

    private String staffId;

    private String templateId;

    private String templateField;

    private String contractSignedUrl;

    private String contractSignedPicUrl;

    private String contractSignedPath;

    private Date contractDate;

    private String clientName;

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId == null ? null : flowId.trim();
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId == null ? null : staffId.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getTemplateField() {
        return templateField;
    }

    public void setTemplateField(String templateField) {
        this.templateField = templateField == null ? null : templateField.trim();
    }

    public String getContractSignedUrl() {
        return contractSignedUrl;
    }

    public void setContractSignedUrl(String contractSignedUrl) {
        this.contractSignedUrl = contractSignedUrl == null ? null : contractSignedUrl.trim();
    }

    public String getContractSignedPicUrl() {
        return contractSignedPicUrl;
    }

    public void setContractSignedPicUrl(String contractSignedPicUrl) {
        this.contractSignedPicUrl = contractSignedPicUrl == null ? null : contractSignedPicUrl.trim();
    }

    public String getContractSignedPath() {
        return contractSignedPath;
    }

    public void setContractSignedPath(String contractSignedPath) {
        this.contractSignedPath = contractSignedPath == null ? null : contractSignedPath.trim();
    }

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName == null ? null : clientName.trim();
    }
}
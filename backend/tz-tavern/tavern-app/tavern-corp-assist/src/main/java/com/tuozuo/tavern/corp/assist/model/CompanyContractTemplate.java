package com.tuozuo.tavern.corp.assist.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;

public class CompanyContractTemplate extends Model<CompanyContractTemplate> {
    private String templateId;

    private String templateName;

    private String templateField;

    private String templateFileUrl;

    private String templateClass;

    private String templateFilePath;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
    }

    public String getTemplateField() {
        return templateField;
    }

    public void setTemplateField(String templateField) {
        this.templateField = templateField == null ? null : templateField.trim();
    }

    public String getTemplateFileUrl() {
        return templateFileUrl;
    }

    public void setTemplateFileUrl(String templateFileUrl) {
        this.templateFileUrl = templateFileUrl == null ? null : templateFileUrl.trim();
    }

    public String getTemplateClass() {
        return templateClass;
    }

    public void setTemplateClass(String templateClass) {
        this.templateClass = templateClass == null ? null : templateClass.trim();
    }

    public String getTemplateFilePath() {
        return templateFilePath;
    }

    public void setTemplateFilePath(String templateFilePath) {
        this.templateFilePath = templateFilePath == null ? null : templateFilePath.trim();
    }
}
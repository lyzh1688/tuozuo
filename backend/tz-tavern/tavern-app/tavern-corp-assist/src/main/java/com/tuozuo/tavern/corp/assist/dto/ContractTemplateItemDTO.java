package com.tuozuo.tavern.corp.assist.dto;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/12/24 <br>
 */
public class ContractTemplateItemDTO {
    private String templateId;
    private String templateName;
    private String templateField;
    private String templateFileUrl;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateField() {
        return templateField;
    }

    public void setTemplateField(String templateField) {
        this.templateField = templateField;
    }

    public String getTemplateFileUrl() {
        return templateFileUrl;
    }

    public void setTemplateFileUrl(String templateFileUrl) {
        this.templateFileUrl = templateFileUrl;
    }
}

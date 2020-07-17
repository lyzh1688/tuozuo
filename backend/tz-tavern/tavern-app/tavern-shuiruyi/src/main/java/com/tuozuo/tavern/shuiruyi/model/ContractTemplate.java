package com.tuozuo.tavern.shuiruyi.model;

public class ContractTemplate {
    private String contractTemplteId;

    private String contractTemplateName;

    private String templateFile;

    public String getContractTemplteId() {
        return contractTemplteId;
    }

    public void setContractTemplteId(String contractTemplteId) {
        this.contractTemplteId = contractTemplteId == null ? null : contractTemplteId.trim();
    }

    public String getContractTemplateName() {
        return contractTemplateName;
    }

    public void setContractTemplateName(String contractTemplateName) {
        this.contractTemplateName = contractTemplateName == null ? null : contractTemplateName.trim();
    }

    public String getTemplateFile() {
        return templateFile;
    }

    public void setTemplateFile(String templateFile) {
        this.templateFile = templateFile == null ? null : templateFile.trim();
    }
}
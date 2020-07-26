package com.tuozuo.tavern.shuiruyi.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;

public class ContractTemplate extends Model<ContractTemplate> {
    private String contractTemplateId;

    private String contractTemplateName;

    private String templateFile;

    public String getContractTemplateId() {
        return contractTemplateId;
    }

    public void setContractTemplateId(String contractTemplateId) {
        this.contractTemplateId = contractTemplateId;
    }

    public String getContractTemplateName() {
        return contractTemplateName;
    }

    public void setContractTemplateName(String contractTemplateName) {
        this.contractTemplateName = contractTemplateName;
    }

    public String getTemplateFile() {
        return templateFile;
    }

    public void setTemplateFile(String templateFile) {
        this.templateFile = templateFile;
    }
}
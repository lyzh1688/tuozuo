package com.tuozuo.tavern.corp.assist.dto;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/12/24 <br>
 */
public class ContractTemplateDTO {
    private String templateClass;
    private List<ContractTemplateItemDTO> templates;

    public String getTemplateClass() {
        return templateClass;
    }

    public void setTemplateClass(String templateClass) {
        this.templateClass = templateClass;
    }

    public List<ContractTemplateItemDTO> getTemplates() {
        return templates;
    }

    public void setTemplates(List<ContractTemplateItemDTO> templates) {
        this.templates = templates;
    }
}

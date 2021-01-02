package com.tuozuo.tavern.corp.assist.endpoint;

import com.google.common.collect.Lists;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.corp.assist.convert.ModelMapConverterFactory;
import com.tuozuo.tavern.corp.assist.dto.ContractTemplateDTO;
import com.tuozuo.tavern.corp.assist.dto.ContractTemplateItemDTO;
import com.tuozuo.tavern.corp.assist.model.CorporationContractTemplate;
import com.tuozuo.tavern.corp.assist.service.CorporationContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/12/24 <br>
 */
@RestController
@RequestMapping("/tuozuo/corporation/v1/contract/")
public class CorporationContractEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(CorporationContractEndpoint.class);

    @Autowired
    private CorporationContractService corporationContractService;

    @Autowired
    private ModelMapConverterFactory converterFactory;

    @GetMapping("template")
    public TavernResponse queryContractTemplate() {
        try {
            List<CorporationContractTemplate> templates = this.corporationContractService.queryAllCompanyContractTemplate();
            Map<String, List<CorporationContractTemplate>> templateMap = templates.stream()
                    .collect(Collectors.groupingBy(CorporationContractTemplate::getTemplateClass));
            List<ContractTemplateDTO> contractTemplateDTOList = Lists.newArrayList();
            for (Map.Entry<String, List<CorporationContractTemplate>> entry : templateMap.entrySet()) {
                ContractTemplateDTO contractTemplateDTO = new ContractTemplateDTO();
                String key = entry.getKey();
                List<CorporationContractTemplate> value = entry.getValue();
                contractTemplateDTO.setTemplateClass(key);
                List<ContractTemplateItemDTO> itemDTOList = value.stream()
                        .map(this.converterFactory::templateModelToDTO)
                        .collect(Collectors.toList());
                contractTemplateDTO.setTemplates(itemDTOList);
                contractTemplateDTOList.add(contractTemplateDTO);
            }
            return TavernResponse.ok(contractTemplateDTOList);
        } catch (Exception e) {
            LOGGER.error("[模板列表] failed", e);
            return TavernResponse.bizFailure("系统查询异常，请稍后再试");
        }

    }

    @GetMapping("preview")
    public void previewContractFile(HttpServletResponse response,
                                    @RequestParam(value = "templateId", required = false) String templateId,
                                    @RequestParam(value = "flowId", required = false) String flowId) {
        try {
            this.corporationContractService.previewContractFile(response, templateId, flowId);
        } catch (Exception e) {
            LOGGER.error("[模板列表] failed", e);
        }
    }
}

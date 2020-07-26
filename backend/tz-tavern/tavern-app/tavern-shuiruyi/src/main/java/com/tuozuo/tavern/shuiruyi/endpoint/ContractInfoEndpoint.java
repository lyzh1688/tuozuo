package com.tuozuo.tavern.shuiruyi.endpoint;

import com.google.common.collect.Lists;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.shuiruyi.dto.ContractListDTO;
import com.tuozuo.tavern.shuiruyi.dto.ContractTemplateDTO;
import com.tuozuo.tavern.shuiruyi.vo.ContractInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/25 <br>
 */
@RestController
@RequestMapping("/tuozuo/shuiruyi/v1/contract")
public class ContractInfoEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractInfoEndpoint.class);

    /**
     * 合同列表
     */
    @GetMapping("/list")
    public TavernResponse queryContractList(@RequestParam(value = "companyId", required = false) String companyId,
                                            @RequestParam(value = "pageNo") int pageNo,
                                            @RequestParam(value = "pageSize") int pageSize) {
        try {
            ContractListDTO contractListDTO = new ContractListDTO();
            return TavernResponse.ok(contractListDTO);
        } catch (Exception e) {
            LOGGER.error("[合同列表] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }


    /**
     * 创建合同
     */
    @PostMapping()
    public TavernResponse addCustomInfo(@ModelAttribute @Valid ContractInfoVO vo,
                                        @RequestParam(value = "contractFile") MultipartFile contractFile) {
        try {
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[新建合同] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }


    /**
     * 合同模板下载
     */
    @GetMapping("/template")
    public TavernResponse queryContractTemplateList() {
        try {
            List<ContractTemplateDTO> contractTemplateDTOList = Lists.newArrayList();
            return TavernResponse.ok(contractTemplateDTOList);
        } catch (Exception e) {
            LOGGER.error("[合同列表] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

}

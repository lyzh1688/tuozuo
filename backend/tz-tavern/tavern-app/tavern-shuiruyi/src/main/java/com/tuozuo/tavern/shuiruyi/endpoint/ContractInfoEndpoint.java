package com.tuozuo.tavern.shuiruyi.endpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernRequestAuthFields;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.shuiruyi.convert.BusinessConverter;
import com.tuozuo.tavern.shuiruyi.dto.BusinessDictDTO;
import com.tuozuo.tavern.shuiruyi.dto.ContractItemDTO;
import com.tuozuo.tavern.shuiruyi.dto.ContractListDTO;
import com.tuozuo.tavern.shuiruyi.dto.ContractTemplateDTO;
import com.tuozuo.tavern.shuiruyi.model.ContractDetailInfo;
import com.tuozuo.tavern.shuiruyi.service.ContractInfoService;
import com.tuozuo.tavern.shuiruyi.vo.ContractAuditVO;
import com.tuozuo.tavern.shuiruyi.vo.ContractInfoVO;
import com.tuozuo.tavern.shuiruyi.vo.ContractModifyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/25 <br>
 */
@RestController
@RequestMapping("/tuozuo/shuiruyi/v1/contract")
public class ContractInfoEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractInfoEndpoint.class);

    @Autowired
    private ContractInfoService contractInfoService;

    /**
     * 合同列表
     */
    @GetMapping("/list")
    public TavernResponse queryContractList(@RequestParam(value = "companyId", required = false) String companyId,
                                            @RequestParam(value = "pageNo") int pageNo,
                                            @RequestParam(value = "pageSize") int pageSize,
                                            @RequestHeader(TavernRequestAuthFields.USER_ID) String customId,
                                            @RequestHeader(TavernRequestAuthFields.ROLE_GROUP) String roleGroup) {
        try {
            IPage<ContractDetailInfo> page = this.contractInfoService.queryContractList(companyId, pageNo, pageSize, customId, roleGroup);
            List<ContractItemDTO> items = page.getRecords().stream()
                    .map(BusinessConverter::modelToContractItemDTO)
                    .collect(Collectors.toList());
            ContractListDTO contractListDTO = new ContractListDTO();
            contractListDTO.setContracts(items);
            contractListDTO.setTotal((int) page.getTotal());
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
    public TavernResponse addContractInfo(@ModelAttribute @Valid ContractInfoVO vo,
                                          @RequestParam(value = "contractFile") MultipartFile contractFile) {
        try {
            vo.setContractFile(contractFile);
            this.contractInfoService.addContractInfo(vo);
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
            List<ContractTemplateDTO> contractTemplateDTOList = this.contractInfoService.queryContractTemplateList()
                    .stream()
                    .map(BusinessConverter::modelToContractTemplateDTO)
                    .collect(Collectors.toList());
            return TavernResponse.ok(contractTemplateDTOList);
        } catch (Exception e) {
            LOGGER.error("[合同列表] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 合同模糊搜索
     */
    @GetMapping("/contracts")
    public TavernResponse queryContracts(@RequestParam(name = "contractStatus", required = false, defaultValue = "") String contractStatus,
                                         @RequestParam(name = "contractName", required = false, defaultValue = "") String contractName,
                                         @RequestParam(name = "queryCnt", required = false, defaultValue = "20") int queryCnt,
                                         @RequestHeader(TavernRequestAuthFields.USER_ID) String customId,
                                         @RequestHeader(TavernRequestAuthFields.ROLE_GROUP) String roleGroup) {
        try {
            List<BusinessDictDTO> businessDictList = this.contractInfoService.fuzzyQueryContractInfo(contractStatus, contractName, queryCnt, customId, roleGroup)
                    .stream()
                    .map(contractInfo -> {
                        BusinessDictDTO dict = new BusinessDictDTO();
                        dict.setId(contractInfo.getContractId());
                        dict.setName(contractInfo.getContractName());
                        return dict;
                    }).collect(Collectors.toList());
            return TavernResponse.ok(businessDictList);
        } catch (Exception e) {
            LOGGER.error("[合同模糊搜索] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 合同修改
     */
    @PutMapping(value = "/{contractId}")
    public TavernResponse modifyCompanyInfo(@PathVariable("contractId") String contractId,
                                            @ModelAttribute @Valid ContractModifyVO vo,
                                            @RequestParam(name = "contractFile", required = false) MultipartFile contractFile) {
        try {
            if (contractFile != null) {
                LOGGER.info("contractFile: {}", contractFile.getOriginalFilename());
            }
            vo.setContractId(contractId);
            vo.setContractFile(contractFile);
            this.contractInfoService.modifyContractInfo(vo);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[合同审核修改] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 合同审核
     */
    @PutMapping(value = "/audit/{contractId}")
    public TavernResponse modifyCompanyInfo(@PathVariable("contractId") String contractId,
                                            @RequestBody ContractAuditVO vo) {
        try {
            this.contractInfoService.auditContractInfo(contractId, vo.getContractStatus(), vo.getRemark());
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[合同审核] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }
}

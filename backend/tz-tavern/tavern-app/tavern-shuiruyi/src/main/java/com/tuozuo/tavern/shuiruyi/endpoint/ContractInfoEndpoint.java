package com.tuozuo.tavern.shuiruyi.endpoint;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernRequestAuthFields;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.shuiruyi.convert.BusinessConverter;
import com.tuozuo.tavern.shuiruyi.dict.Event;
import com.tuozuo.tavern.shuiruyi.dto.ContractItemDTO;
import com.tuozuo.tavern.shuiruyi.dto.ContractListDTO;
import com.tuozuo.tavern.shuiruyi.dto.ContractSearchDTO;
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
import java.math.BigDecimal;
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
     * 合同详情
     */
    @GetMapping("/detail/{contractId}")
    public TavernResponse queryCompanyDetail(@PathVariable("contractId") String contractId) {
        try {
            ContractDetailInfo contractDetailInfo = this.contractInfoService.queryContractDetail(contractId);
            if (contractDetailInfo == null) {
                throw new Exception("该合同不存在");
            }
            ContractItemDTO itemDTO = BusinessConverter.modelToContractDetailDTO(contractDetailInfo);
            return TavernResponse.ok(itemDTO);
        } catch (Exception e) {
            LOGGER.error("[合同详情] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 创建合同
     */
    @PostMapping()
    public TavernResponse addContractInfo(@ModelAttribute @Valid ContractInfoVO vo,
                                          @RequestParam(value = "contractFile") MultipartFile contractFile,
                                          @RequestHeader(TavernRequestAuthFields.USER_ID) String customId,
                                          @RequestHeader(TavernRequestAuthFields.ROLE_GROUP) String roleGroup) {
        try {
            vo.setContractFile(contractFile);
            String contractId = this.contractInfoService.addContractInfo(vo);
            this.contractInfoService.addContractFlow(contractId, Event.CREATE, customId, roleGroup);
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
            List<ContractSearchDTO> businessDictList = this.contractInfoService.fuzzyQueryContractInfo(contractStatus, contractName, queryCnt, customId, roleGroup)
                    .stream()
                    .map(contractInfo -> {
                        ContractSearchDTO contractSearchDTO = new ContractSearchDTO();
                        contractSearchDTO.setId(contractInfo.getContractId());
                        contractSearchDTO.setName(contractInfo.getContractName());
                        contractSearchDTO.setCompanyId(contractInfo.getCompanyId());
                        contractSearchDTO.setCompanyName(contractInfo.getCompanyPartyBName());
                        double invoicedAmount = contractInfo.getInvoicedAmount() != null ? contractInfo.getInvoicedAmount().doubleValue() : BigDecimal.ZERO.doubleValue();
                        double contractAmount = contractInfo.getContractAmount() != null ? contractInfo.getContractAmount().doubleValue() : BigDecimal.ZERO.doubleValue();
                        contractSearchDTO.setInvoicedAmount(invoicedAmount);
                        contractSearchDTO.setContractAmount(contractAmount);
                        return contractSearchDTO;
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
                                            @RequestParam(name = "contractFile", required = false) MultipartFile contractFile,
                                            @RequestHeader(TavernRequestAuthFields.USER_ID) String customId,
                                            @RequestHeader(TavernRequestAuthFields.ROLE_GROUP) String roleGroup) {
        try {
            if (contractFile != null) {
                LOGGER.info("contractFile: {}", contractFile.getOriginalFilename());
            }
            vo.setContractId(contractId);
            vo.setContractFile(contractFile);
            this.contractInfoService.modifyContractInfo(vo);
            this.contractInfoService.addContractFlow(contractId, Event.UPDATE, customId, roleGroup);
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
                                            @RequestBody ContractAuditVO vo,
                                            @RequestHeader(TavernRequestAuthFields.USER_ID) String customId,
                                            @RequestHeader(TavernRequestAuthFields.ROLE_GROUP) String roleGroup) {
        try {
            LOGGER.info("audit: {},contractId: {}", JSON.toJSONString(vo), contractId);
            this.contractInfoService.auditContractInfo(contractId, vo.getContractStatus(), vo.getRemark());
            this.contractInfoService.addContractFlow(contractId, Event.AUDIT, customId, roleGroup);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[合同审核] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }
}

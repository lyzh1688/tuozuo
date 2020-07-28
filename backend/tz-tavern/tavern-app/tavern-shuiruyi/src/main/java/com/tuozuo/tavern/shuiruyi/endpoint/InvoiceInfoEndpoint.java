package com.tuozuo.tavern.shuiruyi.endpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernRequestAuthFields;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.shuiruyi.convert.BusinessConverter;
import com.tuozuo.tavern.shuiruyi.dto.InvoiceInfoDTO;
import com.tuozuo.tavern.shuiruyi.dto.InvoiceItemDTO;
import com.tuozuo.tavern.shuiruyi.dto.InvoiceListDTO;
import com.tuozuo.tavern.shuiruyi.model.InvoiceDetailInfo;
import com.tuozuo.tavern.shuiruyi.service.InvoiceInfoService;
import com.tuozuo.tavern.shuiruyi.vo.InvoiceAuditVO;
import com.tuozuo.tavern.shuiruyi.vo.InvoiceInfoVO;
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
 * Dev Time: 2020/7/26 <br>
 */
@RestController
@RequestMapping("/tuozuo/shuiruyi/v1/invoice")
public class InvoiceInfoEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceInfoEndpoint.class);

    @Autowired
    private InvoiceInfoService invoiceInfoService;

    /**
     * 开票列表
     */
    @GetMapping("/list")
    public TavernResponse queryInvoiceList(@RequestParam(value = "companyId", required = false) String companyId,
                                           @RequestParam(value = "contractId", required = false) String contractId,
                                           @RequestParam(value = "invoiceStatus", required = false) String invoiceStatus,
                                           @RequestParam(value = "pageNo") int pageNo,
                                           @RequestParam(value = "pageSize") int pageSize,
                                           @RequestHeader(TavernRequestAuthFields.USER_ID) String customId,
                                           @RequestHeader(TavernRequestAuthFields.ROLE_GROUP) String roleGroup) {
        try {
            IPage<InvoiceDetailInfo> page = this.invoiceInfoService.queryInvoiceInfoList(companyId, contractId, invoiceStatus, pageNo, pageSize, customId, roleGroup);
            List<InvoiceItemDTO> invoiceInfoDTOList = page.getRecords()
                    .stream()
                    .map(BusinessConverter::modelToInvoiceItemDTO)
                    .collect(Collectors.toList());
            InvoiceListDTO invoiceListDTO = new InvoiceListDTO();
            invoiceListDTO.setInvoices(invoiceInfoDTOList);
            invoiceListDTO.setTotal((int) page.getTotal());
            return TavernResponse.ok(invoiceListDTO);
        } catch (Exception e) {
            LOGGER.error("[开票列表] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 申请开票
     */
    @PostMapping()
    public TavernResponse addInvoice(@ModelAttribute @Valid InvoiceInfoVO vo,
                                     @RequestParam(value = "authLetterFile") MultipartFile authLetterFile,
                                     @RequestParam(value = "bankFlowFile") MultipartFile bankFlowFile) {
        try {
            vo.setAuthLetterFile(authLetterFile);
            vo.setBankFlowFile(bankFlowFile);
            this.invoiceInfoService.createInvoice(vo);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[申请开票] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 开票详情
     */
    @GetMapping("/detail/{invoiceId}")
    public TavernResponse queryInvoiceDetail(@PathVariable("invoiceId") String invoiceId) {
        try {
            InvoiceDetailInfo invoiceDetailInfo = this.invoiceInfoService.queryInvoiceInfo(invoiceId);
            InvoiceInfoDTO invoiceInfoDTO = BusinessConverter.modelToInvoiceInfo(invoiceDetailInfo);
            return TavernResponse.ok(invoiceInfoDTO);
        } catch (Exception e) {
            LOGGER.error("[开票详情] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 开票修改
     */
    @PutMapping("/{invoiceId}")
    public TavernResponse modifyInvoiceInfo(@PathVariable("invoiceId") String invoiceId,
                                            @ModelAttribute @Valid InvoiceInfoVO vo,
                                            @RequestParam(value = "authLetterFile", required = false) MultipartFile authLetterFile,
                                            @RequestParam(value = "bankFlowFile", required = false) MultipartFile bankFlowFile
    ) {
        try {
            vo.setAuthLetterFile(authLetterFile);
            vo.setBankFlowFile(bankFlowFile);
            vo.setInvoiceId(invoiceId);
            this.invoiceInfoService.modifyInvoiceInfo(vo);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[开票修改] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 发票审核
     */
    @PutMapping(value = "/audit/{invoiceId}")
    public TavernResponse auditInvoiceInfo(@PathVariable("invoiceId") String invoiceId, @RequestBody @Valid InvoiceAuditVO vo) {
        try {
            this.invoiceInfoService.auditInvoiceInfo(invoiceId,vo.getInvoiceStatus(),vo.getDeliveryId(),vo.getRemark());
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[发票审核] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }
}

package com.tuozuo.tavern.shuiruyi.endpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernRequestAuthFields;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.shuiruyi.dto.InvoiceStatisticDTO;
import com.tuozuo.tavern.shuiruyi.model.InvoiceStatistic;
import com.tuozuo.tavern.shuiruyi.service.InvoiceInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
@RestController
@RequestMapping("/tuozuo/shuiruyi/v1/bizControl")
public class BizControlEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(BizControlEndpoint.class);

    @Autowired
    private InvoiceInfoService invoiceInfoService;

    /**
     * 开票统计
     */
    @GetMapping("/invoice/statistics")
    public TavernResponse queryInvoiceStatistic(@RequestParam(value = "beginMonth", required = false) String beginMonth,
                                                @RequestParam(value = "endMonth", required = false) String endMonth,
                                                @RequestParam(value = "companyId", required = false) String companyId,
                                                @RequestParam(value = "pageNo") int pageNo,
                                                @RequestParam(value = "pageSize") int pageSize,
                                                @RequestHeader(TavernRequestAuthFields.USER_ID) String customId) {
        try {
            return queryTavernResponse(beginMonth, endMonth, companyId, customId, pageNo, pageSize);
        } catch (Exception e) {
            LOGGER.error("[开票统计] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 缴税明细
     */
    @GetMapping("/invoice/detail")
    public TavernResponse queryInvoiceDetail(@RequestParam(value = "beginMonth", required = false) String beginMonth,
                                             @RequestParam(value = "endMonth", required = false) String endMonth,
                                             @RequestParam(value = "companyId", required = false) String companyId,
                                             @RequestParam(value = "pageNo") int pageNo,
                                             @RequestParam(value = "pageSize") int pageSize,
                                             @RequestHeader(TavernRequestAuthFields.USER_ID) String customId) {
        try {
            return queryTavernResponse(beginMonth, endMonth, companyId, customId, pageNo, pageSize);
        } catch (Exception e) {
            LOGGER.error("[缴税明细] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    private TavernResponse queryTavernResponse(String beginMonth, String endMonth, String companyId, String customId, int pageNo, int pageSize) {
        IPage<InvoiceStatistic> page = this.invoiceInfoService.queryInvoiceStatistics(beginMonth, endMonth, companyId, customId, pageNo, pageSize);
        InvoiceStatisticDTO invoiceStatisticDTO = new InvoiceStatisticDTO();
        invoiceStatisticDTO.setStatistics(page.getRecords());
        invoiceStatisticDTO.setTotal((int) page.getTotal());
        return TavernResponse.ok(invoiceStatisticDTO);
    }

}

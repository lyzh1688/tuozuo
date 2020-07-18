package com.tuozuo.tavern.shuiruyi.endpoint;

import com.tuozuo.tavern.common.protocol.TavernRequestAuthFields;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.shuiruyi.dto.InvoiceStatisticDTO;
import com.tuozuo.tavern.shuiruyi.service.InvoiceInfoService;
import com.tuozuo.tavern.shuiruyi.vo.InvoiceStatisticVO;
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
    public TavernResponse queryInvoiceStatistic(@RequestBody InvoiceStatisticVO vo,
                                           @RequestHeader(TavernRequestAuthFields.USER_ID) String userId) {
        try {
            InvoiceStatisticDTO invoiceStatisticDTO = this.invoiceInfoService.queryInvoiceStatistics(vo, userId);
            return TavernResponse.ok(invoiceStatisticDTO);
        } catch (Exception e) {
            LOGGER.error("[开票统计] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 缴税明细
     */
    @GetMapping("/invoice/detail")
    public TavernResponse queryInvoiceDetail(@RequestBody InvoiceStatisticVO vo,
                                           @RequestHeader(TavernRequestAuthFields.USER_ID) String userId) {
        try {
            InvoiceStatisticDTO invoiceStatisticDTO = this.invoiceInfoService.queryInvoiceStatistics(vo, userId);
            return TavernResponse.ok(invoiceStatisticDTO);
        } catch (Exception e) {
            LOGGER.error("[缴税明细] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

}

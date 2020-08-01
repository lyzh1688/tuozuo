package com.tuozuo.tavern.shuiruyi.endpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.shuiruyi.dto.TaxStatisticDTO;
import com.tuozuo.tavern.shuiruyi.dto.TaxStatisticListDTO;
import com.tuozuo.tavern.shuiruyi.model.TaxStatistic;
import com.tuozuo.tavern.shuiruyi.service.InvoiceInfoService;
import com.tuozuo.tavern.shuiruyi.vo.TaxStatisticVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/31 <br>
 */
@RestController
@RequestMapping("/tuozuo/shuiruyi/v1/tax")
public class TaxInfoEndpoint {
    private final static Logger LOGGER = LoggerFactory.getLogger(TaxInfoEndpoint.class);

    @Autowired
    private InvoiceInfoService invoiceInfoService;
    /**
     * 纳税统计
     */
    @GetMapping("/statistic")
    public TavernResponse queryTaxStatistic(@ModelAttribute @Valid TaxStatisticVO vo) {
        try {
            IPage<TaxStatistic> page = this.invoiceInfoService.queryTaxStatistic(vo.getRegisterArea(),
                    vo.getCustomId(),
                    vo.getAreaLevel(),
                    vo.getAreaCode(),
                    vo.getInvoiceType(),
                    vo.getBeginDate(),
                    vo.getEndDate(),
                    vo.getPageNo(),
                    vo.getPageSize());
            List<TaxStatisticDTO> taxStatisticDTOList = page.getRecords()
                    .stream()
                    .map(taxStatistic -> {
                        TaxStatisticDTO taxStatisticDTO = new TaxStatisticDTO();
                        BeanUtils.copyProperties(taxStatistic,taxStatisticDTO);
                        return taxStatisticDTO;
                    }).collect(Collectors.toList());
            TaxStatistic totalTaxStatistic = this.invoiceInfoService.queryTotalTaxStatistic(vo.getRegisterArea(),
                    vo.getCustomId(),
                    vo.getAreaLevel(),
                    vo.getAreaCode(),
                    vo.getInvoiceType(),
                    vo.getBeginDate(),
                    vo.getEndDate());

            TaxStatisticListDTO taxStatisticDTO = new TaxStatisticListDTO();
            taxStatisticDTO.setStatistics(taxStatisticDTOList);
            taxStatisticDTO.setTotal((int) page.getTotal());
            if(totalTaxStatistic != null){
                taxStatisticDTO.setTotalIncomeTax(totalTaxStatistic.getTotalIncomeTax());
                taxStatisticDTO.setTotalValueAddedTax(totalTaxStatistic.getTotalValueAddedTax());
            }
            return TavernResponse.ok(taxStatisticDTO);
        } catch (Exception e) {
            LOGGER.error("[纳税统计] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }
}

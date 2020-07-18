package com.tuozuo.tavern.shuiruyi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.shuiruyi.dao.InvoiceInfoDao;
import com.tuozuo.tavern.shuiruyi.dto.InvoiceStatisticDTO;
import com.tuozuo.tavern.shuiruyi.model.InvoiceStatistic;
import com.tuozuo.tavern.shuiruyi.service.InvoiceInfoService;
import com.tuozuo.tavern.shuiruyi.vo.InvoiceStatisticVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
@Service
public class InvoiceInfoServiceImpl implements InvoiceInfoService {

    @Autowired
    private InvoiceInfoDao invoiceInfoDao;

    @Override
    public InvoiceStatisticDTO queryInvoiceStatistics(InvoiceStatisticVO vo, String customId) {
        IPage<InvoiceStatistic> page = this.invoiceInfoDao.selectStatistics(vo.getBeginMonth(),
                vo.getEndMonth(),
                vo.getCompanyId(),
                customId,
                vo.getPageNo(),
                vo.getPageSize());
        InvoiceStatisticDTO invoiceStatisticDTO = new InvoiceStatisticDTO();
        invoiceStatisticDTO.setStatistics(page.getRecords());
        invoiceStatisticDTO.setTotal((int) page.getTotal());
        return invoiceStatisticDTO;
    }
}

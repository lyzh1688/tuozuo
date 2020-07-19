package com.tuozuo.tavern.shuiruyi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.shuiruyi.dto.InvoiceStatisticDTO;
import com.tuozuo.tavern.shuiruyi.model.InvoiceStatistic;
import com.tuozuo.tavern.shuiruyi.vo.InvoiceStatisticVO;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
public interface InvoiceInfoService {

    IPage<InvoiceStatistic> queryInvoiceStatistics(InvoiceStatisticVO invoiceStatisticVO, String customId);

}

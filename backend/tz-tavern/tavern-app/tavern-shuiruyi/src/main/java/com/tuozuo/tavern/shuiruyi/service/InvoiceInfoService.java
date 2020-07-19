package com.tuozuo.tavern.shuiruyi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.shuiruyi.model.InvoiceStatistic;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
public interface InvoiceInfoService {

    IPage<InvoiceStatistic> queryInvoiceStatistics(String beginMonth, String endMonth, String companyId, String customId, int pageNo, int pageSize);

}

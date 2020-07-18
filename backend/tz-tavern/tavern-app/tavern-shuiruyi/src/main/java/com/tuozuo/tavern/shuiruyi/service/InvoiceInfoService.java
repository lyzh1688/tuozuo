package com.tuozuo.tavern.shuiruyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tuozuo.tavern.shuiruyi.dto.InvoiceStatisticDTO;
import com.tuozuo.tavern.shuiruyi.model.InvoiceInfo;
import com.tuozuo.tavern.shuiruyi.vo.InvoiceStatisticVO;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
public interface InvoiceInfoService extends IService<InvoiceInfo> {

    InvoiceStatisticDTO queryInvoiceStatistics(InvoiceStatisticVO invoiceStatisticVO);

}

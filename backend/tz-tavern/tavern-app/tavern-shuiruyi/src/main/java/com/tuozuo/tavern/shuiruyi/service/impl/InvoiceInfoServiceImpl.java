package com.tuozuo.tavern.shuiruyi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.shuiruyi.dao.InvoiceInfoDao;
import com.tuozuo.tavern.shuiruyi.model.InvoiceStatistic;
import com.tuozuo.tavern.shuiruyi.service.InvoiceInfoService;
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
    public IPage<InvoiceStatistic> queryInvoiceStatistics(String beginMonth, String endMonth, String companyId, String customId, int pageNo, int pageSize) {
        return this.invoiceInfoDao.selectStatistics(beginMonth,
                endMonth,
                companyId,
                customId,
                pageNo,
                pageSize);

    }
}
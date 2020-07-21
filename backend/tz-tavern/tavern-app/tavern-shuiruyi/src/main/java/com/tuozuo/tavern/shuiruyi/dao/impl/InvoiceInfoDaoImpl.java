package com.tuozuo.tavern.shuiruyi.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.shuiruyi.dao.InvoiceInfoDao;
import com.tuozuo.tavern.shuiruyi.mapper.InvoiceInfoMapper;
import com.tuozuo.tavern.shuiruyi.model.InvoiceStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
@Repository
public class InvoiceInfoDaoImpl implements InvoiceInfoDao {
    @Autowired
    private InvoiceInfoMapper invoiceInfoMapper;

    @Override
    public IPage<InvoiceStatistic> selectStatistics(String beginMonth, String endMonth, String companyId, String customId, int pageNo, int pageSize) {
        Page<InvoiceStatistic> page = new Page(pageNo, pageSize);
        return this.invoiceInfoMapper.selectStatistic(page, beginMonth, endMonth, companyId, customId);

    }
}
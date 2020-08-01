package com.tuozuo.tavern.shuiruyi.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.shuiruyi.model.InvoiceDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.InvoiceInfo;
import com.tuozuo.tavern.shuiruyi.model.InvoiceStatistic;
import com.tuozuo.tavern.shuiruyi.model.TaxStatistic;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
public interface InvoiceInfoDao {

    IPage<InvoiceStatistic> selectStatistics(String beginMonth, String endMonth, String companyId, String customId, int pageNo, int pageSize);

    IPage<InvoiceDetailInfo> selectInvoiceInfoList(String companyId,
                                                   String contractId,
                                                   String invoiceStatus,
                                                   int pageNo,
                                                   int pageSize,
                                                   String customId);

    void insert(InvoiceInfo invoiceInfo);

    void update(InvoiceInfo invoiceInfo);

    InvoiceDetailInfo selectInvoiceInfo(String invoiceId);

    IPage<TaxStatistic> selectTaxStatistic(String registerArea,
                                           String customId,
                                           String areaLevel,
                                           String areaCode,
                                           String invoiceType,
                                           String beginDate,
                                           String endDate,
                                           int pageNo,
                                           int pageSize);

    TaxStatistic selectTotalTaxStatistic(String registerArea,
                                         String customId,
                                         String areaLevel,
                                         String areaCode,
                                         String invoiceType,
                                         String beginDate,
                                         String endDate);

}

package com.tuozuo.tavern.shuiruyi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.shuiruyi.dto.TaxStatisticDTO;
import com.tuozuo.tavern.shuiruyi.model.InvoiceDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.InvoiceInfo;
import com.tuozuo.tavern.shuiruyi.model.InvoiceStatistic;
import com.tuozuo.tavern.shuiruyi.model.TaxStatistic;
import com.tuozuo.tavern.shuiruyi.vo.InvoiceAuditVO;
import com.tuozuo.tavern.shuiruyi.vo.InvoiceInfoVO;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
public interface InvoiceInfoService {

    IPage<InvoiceStatistic> queryInvoiceStatistics(String beginMonth, String endMonth, String companyId, String customId, int pageNo, int pageSize);

    IPage<InvoiceDetailInfo> queryInvoiceInfoList(String companyId,
                                                  String contractId,
                                                  String invoiceStatus,
                                                  int pageNo,
                                                  int pageSize,
                                                  String customId,
                                                  String customGroup);

    void createInvoice(InvoiceInfoVO invoiceInfoVO) throws Exception;

    InvoiceDetailInfo queryInvoiceInfo(String invoiceId);

    void modifyInvoiceInfo(InvoiceInfoVO vo) throws Exception;

    void auditInvoiceInfo(String invoiceId, String invoiceStatus, String deliveryId, String remark,String invoiceContent,double tax);

    IPage<TaxStatistic> queryTaxStatistic(String registerArea,
                                          String customId,
                                          String areaLevel,
                                          String areaCode,
                                          String invoiceType,
                                          String beginDate,
                                          String endDate,
                                          int pageNo,
                                          int pageSize);
    TaxStatistic queryTotalTaxStatistic(String registerArea,
                                          String customId,
                                          String areaLevel,
                                          String areaCode,
                                          String invoiceType,
                                          String beginDate,
                                          String endDate);
}

package com.tuozuo.tavern.shuiruyi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.shuiruyi.model.InvoiceInfo;
import com.tuozuo.tavern.shuiruyi.model.InvoiceStatistic;
import com.tuozuo.tavern.shuiruyi.vo.InvoiceAuditVO;
import com.tuozuo.tavern.shuiruyi.vo.InvoiceInfoVO;
import com.tuozuo.tavern.shuiruyi.vo.InvoiceModifyVO;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
public interface InvoiceInfoService {

    IPage<InvoiceStatistic> queryInvoiceStatistics(String beginMonth, String endMonth, String companyId, String customId, int pageNo, int pageSize);

    IPage<InvoiceInfo> queryInvoiceInfoList(String companyId,
                                            String contractId,
                                            String invoiceStatus,
                                            int pageNo,
                                            int pageSize);

    void createInvoice(InvoiceInfoVO invoiceInfoVO);

    InvoiceInfo queryInvoiceInfo(String invoiceId);

    void modifyInvoiceInfo(InvoiceModifyVO vo);

    void auditInvoiceInfo(InvoiceAuditVO vo);

}

package com.tuozuo.tavern.shuiruyi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.shuiruyi.dao.InvoiceInfoDao;
import com.tuozuo.tavern.shuiruyi.model.InvoiceInfo;
import com.tuozuo.tavern.shuiruyi.model.InvoiceStatistic;
import com.tuozuo.tavern.shuiruyi.service.InvoiceInfoService;
import com.tuozuo.tavern.shuiruyi.vo.InvoiceAuditVO;
import com.tuozuo.tavern.shuiruyi.vo.InvoiceInfoVO;
import com.tuozuo.tavern.shuiruyi.vo.InvoiceModifyVO;
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

    @Override
    public IPage<InvoiceInfo> queryInvoiceInfoList(String companyId, String contractId, String invoiceStatus, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public void createInvoice(InvoiceInfoVO invoiceInfoVO) {

    }

    @Override
    public InvoiceInfo queryInvoiceInfo(String invoiceId) {
        return null;
    }

    @Override
    public void modifyInvoiceInfo(InvoiceModifyVO vo) {

    }

    @Override
    public void auditInvoiceInfo(InvoiceAuditVO vo) {
        
    }
}

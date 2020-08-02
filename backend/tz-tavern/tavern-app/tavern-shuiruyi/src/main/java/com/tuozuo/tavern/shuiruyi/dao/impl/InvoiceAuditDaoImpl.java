package com.tuozuo.tavern.shuiruyi.dao.impl;

import com.tuozuo.tavern.shuiruyi.dao.InvoiceAuditDao;
import com.tuozuo.tavern.shuiruyi.mapper.InvoiceAuditFlowMapper;
import com.tuozuo.tavern.shuiruyi.model.InvoiceAuditFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/1 <br>
 */
@Repository
public class InvoiceAuditDaoImpl implements InvoiceAuditDao {
    @Autowired
    private InvoiceAuditFlowMapper invoiceAuditFlowMapper;

    @Override
    public void insert(InvoiceAuditFlow invoiceAuditFlow) {
        this.invoiceAuditFlowMapper.insert(invoiceAuditFlow);
    }
}

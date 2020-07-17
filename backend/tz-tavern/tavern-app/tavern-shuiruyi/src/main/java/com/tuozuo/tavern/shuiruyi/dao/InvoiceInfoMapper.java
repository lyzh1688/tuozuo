package com.tuozuo.tavern.shuiruyi.dao;

import com.tuozuo.tavern.shuiruyi.model.InvoiceInfo;
import java.util.List;

public interface InvoiceInfoMapper {
    int deleteByPrimaryKey(String invoiceId);

    int insert(InvoiceInfo record);

    InvoiceInfo selectByPrimaryKey(String invoiceId);

    List<InvoiceInfo> selectAll();

    int updateByPrimaryKey(InvoiceInfo record);
}
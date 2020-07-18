package com.tuozuo.tavern.shuiruyi.mapper;

import com.tuozuo.tavern.shuiruyi.model.InvoiceAuditFlow;
import java.util.List;

public interface InvoiceAuditFlowMapper {
    int deleteByPrimaryKey(String flowId);

    int insert(InvoiceAuditFlow record);

    InvoiceAuditFlow selectByPrimaryKey(String flowId);

    List<InvoiceAuditFlow> selectAll();

    int updateByPrimaryKey(InvoiceAuditFlow record);
}
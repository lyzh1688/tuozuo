package com.tuozuo.tavern.shuiruyi.mapper;

import com.tuozuo.tavern.shuiruyi.model.InvoiceAuditFlow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface InvoiceAuditFlowMapper {
    int deleteByPrimaryKey(String flowId);

    int insert(InvoiceAuditFlow record);

    InvoiceAuditFlow selectByPrimaryKey(String flowId);

    List<InvoiceAuditFlow> selectAll();

    int updateByPrimaryKey(InvoiceAuditFlow record);
}
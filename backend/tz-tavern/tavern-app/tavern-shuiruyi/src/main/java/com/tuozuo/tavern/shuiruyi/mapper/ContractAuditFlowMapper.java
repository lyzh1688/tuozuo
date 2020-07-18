package com.tuozuo.tavern.shuiruyi.mapper;

import com.tuozuo.tavern.shuiruyi.model.ContractAuditFlow;
import java.util.List;

public interface ContractAuditFlowMapper {
    int deleteByPrimaryKey(String flowId);

    int insert(ContractAuditFlow record);

    ContractAuditFlow selectByPrimaryKey(String flowId);

    List<ContractAuditFlow> selectAll();

    int updateByPrimaryKey(ContractAuditFlow record);
}
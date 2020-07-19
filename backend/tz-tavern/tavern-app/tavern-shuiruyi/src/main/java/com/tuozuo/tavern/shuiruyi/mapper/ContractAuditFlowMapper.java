package com.tuozuo.tavern.shuiruyi.mapper;

import com.tuozuo.tavern.shuiruyi.model.ContractAuditFlow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ContractAuditFlowMapper {
    int deleteByPrimaryKey(String flowId);

    int insert(ContractAuditFlow record);

    ContractAuditFlow selectByPrimaryKey(String flowId);

    List<ContractAuditFlow> selectAll();

    int updateByPrimaryKey(ContractAuditFlow record);
}
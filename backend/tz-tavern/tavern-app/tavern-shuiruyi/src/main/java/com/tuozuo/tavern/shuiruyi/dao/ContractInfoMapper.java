package com.tuozuo.tavern.shuiruyi.dao;

import com.tuozuo.tavern.shuiruyi.model.ContractInfo;
import java.util.List;

public interface ContractInfoMapper {
    int deleteByPrimaryKey(String contractId);

    int insert(ContractInfo record);

    ContractInfo selectByPrimaryKey(String contractId);

    List<ContractInfo> selectAll();

    int updateByPrimaryKey(ContractInfo record);
}
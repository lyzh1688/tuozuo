package com.tuozuo.tavern.shuiruyi.mapper;

import com.tuozuo.tavern.shuiruyi.model.ContractInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ContractInfoMapper {
    int deleteByPrimaryKey(String contractId);

    int insert(ContractInfo record);

    ContractInfo selectByPrimaryKey(String contractId);

    List<ContractInfo> selectAll();

    int updateByPrimaryKey(ContractInfo record);
}
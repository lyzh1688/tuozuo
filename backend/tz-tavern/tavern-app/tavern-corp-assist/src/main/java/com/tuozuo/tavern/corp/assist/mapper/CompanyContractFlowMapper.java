package com.tuozuo.tavern.corp.assist.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.corp.assist.model.CompanyContractFlow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CompanyContractFlowMapper extends BaseMapper<CompanyContractFlow> {
    int deleteByPrimaryKey(String flowId);

    int insert(CompanyContractFlow record);

    CompanyContractFlow selectByPrimaryKey(String flowId);

    List<CompanyContractFlow> selectAll();

    int updateByPrimaryKey(CompanyContractFlow record);
}
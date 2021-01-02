package com.tuozuo.tavern.corp.assist.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.corp.assist.model.CorporationContractFlow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CorporationContractFlowMapper extends BaseMapper<CorporationContractFlow> {
    int deleteByPrimaryKey(String flowId);

    int insert(CorporationContractFlow record);

    CorporationContractFlow selectByPrimaryKey(String flowId);

    List<CorporationContractFlow> selectAll();

    int updateByPrimaryKey(CorporationContractFlow record);
}
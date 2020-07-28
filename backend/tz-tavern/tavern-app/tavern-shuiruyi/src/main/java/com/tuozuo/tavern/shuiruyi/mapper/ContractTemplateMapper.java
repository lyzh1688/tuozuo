package com.tuozuo.tavern.shuiruyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.shuiruyi.model.ContractTemplate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContractTemplateMapper extends BaseMapper<ContractTemplate> {
    List<ContractTemplate> selectAll();


}
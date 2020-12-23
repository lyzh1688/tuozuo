package com.tuozuo.tavern.corp.assist.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.corp.assist.model.CompanyContractTemplate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CompanyContractTemplateMapper extends BaseMapper<CompanyContractTemplate> {
    int deleteByPrimaryKey(String templateId);

    int insert(CompanyContractTemplate record);

    CompanyContractTemplate selectByPrimaryKey(String templateId);

    List<CompanyContractTemplate> selectAll();

    int updateByPrimaryKey(CompanyContractTemplate record);
}
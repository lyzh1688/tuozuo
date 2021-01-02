package com.tuozuo.tavern.corp.assist.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.corp.assist.model.CorporationContractTemplate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CorporationContractTemplateMapper extends BaseMapper<CorporationContractTemplate> {
    int deleteByPrimaryKey(String templateId);

    int insert(CorporationContractTemplate record);

    CorporationContractTemplate selectByPrimaryKey(String templateId);

    List<CorporationContractTemplate> selectAll();

    int updateByPrimaryKey(CorporationContractTemplate record);
}
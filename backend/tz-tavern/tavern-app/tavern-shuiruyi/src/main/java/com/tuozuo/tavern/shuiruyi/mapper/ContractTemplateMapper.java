package com.tuozuo.tavern.shuiruyi.mapper;

import com.tuozuo.tavern.shuiruyi.model.ContractTemplate;
import java.util.List;

public interface ContractTemplateMapper {
    int deleteByPrimaryKey(String contractTemplteId);

    int insert(ContractTemplate record);

    ContractTemplate selectByPrimaryKey(String contractTemplteId);

    List<ContractTemplate> selectAll();

    int updateByPrimaryKey(ContractTemplate record);
}
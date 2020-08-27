package com.tuozuo.tavern.xinruyi.mapper;

import com.tuozuo.tavern.xinruyi.model.CompanyInfoExt;
import java.util.List;

public interface CompanyInfoExtMapper {
    int deleteByPrimaryKey(String companyId);

    int insert(CompanyInfoExt record);

    CompanyInfoExt selectByPrimaryKey(String companyId);

    List<CompanyInfoExt> selectAll();

    int updateByPrimaryKey(CompanyInfoExt record);
}
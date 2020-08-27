package com.tuozuo.tavern.xinruyi.mapper;

import com.tuozuo.tavern.xinruyi.model.CompanyInfo;
import java.util.List;

public interface CompanyInfoMapper {
    int deleteByPrimaryKey(String registerId);

    int insert(CompanyInfo record);

    CompanyInfo selectByPrimaryKey(String registerId);

    List<CompanyInfo> selectAll();

    int updateByPrimaryKey(CompanyInfo record);
}
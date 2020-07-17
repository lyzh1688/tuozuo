package com.tuozuo.tavern.shuiruyi.dao;

import com.tuozuo.tavern.shuiruyi.model.BusinessDict;

import java.util.List;

public interface BusinessDictMapper {
    int deleteByPrimaryKey(String businessId);

    int insert(BusinessDict record);

    BusinessDict selectByPrimaryKey(String businessId);

    List<BusinessDict> selectAll();

    int updateByPrimaryKey(BusinessDict record);
}
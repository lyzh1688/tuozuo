package com.tuozuo.tavern.xinruyi.mapper;

import com.tuozuo.tavern.xinruyi.model.StaffResourcePool;
import java.util.List;

public interface StaffResourcePoolMapper {
    int deleteByPrimaryKey(String companyId);

    int insert(StaffResourcePool record);

    StaffResourcePool selectByPrimaryKey(String companyId);

    List<StaffResourcePool> selectAll();

    int updateByPrimaryKey(StaffResourcePool record);
}
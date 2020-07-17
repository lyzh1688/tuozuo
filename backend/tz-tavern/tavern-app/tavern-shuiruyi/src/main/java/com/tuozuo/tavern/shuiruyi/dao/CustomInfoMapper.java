package com.tuozuo.tavern.shuiruyi.dao;

import com.tuozuo.tavern.shuiruyi.model.CustomInfo;
import java.util.List;

public interface CustomInfoMapper {
    int deleteByPrimaryKey(String customId);

    int insert(CustomInfo record);

    CustomInfo selectByPrimaryKey(String customId);

    List<CustomInfo> selectAll();

    int updateByPrimaryKey(CustomInfo record);
}
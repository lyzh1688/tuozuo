package com.tuozuo.tavern.xinruyi.mapper;

import com.tuozuo.tavern.xinruyi.model.WorkerInfo;
import java.util.List;

public interface WorkerInfoMapper {
    int deleteByPrimaryKey(String registerId);

    int insert(WorkerInfo record);

    WorkerInfo selectByPrimaryKey(String registerId);

    List<WorkerInfo> selectAll();

    int updateByPrimaryKey(WorkerInfo record);
}
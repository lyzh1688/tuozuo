package com.tuozuo.tavern.xinruyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.xinruyi.model.WorkerInfo;
import com.tuozuo.tavern.xinruyi.model.WorkerSummaryInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface WorkerInfoMapper extends BaseMapper<WorkerInfo> {

    WorkerSummaryInfo selectWorkerSumInfo(@Param("registerId")String registerId);
}
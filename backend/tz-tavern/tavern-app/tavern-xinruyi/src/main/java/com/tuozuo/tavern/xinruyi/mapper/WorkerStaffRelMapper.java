package com.tuozuo.tavern.xinruyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.xinruyi.model.WorkerStaffRel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WorkerStaffRelMapper extends BaseMapper<WorkerStaffRel> {
    List<WorkerStaffRel> selectStaffProject(@Param("registerId") String registerId);
}
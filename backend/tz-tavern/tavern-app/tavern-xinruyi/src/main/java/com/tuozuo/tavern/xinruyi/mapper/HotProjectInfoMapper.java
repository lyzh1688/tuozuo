package com.tuozuo.tavern.xinruyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.xinruyi.model.HotProjectInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface HotProjectInfoMapper extends BaseMapper<HotProjectInfo> {

    List<HotProjectInfo> selectAll();
}
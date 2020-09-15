package com.tuozuo.tavern.xinruyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.xinruyi.model.AreaInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AreaInfoMapper extends BaseMapper<AreaInfo> {

    List<AreaInfo> selectAreaList(@Param("areaCode") String areaCode, @Param("areaLevel") String areaLevel);


}
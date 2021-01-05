package com.tuozuo.tavern.corp.assist.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.corp.assist.model.CorporationInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CorporationInfoMapper extends BaseMapper<CorporationInfo> {
    int insert(CorporationInfo record);

    List<CorporationInfo> selectAll();
}
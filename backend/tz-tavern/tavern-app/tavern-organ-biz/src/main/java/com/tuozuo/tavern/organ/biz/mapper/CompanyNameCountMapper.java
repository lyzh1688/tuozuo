package com.tuozuo.tavern.organ.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.organ.biz.model.CompanyNameCount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CompanyNameCountMapper extends BaseMapper<CompanyNameCount> {
    int insert(CompanyNameCount record);

    List<CompanyNameCount> selectAll();
}
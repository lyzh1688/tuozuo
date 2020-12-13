package com.tuozuo.tavern.organ.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.organ.biz.model.CompanyNameArea;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CompanyNameAreaMapper extends BaseMapper<CompanyNameArea> {
    int insert(CompanyNameArea record);

    List<CompanyNameArea> selectAll();
}
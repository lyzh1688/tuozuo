package com.tuozuo.tavern.organ.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.organ.biz.model.CompanyNameProperty;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CompanyNamePropertyMapper  extends BaseMapper<CompanyNameProperty> {
    int insert(CompanyNameProperty record);

    List<CompanyNameProperty> selectAll();
}
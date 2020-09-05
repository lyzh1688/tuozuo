package com.tuozuo.tavern.xinruyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.xinruyi.model.CompanyInfoExt;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CompanyInfoExtMapper extends BaseMapper<CompanyInfoExt> {
    int deleteByPrimaryKey(String companyId);

    int insert(CompanyInfoExt record);

    CompanyInfoExt selectByPrimaryKey(String companyId);

    List<CompanyInfoExt> selectAll();

    int updateByPrimaryKey(CompanyInfoExt record);
}
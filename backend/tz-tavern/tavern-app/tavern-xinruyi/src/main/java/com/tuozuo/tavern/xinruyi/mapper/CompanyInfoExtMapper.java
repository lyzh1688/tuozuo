package com.tuozuo.tavern.xinruyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.xinruyi.model.CompanyInfoExt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CompanyInfoExtMapper extends BaseMapper<CompanyInfoExt> {
    void insertOrUpdate(CompanyInfoExt companyInfoExt);

    CompanyInfoExt select(@Param("companyId") String companyId);
}
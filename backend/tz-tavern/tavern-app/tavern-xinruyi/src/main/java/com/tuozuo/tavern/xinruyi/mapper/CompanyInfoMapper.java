package com.tuozuo.tavern.xinruyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.xinruyi.model.CompanyInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CompanyInfoMapper extends BaseMapper<CompanyInfo> {

    CompanyInfo select(@Param("companyId")String companyId);

}
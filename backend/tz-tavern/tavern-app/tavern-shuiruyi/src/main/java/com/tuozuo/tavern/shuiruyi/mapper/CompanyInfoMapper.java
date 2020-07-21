package com.tuozuo.tavern.shuiruyi.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.shuiruyi.model.CompanyDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.CompanyInfo;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/08/10 <br>
 */
@Mapper
public interface CompanyInfoMapper extends BaseMapper<CompanyInfo> {

    CompanyDetailInfo selectDetailInfo(@Param("companyId") String companyId);

    IPage<CompanyInfo> selectCompanyList(Page page,
            @Param("customId") String customId,
            @Param("companyStatus") String companyStatus,
            @Param("registerStatus") String registerStatus);
}
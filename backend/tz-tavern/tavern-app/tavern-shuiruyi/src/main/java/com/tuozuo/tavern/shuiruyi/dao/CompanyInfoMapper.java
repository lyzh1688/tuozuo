package com.tuozuo.tavern.shuiruyi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuozuo.tavern.shuiruyi.model.CompanyInfo;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/08/10 <br>
 */
public interface CompanyInfoMapper extends BaseMapper<CompanyInfo> {
       CompanyInfo selectCompanyDetailInfo();
}

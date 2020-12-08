package com.tuozuo.tavern.organ.biz.dao;

import com.tuozuo.tavern.organ.biz.model.CompanyNameCount;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/12/8 <br>
 */
public interface CompanyNameCountDao {

    CompanyNameCount queryCompanyNameCount(String pinyin);

    void updateCompanyNameCount(CompanyNameCount companyNameCount);

}

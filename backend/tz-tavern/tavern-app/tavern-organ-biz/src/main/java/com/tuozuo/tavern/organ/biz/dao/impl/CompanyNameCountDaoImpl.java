package com.tuozuo.tavern.organ.biz.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.organ.biz.dao.CompanyNameCountDao;
import com.tuozuo.tavern.organ.biz.mapper.CompanyNameCountMapper;
import com.tuozuo.tavern.organ.biz.model.CompanyNameCount;
import org.springframework.stereotype.Repository;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/12/8 <br>
 */
@Repository
public class CompanyNameCountDaoImpl extends ServiceImpl<CompanyNameCountMapper, CompanyNameCount> implements CompanyNameCountDao {

    @Override
    public CompanyNameCount queryCompanyNameCount(String pinyin) {
        return this.getOne(Wrappers.<CompanyNameCount>query()
                .lambda()
                .eq(CompanyNameCount::getPinyin, pinyin));
    }

    @Override
    public void updateCompanyNameCount(CompanyNameCount companyNameCount) {
        this.saveOrUpdate(companyNameCount);
    }
}

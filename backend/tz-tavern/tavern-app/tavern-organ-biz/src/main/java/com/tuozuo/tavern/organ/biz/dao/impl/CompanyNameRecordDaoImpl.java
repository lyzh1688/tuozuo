package com.tuozuo.tavern.organ.biz.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.organ.biz.dao.CompanyNameRecordDao;
import com.tuozuo.tavern.organ.biz.mapper.CompanyNameRecordMapper;
import com.tuozuo.tavern.organ.biz.model.CompanyNameRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/24 <br>
 */
@Repository
public class CompanyNameRecordDaoImpl extends ServiceImpl<CompanyNameRecordMapper, CompanyNameRecord> implements CompanyNameRecordDao {

    @Autowired
    private CompanyNameRecordMapper companyNameRecordMapper;

    @Override
    public List<CompanyNameRecord> queryCompanyRecords(String pinyin) {
        return this.list(Wrappers.<CompanyNameRecord>query()
                .lambda()
                .eq(CompanyNameRecord::getPinyin, pinyin));
    }

    @Override
    public void mergeCompanyRecords(List<CompanyNameRecord> companyNameRecordList) {
        this.companyNameRecordMapper.mergeBatch(companyNameRecordList);
    }
}

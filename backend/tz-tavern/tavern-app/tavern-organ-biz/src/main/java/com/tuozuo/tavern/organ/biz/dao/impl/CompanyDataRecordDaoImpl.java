package com.tuozuo.tavern.organ.biz.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.organ.biz.dao.CompanyDataRecordDao;
import com.tuozuo.tavern.organ.biz.mapper.CompanyDataRecordMapper;
import com.tuozuo.tavern.organ.biz.model.CompanyDataRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/24 <br>
 */
@Repository
public class CompanyDataRecordDaoImpl extends ServiceImpl<CompanyDataRecordMapper, CompanyDataRecord> implements CompanyDataRecordDao {

    @Autowired
    private CompanyDataRecordMapper companyDataRecordMapper;

    @Override
    public List<CompanyDataRecord> queryCompanyRecords(String pinyin) {
        return this.list(Wrappers.<CompanyDataRecord>query()
                .lambda()
                .eq(CompanyDataRecord::getPinyin, pinyin));
    }
}

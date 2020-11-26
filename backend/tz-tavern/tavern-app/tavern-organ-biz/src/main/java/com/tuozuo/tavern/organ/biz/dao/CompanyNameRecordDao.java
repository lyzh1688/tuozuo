package com.tuozuo.tavern.organ.biz.dao;

import com.tuozuo.tavern.organ.biz.model.CompanyNameRecord;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/24 <br>
 */
public interface CompanyNameRecordDao {

    List<CompanyNameRecord> queryCompanyRecords(String pinyin);

    void mergeCompanyRecords(List<CompanyNameRecord> companyNameRecordList);

}

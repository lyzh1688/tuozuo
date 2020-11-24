package com.tuozuo.tavern.organ.biz.dao;

import com.tuozuo.tavern.organ.biz.model.CompanyDataRecord;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/24 <br>
 */
public interface CompanyDataRecordDao {

    List<CompanyDataRecord> queryCompanyRecords(String pinyin);

}

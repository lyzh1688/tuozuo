package com.tuozuo.tavern.organ.biz.dao;

import com.tuozuo.tavern.organ.biz.model.CompanyNameArea;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/12/13 <br>
 */
public interface CompanyNameAreaDao {

    void insertBatch(List<CompanyNameArea> companyNameAreas);

    List<CompanyNameArea> selectAll();

}

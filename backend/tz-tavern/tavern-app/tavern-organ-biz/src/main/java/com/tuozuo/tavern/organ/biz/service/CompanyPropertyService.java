package com.tuozuo.tavern.organ.biz.service;

import com.tuozuo.tavern.organ.biz.dict.CompanyPropertyType;
import com.tuozuo.tavern.organ.biz.model.CompanyNameProperty;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/26 <br>
 */
public interface CompanyPropertyService {


    List<CompanyNameProperty> queryCompanyProperty(CompanyPropertyType type);

    String queryIndustrySuperClass(String subClass);

}

package com.tuozuo.tavern.organ.biz.dao;

import com.tuozuo.tavern.organ.biz.model.CompanyNameProperty;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/26 <br>
 */
public interface CompanyPropertyDao {

    List<CompanyNameProperty> selectByProperty(String type);

}

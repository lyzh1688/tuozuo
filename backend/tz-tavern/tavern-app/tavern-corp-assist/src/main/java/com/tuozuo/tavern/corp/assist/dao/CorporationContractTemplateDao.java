package com.tuozuo.tavern.corp.assist.dao;

import com.tuozuo.tavern.corp.assist.model.CorporationContractTemplate;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/12/24 <br>
 */
public interface CorporationContractTemplateDao {

    List<CorporationContractTemplate> selectAll();

    CorporationContractTemplate selectById(String templateId);

}

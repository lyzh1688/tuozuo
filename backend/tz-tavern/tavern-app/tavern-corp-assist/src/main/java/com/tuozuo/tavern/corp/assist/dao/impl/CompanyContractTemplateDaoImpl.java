package com.tuozuo.tavern.corp.assist.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.corp.assist.dao.CorporationContractTemplateDao;
import com.tuozuo.tavern.corp.assist.mapper.CorporationContractTemplateMapper;
import com.tuozuo.tavern.corp.assist.model.CorporationContractTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/12/24 <br>
 */
@Repository
public class CompanyContractTemplateDaoImpl extends ServiceImpl<CorporationContractTemplateMapper, CorporationContractTemplate> implements CorporationContractTemplateDao {

    @Override
    public List<CorporationContractTemplate> selectAll() {
        return this.list();
    }

    @Override
    public CorporationContractTemplate selectById(String templateId) {
        return this.getById(templateId);
    }
}

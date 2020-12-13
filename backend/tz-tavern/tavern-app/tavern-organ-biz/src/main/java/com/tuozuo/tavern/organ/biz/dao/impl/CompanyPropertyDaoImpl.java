package com.tuozuo.tavern.organ.biz.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.organ.biz.dao.CompanyPropertyDao;
import com.tuozuo.tavern.organ.biz.mapper.CompanyNamePropertyMapper;
import com.tuozuo.tavern.organ.biz.model.CompanyNameProperty;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/26 <br>
 */
@Repository
public class CompanyPropertyDaoImpl extends ServiceImpl<CompanyNamePropertyMapper, CompanyNameProperty> implements CompanyPropertyDao {

    @Override
    public List<CompanyNameProperty> selectByProperty(String type) {
        return this.list(Wrappers.<CompanyNameProperty>query()
                .lambda()
                .eq(CompanyNameProperty::getType, type));
    }

    @Cacheable(value = "company_industry")
    @Override
    public Map<String, String> selectIndustryAll(String type) {
        return this.list(Wrappers.<CompanyNameProperty>query()
                .lambda()
                .eq(CompanyNameProperty::getType, type)).parallelStream().collect(Collectors.toMap(k -> k.getSubClass(), v -> v.getSuperClass()));
    }
}

package com.tuozuo.tavern.organ.biz.service.impl;

import com.tuozuo.tavern.organ.biz.dao.CompanyNameAreaDao;
import com.tuozuo.tavern.organ.biz.dao.CompanyPropertyDao;
import com.tuozuo.tavern.organ.biz.dict.CompanyPropertyType;
import com.tuozuo.tavern.organ.biz.model.CompanyNameArea;
import com.tuozuo.tavern.organ.biz.model.CompanyNameProperty;
import com.tuozuo.tavern.organ.biz.service.CompanyPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/26 <br>
 */
@Service
public class CompanyPropertyServiceImpl implements CompanyPropertyService {

    @Autowired
    private CompanyPropertyDao companyPropertyDao;
    @Autowired
    private CompanyNameAreaDao companyNameAreaDao;

    @Override
    public List<CompanyNameProperty> queryCompanyProperty(CompanyPropertyType type) {
        if (type.equals(CompanyPropertyType.area)) {
            List<CompanyNameProperty> companyNamePropertyList = this.companyNameAreaDao.selectAll()
                    .parallelStream()
                    .map(area -> {
                        CompanyNameProperty companyNameProperty = new CompanyNameProperty();
                        companyNameProperty.setSuperClass(area.getProvinceName());
                        companyNameProperty.setSubClass(area.getCityName());
                        companyNameProperty.setType(CompanyPropertyType.area.name());
                        return companyNameProperty;
                    }).collect(Collectors.toList());
            return companyNamePropertyList;
        }
        return this.companyPropertyDao.selectByProperty(type.name());
    }

    @Override
    public String queryIndustrySuperClass(String subClass) {
        Map<String, String> industryMap = this.companyPropertyDao.selectIndustryAll(CompanyPropertyType.industry.name());
        return industryMap.getOrDefault(subClass, subClass);
    }
}

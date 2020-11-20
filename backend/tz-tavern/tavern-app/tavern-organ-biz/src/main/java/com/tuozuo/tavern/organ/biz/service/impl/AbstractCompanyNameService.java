package com.tuozuo.tavern.organ.biz.service.impl;

import com.tuozuo.tavern.organ.biz.exeception.ExecuteException;
import com.tuozuo.tavern.organ.biz.model.CompanyName;
import com.tuozuo.tavern.organ.biz.service.CompanyNameService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/20 <br>
 */
public abstract class AbstractCompanyNameService implements CompanyNameService {

    private static final int pageSize = 20;

    abstract List<CompanyName> getCompanyName(String area, String industry, String source, String preferWord, String isTwoWords,String type) throws ExecuteException;


    @Override
    public List<CompanyName> queryCompanyName(String area, String industry, String source, String preferWord, String isTwoWords,String type, int pageNo) throws ExecuteException {
        List<CompanyName> companyNameList = getCompanyName(area, industry, source, preferWord, isTwoWords,type);
        List<CompanyName> pageDataList = companyNameList.stream().skip((pageNo - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
        return pageDataList;
    }
}

package com.tuozuo.tavern.organ.biz.service.impl;

import com.tuozuo.tavern.organ.biz.model.CompanyNameRecord;
import com.tuozuo.tavern.organ.biz.model.CompanyVerifyResult;
import com.tuozuo.tavern.organ.biz.model.RecordItem;
import com.tuozuo.tavern.organ.biz.model.UserCompanyName;
import com.tuozuo.tavern.organ.biz.service.CompanyNameService;
import com.tuozuo.tavern.organ.biz.util.PinyinProcUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/23 <br>
 */
public abstract class CompanyNameTemplate implements CompanyNameService {

    public abstract List<String> splitName(String name);

    public abstract List<String> transferPinyin(List<String> name);

    public abstract List<CompanyNameRecord> getCompanyName(List<String> pinyinList);

    public abstract List<RecordItem> processCompanyName(List<CompanyNameRecord> companyNameList);

    @Override
    public CompanyVerifyResult queryCompanyResult(String area, String name, String industryDesc) {
        //1、二个字倒序，二个字以上拆词

        //2、拼音转换

        //3、查询数据库或调用接口

        //4、


        return null;
    }

    private UserCompanyName createUserCompanyName(String area, String name, String industryDesc){
        UserCompanyName userCompanyName = new UserCompanyName();
        userCompanyName.setArea(area);
        userCompanyName.setIndustryDesc(industryDesc);
        userCompanyName.setName(name);
        List<String> pinyin = Arrays.asList(PinyinProcUtils.getPinyin(name,","),",");
        userCompanyName.setNamePinYinList(pinyin);
        return userCompanyName;
    }
}

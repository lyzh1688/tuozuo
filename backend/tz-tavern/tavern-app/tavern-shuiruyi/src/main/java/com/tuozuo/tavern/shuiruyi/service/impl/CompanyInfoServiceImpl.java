package com.tuozuo.tavern.shuiruyi.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tuozuo.tavern.shuiruyi.model.CompanyDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.CompanyInfo;
import com.tuozuo.tavern.shuiruyi.service.CompanyInfoService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/08/10 <br>
 */
@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {

    /*@Override
    public List<CompanyInfo> fuzzyQueryCompany(String companyName, int queryCnt, boolean showAll) {
        //1、管理员

        //2、用户：需要携带用户ID




        return null;
       *//* if (showAll) {
            return this.list(Wrappers.<CompanyInfo>query()
                    .lambda()
                    .like(CompanyInfo::getCompanyName, companyName)
                    .orderByAsc(CompanyInfo::getCompanyName));
        } else {

            return this.list(Wrappers.<CompanyInfo>query()
                    .lambda()
                    .like(CompanyInfo::getCompanyName, companyName)
                    .orderByAsc(CompanyInfo::getCompanyName)
                    .last("limit " + queryCnt));
        }*//*

    }
*/
    @Override
    public List<CompanyInfo> fuzzyQueryCompany(String companyName, int queryCnt, boolean isAll, String userId, String roleGroup) {
        if()


        return null;
    }

    @Override
    public CompanyDetailInfo queryCompanyDetail(String companyId) {
        return this.baseMapper.selectDetailInfo(companyId);
    }
}

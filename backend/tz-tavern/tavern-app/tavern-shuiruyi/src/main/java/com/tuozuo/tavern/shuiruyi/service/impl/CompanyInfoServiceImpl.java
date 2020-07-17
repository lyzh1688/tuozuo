package com.tuozuo.tavern.shuiruyi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tavern.shuiruyi.model.CompanyInfo;
import com.tuozuo.tavern.shuiruyi.dao.CompanyInfoDao;
import com.tuozuo.tavern.shuiruyi.service.CompanyInfoService;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/08/10 <br>
 */
@Service
public class CompanyInfoServiceImpl extends ServiceImpl<CompanyInfoDao, CompanyInfo> implements CompanyInfoService {

    @Override
    public List<CompanyInfo> fuzzyQueryCompany(String companyName, int queryCnt, boolean showAll) {
        if (showAll) {
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
        }

    }

    @Override
    public CompanyInfo queryCompanyDetail(String companyId) {
        return this.getById(companyId);
    }
}

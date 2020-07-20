package com.tuozuo.tavern.shuiruyi.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.shuiruyi.model.CompanyDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.CompanyInfo;

import java.util.List;
import java.util.Optional;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
public interface CompanyInfoDao {

    List<CompanyInfo> selectCompanyList(String companyName, int queryCnt);

    List<CompanyInfo> selectAllCompanyList(String companyName);

    List<CompanyInfo> selectAllCustomCompanyList(String companyName, String customId);

    List<CompanyInfo> selectCustomCompanyList(String companyName, int queryCnt, String customId);

    Optional<CompanyDetailInfo> selectDetailInfo(String companyId);

    IPage<CompanyInfo> selectCompanyList(String customId,String companyStatus,String registerStatus,int pageNo,int pageSize);
}

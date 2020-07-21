package com.tuozuo.tavern.shuiruyi.vo;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/08/10 <br>
 */
public class CompanyDetailVO {

    private CompanyInfoVO companyInfo;
    private BossInfoVO bossInfo;
    private CfoInfoVO cfoInfo;

    public CompanyInfoVO getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfoVO companyInfo) {
        this.companyInfo = companyInfo;
    }

    public BossInfoVO getBossInfo() {
        return bossInfo;
    }

    public void setBossInfo(BossInfoVO bossInfo) {
        this.bossInfo = bossInfo;
    }

    public CfoInfoVO getCfoInfo() {
        return cfoInfo;
    }

    public void setCfoInfo(CfoInfoVO cfoInfo) {
        this.cfoInfo = cfoInfo;
    }
}

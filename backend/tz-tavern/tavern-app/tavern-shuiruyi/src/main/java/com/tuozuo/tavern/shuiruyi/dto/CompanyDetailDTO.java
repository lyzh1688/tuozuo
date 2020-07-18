package com.tuozuo.tavern.shuiruyi.dto;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/08/10 <br>
 */
public class CompanyDetailDTO {

    private CompanyInfoDTO companyInfo;

    private BossInfoDTO bossInfo;

    private CfoInfoDTO cfoInfo;

    public CompanyInfoDTO getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfoDTO companyInfo) {
        this.companyInfo = companyInfo;
    }

    public BossInfoDTO getBossInfo() {
        return bossInfo;
    }

    public void setBossInfo(BossInfoDTO bossInfo) {
        this.bossInfo = bossInfo;
    }

    public CfoInfoDTO getCfoInfo() {
        return cfoInfo;
    }

    public void setCfoInfo(CfoInfoDTO cfoInfo) {
        this.cfoInfo = cfoInfo;
    }
}

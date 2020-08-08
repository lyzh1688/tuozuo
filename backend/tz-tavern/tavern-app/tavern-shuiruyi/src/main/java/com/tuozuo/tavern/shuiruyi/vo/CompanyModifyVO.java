package com.tuozuo.tavern.shuiruyi.vo;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/21 <br>
 */
public class CompanyModifyVO {

    private String companyId;
    private int freeDeliveryCnt;
    private String companyStatus;
    private String includeCancel;
    private String beginDate;
    private String endDate;
    private String tradeFlow;
    private String registerArea;
    private double valueAddedRebateRate;
    private double incomeRebateRate;


    public double getValueAddedRebateRate() {
        return valueAddedRebateRate;
    }

    public void setValueAddedRebateRate(double valueAddedRebateRate) {
        this.valueAddedRebateRate = valueAddedRebateRate;
    }

    public double getIncomeRebateRate() {
        return incomeRebateRate;
    }

    public void setIncomeRebateRate(double incomeRebateRate) {
        this.incomeRebateRate = incomeRebateRate;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public int getFreeDeliveryCnt() {
        return freeDeliveryCnt;
    }

    public void setFreeDeliveryCnt(int freeDeliveryCnt) {
        this.freeDeliveryCnt = freeDeliveryCnt;
    }

    public String getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(String companyStatus) {
        this.companyStatus = companyStatus;
    }

    public String getIncludeCancel() {
        return includeCancel;
    }

    public void setIncludeCancel(String includeCancel) {
        this.includeCancel = includeCancel;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTradeFlow() {
        return tradeFlow;
    }

    public void setTradeFlow(String tradeFlow) {
        this.tradeFlow = tradeFlow;
    }

    public String getRegisterArea() {
        return registerArea;
    }

    public void setRegisterArea(String registerArea) {
        this.registerArea = registerArea;
    }

}

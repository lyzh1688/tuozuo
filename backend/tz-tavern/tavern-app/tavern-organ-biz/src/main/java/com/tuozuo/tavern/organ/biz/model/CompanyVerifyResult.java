package com.tuozuo.tavern.organ.biz.model;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/23 <br>
 */
public class CompanyVerifyResult {

    private static final BigDecimal DEF_SCORES = new BigDecimal(80);

    private BigDecimal scores;
    private List<String> pinyinDupRecords = Lists.newArrayList();
    private List<String> wordDupRecords = Lists.newArrayList();
    private List<String> pinYinPosDupRecords = Lists.newArrayList();
    private List<String> wordPosRecords = Lists.newArrayList();
    private List<String> industryDescRecords = Lists.newArrayList();

    public CompanyVerifyResult() {
    }

    public CompanyVerifyResult(BigDecimal scores) {
        this.scores = scores;
    }

    public static CompanyVerifyResult defCompanyNameRecord() {
        return new CompanyVerifyResult(DEF_SCORES);
    }


    public BigDecimal getScores() {
        return scores;
    }

    public void setScores(BigDecimal scores) {
        this.scores = scores;
    }

    public List<String> getPinyinDupRecords() {
        return pinyinDupRecords;
    }

    public void setPinyinDupRecords(List<String> pinyinDupRecords) {
        this.pinyinDupRecords = pinyinDupRecords;
    }

    public List<String> getWordDupRecords() {
        return wordDupRecords;
    }

    public void setWordDupRecords(List<String> wordDupRecords) {
        this.wordDupRecords = wordDupRecords;
    }

    public List<String> getPinYinPosDupRecords() {
        return pinYinPosDupRecords;
    }

    public void setPinYinPosDupRecords(List<String> pinYinPosDupRecords) {
        this.pinYinPosDupRecords = pinYinPosDupRecords;
    }

    public List<String> getWordPosRecords() {
        return wordPosRecords;
    }

    public void setWordPosRecords(List<String> wordPosRecords) {
        this.wordPosRecords = wordPosRecords;
    }

    public List<String> getIndustryDescRecords() {
        return industryDescRecords;
    }

    public void setIndustryDescRecords(List<String> industryDescRecords) {
        this.industryDescRecords = industryDescRecords;
    }
}

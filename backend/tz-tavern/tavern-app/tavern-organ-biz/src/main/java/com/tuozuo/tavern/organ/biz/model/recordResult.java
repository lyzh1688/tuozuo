package com.tuozuo.tavern.organ.biz.model;

import java.math.BigDecimal;

//搜索记录处理结果
public class recordResult {
    String recordName;//记录名称
    String industryDesc;//记录行业描述
    BigDecimal totalMinusScore;//总扣减分数
    BigDecimal pinYingDupMinusScore;//谐音重复扣减分值
    BigDecimal wordDupMinusScore;//字号重复扣减分值
    BigDecimal pinYingPosMinusScore;//谐音偏移扣减分值
    BigDecimal wordPosMinusScore;//字号偏移扣减分值
    BigDecimal industryDescMinusScore;//行业描述扣减分值

    @Override
    public String toString() {
        return "recordResult{" +
                "recordName='" + recordName + '\'' +
                ", industryDesc='" + industryDesc + '\'' +
                ", totalMinusScore=" + totalMinusScore +
                ", pinYingDupMinusScore=" + pinYingDupMinusScore +
                ", wordDupMinusScore=" + wordDupMinusScore +
                ", pinYingPosMinusScore=" + pinYingPosMinusScore +
                ", wordPosMinusScore=" + wordPosMinusScore +
                ", industryDescMinusScore=" + industryDescMinusScore +
                '}';
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public String getIndustryDesc() {
        return industryDesc;
    }

    public void setIndustryDesc(String industryDesc) {
        this.industryDesc = industryDesc;
    }

    public BigDecimal getTotalMinusScore() {
        return totalMinusScore;
    }

    public void setTotalMinusScore(BigDecimal totalMinusScore) {
        this.totalMinusScore = totalMinusScore;
    }

    public BigDecimal getPinYingDupMinusScore() {
        return pinYingDupMinusScore;
    }

    public void setPinYingDupMinusScore(BigDecimal pinYingDupMinusScore) {
        this.pinYingDupMinusScore = pinYingDupMinusScore;
    }

    public BigDecimal getWordDupMinusScore() {
        return wordDupMinusScore;
    }

    public void setWordDupMinusScore(BigDecimal wordDupMinusScore) {
        this.wordDupMinusScore = wordDupMinusScore;
    }

    public BigDecimal getPinYingPosMinusScore() {
        return pinYingPosMinusScore;
    }

    public void setPinYingPosMinusScore(BigDecimal pinYingPosMinusScore) {
        this.pinYingPosMinusScore = pinYingPosMinusScore;
    }

    public BigDecimal getWordPosMinusScore() {
        return wordPosMinusScore;
    }

    public void setWordPosMinusScore(BigDecimal wordPosMinusScore) {
        this.wordPosMinusScore = wordPosMinusScore;
    }

    public BigDecimal getIndustryDescMinusScore() {
        return industryDescMinusScore;
    }

    public void setIndustryDescMinusScore(BigDecimal industryDescMinusScore) {
        this.industryDescMinusScore = industryDescMinusScore;
    }
}

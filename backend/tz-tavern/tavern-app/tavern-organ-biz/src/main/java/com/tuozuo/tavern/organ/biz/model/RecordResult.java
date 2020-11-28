package com.tuozuo.tavern.organ.biz.model;

import java.math.BigDecimal;

//搜索记录处理结果
public class RecordResult {
    String recordName;//记录名称
    String industryDesc;//记录行业描述
    String fullName;
    BigDecimal totalMinusScore;//总扣减分数
    BigDecimal pinYinDupMinusScore;//谐音重复扣减分值
    BigDecimal wordDupMinusScore;//字号重复扣减分值
    BigDecimal pinYinPosMinusScore;//谐音偏移扣减分值
    BigDecimal wordPosMinusScore;//字号偏移扣减分值
    BigDecimal industryDescMinusScore;//行业描述扣减分值


    @Override
    public String toString() {
        return "RecordResult{" +
                "recordName='" + recordName + '\'' +
                ", industryDesc='" + industryDesc + '\'' +
                ", fullName='" + fullName + '\'' +
                ", totalMinusScore=" + totalMinusScore +
                ", pinYinDupMinusScore=" + pinYinDupMinusScore +
                ", wordDupMinusScore=" + wordDupMinusScore +
                ", pinYinPosMinusScore=" + pinYinPosMinusScore +
                ", wordPosMinusScore=" + wordPosMinusScore +
                ", industryDescMinusScore=" + industryDescMinusScore +
                '}';
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public BigDecimal getPinYinDupMinusScore() {
        return pinYinDupMinusScore;
    }

    public void setPinYinDupMinusScore(BigDecimal pinYinDupMinusScore) {
        this.pinYinDupMinusScore = pinYinDupMinusScore;
    }

    public BigDecimal getWordDupMinusScore() {
        return wordDupMinusScore;
    }

    public void setWordDupMinusScore(BigDecimal wordDupMinusScore) {
        this.wordDupMinusScore = wordDupMinusScore;
    }

    public BigDecimal getPinYinPosMinusScore() {
        return pinYinPosMinusScore;
    }

    public void setPinYinPosMinusScore(BigDecimal pinYinPosMinusScore) {
        this.pinYinPosMinusScore = pinYinPosMinusScore;
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

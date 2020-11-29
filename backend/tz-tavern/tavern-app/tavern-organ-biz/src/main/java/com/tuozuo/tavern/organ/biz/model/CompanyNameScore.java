package com.tuozuo.tavern.organ.biz.model;

import java.math.BigDecimal;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/29 <br>
 */
public class CompanyNameScore {
    private String name;
    private BigDecimal score;

    public CompanyNameScore(String name, BigDecimal score) {
        this.name = name;
        this.score = score;
    }

    public static CompanyNameScore createNameScore(String name, BigDecimal score) {
        score = score.setScale(2,BigDecimal.ROUND_HALF_UP);
        return new CompanyNameScore(name, score);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }
}

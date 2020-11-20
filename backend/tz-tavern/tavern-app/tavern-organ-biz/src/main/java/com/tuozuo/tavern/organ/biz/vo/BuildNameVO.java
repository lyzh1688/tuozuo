package com.tuozuo.tavern.organ.biz.vo;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/20 <br>
 */
public class BuildNameVO {

    private String area;
    private String industry;
    private String source;
    private String preferWord;
    private Boolean isTwoWords;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPreferWord() {
        return preferWord;
    }

    public void setPreferWord(String preferWord) {
        this.preferWord = preferWord;
    }

    public Boolean getTwoWords() {
        return isTwoWords;
    }

    public void setTwoWords(Boolean twoWords) {
        isTwoWords = twoWords;
    }
}

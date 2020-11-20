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
    private String isTwoWords;
    private String type;
    private int pageNo;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

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

    public String getIsTwoWords() {
        return isTwoWords;
    }

    public void setIsTwoWords(String isTwoWords) {
        this.isTwoWords = isTwoWords;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

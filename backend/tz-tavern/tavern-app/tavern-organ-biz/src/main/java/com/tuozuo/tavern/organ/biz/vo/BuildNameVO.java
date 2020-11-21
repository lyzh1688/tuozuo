package com.tuozuo.tavern.organ.biz.vo;

import javax.validation.constraints.NotNull;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/20 <br>
 */
public class BuildNameVO extends PageVO{

    @NotNull(message = "area is not null")
    private String area;
    @NotNull(message = "industry is not null")
    private String industry;
    @NotNull(message = "source is not null")
    private String source;
    @NotNull(message = "preferWord is not null")
    private String preferWord;
    @NotNull(message = "isTwoWords is not null")
    private String isTwoWords;
    @NotNull(message = "type is not null")
    private String type;



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

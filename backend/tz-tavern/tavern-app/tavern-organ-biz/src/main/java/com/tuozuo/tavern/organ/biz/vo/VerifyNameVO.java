package com.tuozuo.tavern.organ.biz.vo;

import javax.validation.constraints.NotNull;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/20 <br>
 */
public class VerifyNameVO {

    @NotNull(message = "area is not null")
    private String area;
    @NotNull(message = "industry is not null")
    private String industry;
    @NotNull(message = "name is not null")
    private String name;



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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

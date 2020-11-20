package com.tuozuo.tavern.organ.biz.model;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/20 <br>
 */
public class CompanyName {
    private String fullName;
    private String name;
    private List<Integer> strokeNums;
    private String reference;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getStrokeNums() {
        return strokeNums;
    }

    public void setStrokeNums(List<Integer> strokeNums) {
        this.strokeNums = strokeNums;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}

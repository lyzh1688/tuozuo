package com.tuozuo.tavern.organ.biz.model;

import com.google.common.collect.Lists;
import com.tuozuo.tavern.organ.biz.util.PythonProcessUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/20 <br>
 */
public class CompanyName implements Serializable {
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

    public static CompanyName create(String result, String isTwoWords) {
        try {
            String[] strs = StringUtils.split(result, "@");
            if(PythonProcessUtils.hasDigit(strs[1])){
                return null;
            }
            CompanyName companyName = new CompanyName();
            companyName.setFullName(strs[0]);
            companyName.setName(strs[1]);
            List<Integer> numList = Lists.newArrayList();
            if (isTwoWords.equals("true")) {
                numList.add(Integer.parseInt(strs[2]));
                numList.add(Integer.parseInt(strs[3]));
                companyName.setReference(strs[4]);
            } else {
                numList.add(Integer.parseInt(strs[2]));
                numList.add(Integer.parseInt(strs[3]));
                numList.add(Integer.parseInt(strs[4]));
                companyName.setReference(strs[5]);
            }
            companyName.setStrokeNums(numList);
            return companyName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "CompanyName{" +
                "fullName='" + fullName + '\'' +
                ", name='" + name + '\'' +
                ", strokeNums=" + strokeNums +
                ", reference='" + reference + '\'' +
                '}';
    }
}

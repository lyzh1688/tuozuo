package com.tuozuo.tavern.organ.biz.util;

import com.google.common.collect.Lists;
import com.tuozuo.tavern.organ.biz.store.CompanyPropertyStore;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/24 <br>
 */
@Component
public class FilterUtils {

    @Autowired
    private CompanyPropertyStore companyPropertyStore;

    public String filterSpecialChar(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public List<String> filterAreaChar(List<String> str) {
        for (int i = 0; i < str.size(); i++) {
            if (companyPropertyStore.getAreaMap().containsKey(str.get(i))) {
                str.remove(i);
                break;
            }
        }
        return str;
    }

    public List<String> filterTypeChar(List<String> str) {
//        [南京, 延锋安道, 拓, 座椅, 有限公司, 无锡, 分公司]
        List<String> filterResult = Lists.newArrayList();
        for (int i = 0; i < str.size(); i++) {
            if (companyPropertyStore.getTypeMap().containsKey(str.get(i))) {
                break;
            } else {
                filterResult.add(str.get(i));
            }
        }
        return filterResult;
    }

    public String getIndustryChar(List<String> str) {
//        [南京, 延锋安道, 拓, 座椅, 有限公司, 无锡, 分公司]
        StringBuilder ind = new StringBuilder();
        for (int i = 0; i < str.size(); i++) {
            if (companyPropertyStore.getIndustryMap().containsKey(str.get(i))) {
                ind.append(str.get(i));
            }
        }
        return ind.toString();
    }

    public String getCompanySimpleName(List<String> str) {
//        [南京, 延锋安道, 拓, 座椅, 有限公司, 无锡, 分公司]
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < str.size(); i++) {
            if (!companyPropertyStore.getIndustryMap().containsKey(str.get(i))) {
                name.append(str.get(i));
            }
        }
        return name.toString();
    }


    //方法1、通过String的indexOf(String str, int fromIndex)方法
    private static void matchStringByIndexOf(String parent, String child) {
        int count = 0;
        int index = 0;
        while ((index = parent.indexOf(child, index)) != -1) {
            index = index + child.length();
            count++;
        }
        System.out.println("匹配个数为" + count);                              //结果输出
    }

    private static void matchStringByRegularExpression(String parent, String child) {

        int count = 0;
        Pattern p = Pattern.compile(child);
        Matcher m = p.matcher(parent);
        while (m.find()) {
            count++;
            System.out.println("匹配项" + count + "：" + m.group()); //group方法返回由以前匹配操作所匹配的输入子序列。
        }
        System.out.println("匹配个数为" + count);                              //结果输出
    }

    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();
        System.out.println(builder.toString().equals(""));
    }

}

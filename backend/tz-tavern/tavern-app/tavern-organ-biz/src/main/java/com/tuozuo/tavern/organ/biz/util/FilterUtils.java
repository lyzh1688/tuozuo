package com.tuozuo.tavern.organ.biz.util;

import com.tuozuo.tavern.organ.biz.store.CompanyPropertyStore;
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
            }
        }

        return str;
    }
    public List<String> filterTypeChar(List<String> str) {
        for (int i = 0; i < str.size(); i++) {
            if (companyPropertyStore.getTypeMap().containsKey(str.get(i))) {
                for (int j = i; j < str.size(); j++) {
                    str.remove(j);

                }
            }
        }

        return str;
    }


}

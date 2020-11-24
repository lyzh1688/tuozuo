package com.tuozuo.tavern.organ.biz.util;

import com.tuozuo.tavern.organ.biz.store.CompanyPropertyStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public String filterSpecialChar(String str){
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public String filterAreaChar(String str){

    }



}

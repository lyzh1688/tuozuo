package com.tuozuo.tavern.shuiruyi.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/8 <br>
 */
public class ValidateUtils {

    /**
     * 判断字符串中是否包含中文
     *
     * @param str 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }


    public static void main(String[] args) {
        System.out.println(isContainChinese("123"));
    }
}

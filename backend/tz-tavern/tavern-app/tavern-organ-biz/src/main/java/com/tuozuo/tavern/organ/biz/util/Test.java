package com.tuozuo.tavern.organ.biz.util;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/22 <br>
 */
public class Test {

    public static void main(String[] args) {
        /*String str = "阿里1123";
        System.out.println(str.length());

        char[] chr = str.toCharArray();
        String target = "";

        for (int i = chr.length - 1; i >= 0; i--) {
            target += chr[i];
        }
        System.out.println(chr);*/
        List<String> list = Lists.newArrayList();
        list.add("1");
        list.add("2");
        list.add("1");
        list.add("京基智农");
        list.add("京基");
        list.add("京基智农");
        list = list.stream().distinct().collect(Collectors.toList());
        System.out.println(list);
    }


}

package com.tuozuo.tavern.organ.biz.util;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/22 <br>
 */
public class Test {

    public static void main(String[] args) {
        List<String> pinyinList = Lists.newArrayList();
        pinyinList.add("qing");
        pinyinList.add("long");
        List<String> recordPinyinList = Lists.newArrayList();
        recordPinyinList.add("qin");
        recordPinyinList.add("lon");
        int size = pinyinList.stream()
                .map(t -> recordPinyinList.stream().filter(s -> Objects.nonNull(s) && Objects.nonNull(s) && Objects.equals(t, s)).findAny().orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList())
                .size();
        System.out.println(size);
    }


}

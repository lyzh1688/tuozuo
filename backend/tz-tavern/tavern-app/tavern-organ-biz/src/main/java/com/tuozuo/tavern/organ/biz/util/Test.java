package com.tuozuo.tavern.organ.biz.util;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/22 <br>
 */
public class Test {

    public static void main(String[] args) {
        String str = "阿里";

        char[] chr = str.toCharArray();
        String target = "";

        for (int i = chr.length - 1; i >= 0; i--) {
            target += chr[i];
        }
        System.out.println(chr);


    }


}

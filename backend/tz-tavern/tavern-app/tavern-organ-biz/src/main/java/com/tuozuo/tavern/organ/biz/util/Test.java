package com.tuozuo.tavern.organ.biz.util;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/22 <br>
 */
public class Test {

    public static void main(String[] args) {
        String str = "阿里巴巴";
        comb(str.toCharArray());
    }

    public static void comb(char[] chs) {
        int len = chs.length;
        int nbits = 1 << len;
        for (int i = 0; i < nbits; ++i) {
            int t;
            for (int j = 0; j < len; j++) {
                t = 1 << j;
                if ((t & i) != 0) { // 与运算，同为1时才会是1
                    System.out.print(chs[j]);
                }
            }
        }
    }

}

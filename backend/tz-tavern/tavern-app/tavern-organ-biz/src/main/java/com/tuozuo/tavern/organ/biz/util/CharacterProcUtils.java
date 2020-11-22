package com.tuozuo.tavern.organ.biz.util;

import com.huaban.analysis.jieba.JiebaSegmenter;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/22 <br>
 */
public class CharacterProcUtils {

    public static String createReverseCharacter(String str) {
        char[] chr = str.toCharArray();
        String target = "";

        for (int i = chr.length - 1; i >= 0; i--) {
            target += chr[i];
        }
        return target;
    }

    public static void main(String[] args) {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        String sentences = "东方红";
        System.out.println(segmenter.sentenceProcess(sentences));
        System.out.println(segmenter.process(sentences,JiebaSegmenter.SegMode.INDEX));
        System.out.println(segmenter.process(sentences,JiebaSegmenter.SegMode.SEARCH));
    }
}

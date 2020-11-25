package com.tuozuo.tavern.organ.biz.util;

import com.huaban.analysis.jieba.JiebaSegmenter;

import java.util.List;
import java.util.stream.Collectors;

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

    public static List<String> splitCharacters(String sentences) {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<String> resultist = segmenter.sentenceProcess(sentences).stream()
                .filter(r -> r.length() != 1)
                .collect(Collectors.toList());
        return resultist;
    }

    public static List<String> splitSearchCharacters(String sentences) {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<String> resultist = segmenter.process(sentences, JiebaSegmenter.SegMode.SEARCH).stream()
                .map(token -> token.word)
                .collect(Collectors.toList());
        return resultist;
    }

    public static void main(String[] args) {
        JiebaSegmenter segmenter = new JiebaSegmenter();
//        String sentences = "上海拓佑财务咨询有限公司";
//        String sentences = "杭州东方网升科技股份有限公司";
//        String sentences = "霍邱福润肉类加工有限公司";
        String sentences = "拓祚國際计算机科技有限公司";
        System.out.println(splitSearchCharacters(sentences));



        List<String> resultist = segmenter.sentenceProcess(sentences).stream()
//                .filter(r -> r.length() != 1)
                .collect(Collectors.toList());
        System.out.println(resultist);
        System.out.println(segmenter.process(sentences, JiebaSegmenter.SegMode.INDEX));
        System.out.println(segmenter.process(sentences, JiebaSegmenter.SegMode.SEARCH));
    }
}

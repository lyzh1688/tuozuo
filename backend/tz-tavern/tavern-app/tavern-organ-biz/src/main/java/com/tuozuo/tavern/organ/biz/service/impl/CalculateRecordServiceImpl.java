package com.tuozuo.tavern.organ.biz.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tuozuo.tavern.organ.biz.model.RecordItem;
import com.tuozuo.tavern.organ.biz.model.RecordMark;
import com.tuozuo.tavern.organ.biz.model.RecordResult;
import com.tuozuo.tavern.organ.biz.model.UserCompanyName;
import com.tuozuo.tavern.organ.biz.service.CalculateRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CalculateRecordServiceImpl implements CalculateRecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculateRecordServiceImpl.class);

    private static BigDecimal wordDupScore = BigDecimal.valueOf(2);
    private static BigDecimal pinyinDupScore = BigDecimal.valueOf(2);
    private static BigDecimal wordPosScore = BigDecimal.valueOf(1);
    private static BigDecimal pinyinPosScore = BigDecimal.valueOf(1);
    private static BigDecimal industryParam = BigDecimal.valueOf(0.8);
    private static BigDecimal totalScore = BigDecimal.valueOf(100);
    //计算公式 总扣减分数=（wordDupScore*字号重复度*字号占原名称占比+pinyinDupScore*拼音重复度*拼音占原名称占比+
    // wordPosScore*字号偏移度*字号占原名称占比+pinyinPosScore*拼音偏移度*拼音占原名称占比）*industryParam + 行业描述重复扣减分
    // 行业描述重复扣减分=（wordDupScore*字号重复度*字号占原名称占比+pinyinDupScore*拼音重复度*拼音占原名称占比+
    // wordPosScore*字号偏移度*字号占原名称占比+pinyinPosScore*拼音偏移度*拼音占原名称占比）*(1-industryParam)
    //行业描述重复扣减分在不匹配时为0

    @Override
    public RecordResult handleRecord(RecordItem item, UserCompanyName companyName) {
        return doCalculate(item, companyName);
    }

    private static RecordResult doCalculate(RecordItem item, UserCompanyName companyName) {
        if (item.getMarkers().size() == 0) {
            LOGGER.warn("item misses markers:{}", item);
        }
        try {
            List<String> wordList = getWordSet(item.getMarkers());
            List<String> pinyinList = getPinyinSet(item.getMarkers());
            List<String> originNameList = Lists.newArrayList();
            for (char word : companyName.getName().toCharArray()) {
                originNameList.add(String.valueOf(word));
            }
            List<String> recordNameList = Lists.newArrayList();
            for (char word : item.getName().toCharArray()) {
                recordNameList.add(String.valueOf(word));
            }
            List<String> sameWordList = getSameWordList(wordList, recordNameList);
            BigDecimal wordInOriginPct = getPct(sameWordList, originNameList);
            BigDecimal wordInRecordPct = getPct(sameWordList, recordNameList);
            BigDecimal pinyinInOriginPct = getPct(pinyinList, companyName.getNamePinYinList());
            BigDecimal pinyinInRecordPct = getPct(pinyinList, item.getNamePinYinList());
            boolean isIndustryTypeSame = companyName.getIndustryDesc().equals(item.getIndustryDesc());
            BigDecimal wordPosPct = getPositionPct(sameWordList, recordNameList, originNameList);
            BigDecimal pinyinPosPct = getPositionPct(pinyinList, item.getNamePinYinList(), companyName.getNamePinYinList());
            RecordResult recordResult = new RecordResult();
            recordResult.setIndustryDesc(item.getIndustryDesc());
            recordResult.setRecordName(item.getName());
            recordResult.setFullName(item.getFullName());
            if (pinyinInOriginPct.equals(BigDecimal.ONE.setScale(6)) && pinyinInRecordPct.equals(BigDecimal.ONE.setScale(6)) && item.getName().length() == companyName.getName().length()) {
                if (isIndustryTypeSame) {
                    recordResult.setTotalMinusScore(totalScore);
                    recordResult.setPinYinDupMinusScore(BigDecimal.valueOf(25));
                    recordResult.setWordDupMinusScore(BigDecimal.valueOf(25));
                    recordResult.setPinYinPosMinusScore(BigDecimal.valueOf(20));
                    recordResult.setWordPosMinusScore(BigDecimal.valueOf(20));
                    recordResult.setIndustryDescMinusScore(BigDecimal.valueOf(10));
                    return recordResult;
                } else {
                    recordResult.setTotalMinusScore(BigDecimal.valueOf(90));
                    recordResult.setPinYinDupMinusScore(BigDecimal.valueOf(25));
                    recordResult.setWordDupMinusScore(BigDecimal.valueOf(25));
                    recordResult.setPinYinPosMinusScore(BigDecimal.valueOf(20));
                    recordResult.setWordPosMinusScore(BigDecimal.valueOf(20));
                    recordResult.setIndustryDescMinusScore(BigDecimal.ZERO);
                    return recordResult;
                }
            }
            BigDecimal wordDupMinusScore;
            BigDecimal pinyinDupMinusScore;
            BigDecimal wordPosMinusScore;
            BigDecimal pinyinPosMinusScore;
            BigDecimal industryDescMinusScore = BigDecimal.ZERO;
            wordDupMinusScore = wordDupScore.multiply(wordInOriginPct).multiply(wordInRecordPct);
            pinyinDupMinusScore = pinyinDupScore.multiply(pinyinInOriginPct).multiply(pinyinInRecordPct);
            wordPosMinusScore = wordPosScore.multiply(wordInOriginPct).multiply(wordPosPct);
            pinyinPosMinusScore = pinyinPosScore.multiply(pinyinInOriginPct).multiply(pinyinPosPct);
            BigDecimal total = wordDupMinusScore.add(pinyinDupMinusScore).add(wordPosMinusScore).add(pinyinPosMinusScore);
            if (isIndustryTypeSame) {
                industryDescMinusScore = total.multiply(BigDecimal.ONE.subtract(industryParam));
            }
            recordResult.setPinYinDupMinusScore(pinyinDupMinusScore.multiply(industryParam));
            recordResult.setWordDupMinusScore(wordDupMinusScore.multiply(industryParam));
            recordResult.setPinYinPosMinusScore(pinyinPosMinusScore.multiply(industryParam));
            recordResult.setWordPosMinusScore(wordPosMinusScore.multiply(industryParam));
            recordResult.setIndustryDescMinusScore(industryDescMinusScore);
            recordResult.setTotalMinusScore(BigDecimal.ZERO
                    .add(recordResult.getIndustryDescMinusScore())
                    .add(recordResult.getPinYinDupMinusScore())
                    .add(recordResult.getPinYinPosMinusScore())
                    .add(recordResult.getWordDupMinusScore())
                    .add(recordResult.getWordPosMinusScore()));
            return recordResult;
        } catch (Exception e) {
            LOGGER.warn("handleRecord error", e);
            throw e;
        }
    }

    private static BigDecimal getPositionPct(List<String> wordList, List<String> source, List<String> target) {
        if(wordList.size() == 0){
            return BigDecimal.ZERO;
        }
        BigDecimal posNum = BigDecimal.ZERO;
        for (String item : wordList) {
            String tmp1 = "";
            String tmp2 = "";
            for (int i = 0; i < source.size(); i++) {
                if (item.equals(source.get(i))) {
                    tmp1 = Strings.isNullOrEmpty(tmp1) ? String.valueOf(i) : tmp1 + "," + i;
                }
            }
            for (int i = 0; i < target.size(); i++) {
                if (item.equals(target.get(i))) {
                    tmp2 = Strings.isNullOrEmpty(tmp2) ? String.valueOf(i) : tmp2 + "," + i;
                }
            }
            if (tmp1.equals(tmp2)) {
                posNum = posNum.add(BigDecimal.ONE);
            } else if (tmp1.contains(tmp2)) {
                posNum = posNum.add(BigDecimal.ONE);
            } else if (tmp2.contains(tmp1)) {
                posNum = posNum.add(BigDecimal.ONE);
            }
        }
        return posNum.divide(BigDecimal.valueOf(wordList.size()), 6, BigDecimal.ROUND_HALF_UP);
    }

    private static List<String> getSameWordList(List<String> source, List<String> target) {
        List<String> sameWordList = Lists.newArrayList();
        for (String item : source) {
            for (String item2 : target) {
                if (item.equals(item2)) {
                    sameWordList.add(item);
                }
            }
        }
        return sameWordList;
    }

    private static List<String> getWordSet(List<RecordMark> marks) {
        Set<String> wordSet = Sets.newHashSet();
        for (RecordMark mark : marks) {
            for (char word : mark.getWord().toCharArray()) {
                wordSet.add(String.valueOf(word));
            }
        }
        return new ArrayList<>(wordSet);
    }

    private static List<String> getPinyinSet(List<RecordMark> marks) {
        Set<String> pinyinSet = Sets.newHashSet();
        for (RecordMark mark : marks) {
            pinyinSet.addAll(mark.getWordPinYinList());
        }
        return new ArrayList<>(pinyinSet);
    }

    private static BigDecimal getPct(List<String> source, List<String> target) {
        class PicItem {
            String name;
            boolean isPicked;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public boolean isPicked() {
                return isPicked;
            }

            public void setPicked(boolean picked) {
                this.isPicked = picked;
            }
        }
        List<PicItem> targetPic = Lists.newArrayList();
        target.forEach(p -> {
            PicItem picItem = new PicItem();
            picItem.setName(p);
            picItem.setPicked(false);
            targetPic.add(picItem);
        });
        for (String item : source) {
            for (PicItem picItem : targetPic) {
                if (picItem.getName().equals(item)) {
                    picItem.setPicked(true);
                }
            }
        }
        BigDecimal totalNum = BigDecimal.valueOf(targetPic.size());
        BigDecimal pickedNum = BigDecimal.ZERO;
        for (PicItem picItem : targetPic) {
            if (picItem.isPicked) {
                pickedNum = pickedNum.add(BigDecimal.ONE);
            }
        }
        return pickedNum.divide(totalNum, 6, BigDecimal.ROUND_HALF_UP);
    }

//    public static void main(String[] args) {
//        RecordMark mark1 = new RecordMark();
//        mark1.setWord("中文啊");
//        RecordMark mark2 = new RecordMark();
//        mark2.setWord("我文啊");
//        List<RecordMark> marks1 = Lists.newArrayList();
//        marks1.add(mark1);
//        marks1.add(mark2);
//        List<String> wordSet1 = getWordSet(marks1);
//        System.out.println(wordSet1);
//        List<String> sameWord = Lists.newArrayList("我", "啊");
//        System.out.println(getSameWordList(sameWord, wordSet1));
//        RecordMark mark3 = new RecordMark();
//        mark3.setWordPinYinList(Lists.newArrayList("wo", "zen", "me"));
//        RecordMark mark4 = new RecordMark();
//        mark4.setWordPinYinList(Lists.newArrayList("wo", "zen", "le"));
//        List<RecordMark> marks = Lists.newArrayList();
//        marks.add(mark3);
//        marks.add(mark4);
//        List<String> wordSet = getPinyinSet(marks);
//        System.out.println(wordSet);
//        System.out.println(getPct(Lists.newArrayList("zen", "me"), Lists.newArrayList("wo", "zen", "me")));
//
//        UserCompanyName userCompanyName = new UserCompanyName();
//        userCompanyName.setArea("上海");
//        userCompanyName.setIndustryDesc("网络科技");
//        userCompanyName.setName("阿里巴巴");
//        userCompanyName.setNamePinYinList(Lists.newArrayList("a","li","ba","ba"));
//
//        RecordItem recordItem = new RecordItem();
//        recordItem.setIndustryDesc("农业科技");
//        recordItem.setName("阿里吧粑去哪儿");
//        recordItem.setNamePinYinList(Lists.newArrayList("a","li","ba","ba","qu","na","er"));
//        RecordMark mark5 = new RecordMark();
//        mark5.setWord("阿里");
//        mark5.setWordPinYinList(Lists.newArrayList("a", "li"));
//        RecordMark mark6 = new RecordMark();
//        mark6.setWord("巴巴");
//        mark6.setWordPinYinList(Lists.newArrayList("ba", "ba"));
//        List<RecordMark> markss = Lists.newArrayList();
//        markss.add(mark5);
//        markss.add(mark6);
//        recordItem.setMarkers(markss);
//        RecordResult recordResult = doCalculate(recordItem, userCompanyName);
//        System.out.println(recordResult);
//    }
}

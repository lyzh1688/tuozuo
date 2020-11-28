package com.tuozuo.tavern.organ.biz.service.impl;

import com.google.common.collect.Lists;
import com.tuozuo.tavern.organ.biz.exeception.QccException;
import com.tuozuo.tavern.organ.biz.model.*;
import com.tuozuo.tavern.organ.biz.service.CompanyNameService;
import com.tuozuo.tavern.organ.biz.util.PinyinProcUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/23 <br>
 */
public abstract class CompanyNameTemplate implements CompanyNameService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyNameTemplate.class);

    public abstract BigDecimal getPerfectScores();

    public abstract List<String> splitName(String name);

    public abstract List<CompanyNameRecord> transferPinyin(List<String> name);

    public abstract List<CompanyNameRecord> getCompanyName(List<CompanyNameRecord> recordList) throws QccException;

    public abstract List<RecordItem> processCompanyName(List<CompanyNameRecord> companyNameList);

    public abstract RecordResult calculateRecord(RecordItem recordItem, UserCompanyName userCompanyName);

    public abstract void storeCompanyNameRecord(List<CompanyNameRecord> companyNameRecordList);

    @Override
    public CompanyVerifyResult queryCompanyResult(String area, String name, String industryDesc) throws QccException {

        UserCompanyName userCompanyName = this.createUserCompanyName(area, name, industryDesc);

        //1、二个字倒序，二个字以上拆词
        List<String> splitNames = this.splitName(name);
        //2、拼音转换
        List<CompanyNameRecord> pinyinRecords = this.transferPinyin(splitNames);
        //3、查询数据库或调用接口
        List<CompanyNameRecord> companyNameRecords = this.getCompanyName(pinyinRecords);
        if (companyNameRecords.isEmpty()) {
            LOGGER.error("[核名未获取任何匹配结果] area:[{}] name:[{}] industryDesc:[{}] ", area, name, industryDesc);
            return CompanyVerifyResult.defCompanyNameRecord();
        }
        List<RecordItem> recordItemList = this.processCompanyName(companyNameRecords);
        BigDecimal perfectScores = this.getPerfectScores();
        List<RecordResult> recordResults = Lists.newArrayList();
        for (RecordItem item : recordItemList) {
            RecordResult recordResult = this.calculateRecord(item, userCompanyName);
            LOGGER.error("[核名扣分结果] area:[{}] name:[{}] industryDesc:[{}],recordResult:[{}] ", area, item.getName(), item.getIndustryDesc(),recordResult.toString());
            perfectScores = (perfectScores.subtract(recordResult.getTotalMinusScore())).setScale(2,BigDecimal.ROUND_HALF_UP);
            recordResults.add(recordResult);
        }
        //4、计算最高的三条数据
        CompanyVerifyResult companyVerifyResult = this.statCompanyVerifyResult(recordResults);
        companyVerifyResult.setScores(perfectScores);
        this.storeCompanyNameRecord(companyNameRecords);


        return companyVerifyResult;
    }

    private UserCompanyName createUserCompanyName(String area, String name, String industryDesc) {
        UserCompanyName userCompanyName = new UserCompanyName();
        userCompanyName.setArea(area);
        userCompanyName.setIndustryDesc(industryDesc);
        userCompanyName.setName(name);
        List<String> pinyin = Arrays.asList(PinyinProcUtils.getPinyin(name, ","), ",");
        userCompanyName.setNamePinYinList(pinyin);
        return userCompanyName;
    }

    private CompanyVerifyResult statCompanyVerifyResult(List<RecordResult> recordResultList) {
        List<String> pinYinDupMinusScoreRank = recordResultList
                .parallelStream()
                .sorted((r1,r2) -> r2.getPinYinDupMinusScore().compareTo(r1.getPinYinDupMinusScore()))
                .map(RecordResult::getFullName)
                .collect(Collectors.toList());
        List<String> wordDupMinusScoreRank = recordResultList
                .parallelStream()
                .sorted((r1,r2) -> r2.getWordDupMinusScore().compareTo(r1.getWordDupMinusScore()))
                .map(RecordResult::getFullName)
                .collect(Collectors.toList());
        List<String> pinYinPosMinusScoreRank = recordResultList
                .parallelStream()
                .sorted((r1,r2) -> r2.getPinYinPosMinusScore().compareTo(r1.getPinYinPosMinusScore()))
                .map(RecordResult::getFullName)
                .collect(Collectors.toList());
        List<String> wordPosMinusScoreRank = recordResultList
                .parallelStream()
                .sorted((r1,r2) -> r2.getWordPosMinusScore().compareTo(r1.getWordPosMinusScore()))
                .map(RecordResult::getFullName)
                .collect(Collectors.toList());
        List<String> industryDescMinusScoreRank = recordResultList
                .parallelStream()
                .sorted((r1,r2) -> r2.getIndustryDescMinusScore().compareTo(r1.getIndustryDescMinusScore()))
                .map(RecordResult::getFullName)
                .collect(Collectors.toList());

        List<List<String>> pinYinDupMinusScoreList = Lists.partition(pinYinDupMinusScoreRank, 3);
        List<List<String>> wordDupMinusScoreList = Lists.partition(wordDupMinusScoreRank, 3);
        List<List<String>> pinYinPosMinusScoreList = Lists.partition(pinYinPosMinusScoreRank, 3);
        List<List<String>> wordPosMinusScoreList = Lists.partition(wordPosMinusScoreRank, 3);
        List<List<String>> industryDescMinusScoreList = Lists.partition(industryDescMinusScoreRank, 3);


        CompanyVerifyResult companyVerifyResult = new CompanyVerifyResult();
        companyVerifyResult.setPinyinDupRecords(pinYinDupMinusScoreList.get(0));
        companyVerifyResult.setWordDupRecords(wordDupMinusScoreList.get(0));
        companyVerifyResult.setPinYinPosDupRecords(pinYinPosMinusScoreList.get(0));
        companyVerifyResult.setWordPosRecords(wordPosMinusScoreList.get(0));
        companyVerifyResult.setIndustryDescRecords(industryDescMinusScoreList.get(0));

        return companyVerifyResult;

    }

}

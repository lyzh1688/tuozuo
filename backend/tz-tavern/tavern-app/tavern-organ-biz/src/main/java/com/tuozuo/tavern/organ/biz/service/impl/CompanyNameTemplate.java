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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/23 <br>
 */
public abstract class CompanyNameTemplate implements CompanyNameService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyNameTemplate.class);

    public abstract CompanyVerifyResult getSimilarResult(String name, String industryDesc) throws QccException;

    public abstract BigDecimal getPerfectScores();

    public abstract BigDecimal getMaxMinusScores();

    public abstract List<String> splitName(String name);

    public abstract List<CompanyNameRecord> transferPinyin(List<String> name);

    public abstract List<CompanyNameRecord> getCompanyName(List<CompanyNameRecord> recordList) throws QccException;

    public abstract List<RecordItem> processCompanyName(List<CompanyNameRecord> companyNameList);

    public abstract RecordResult calculateRecord(RecordItem recordItem, UserCompanyName userCompanyName);

    public abstract void storeCompanyNameRecord(List<CompanyNameRecord> companyNameRecordList);

    @Override
    public CompanyVerifyResult queryCompanyResult(String area, String name, String industryDesc) throws QccException {

        UserCompanyName userCompanyName = this.createUserCompanyName(area, name, industryDesc);
        CompanyVerifyResult similarVerifyResult = this.getSimilarResult(name, industryDesc);
        if (similarVerifyResult != null) {
            return similarVerifyResult;
        }

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
            LOGGER.info("[核名扣分入参] :area:[{}] name:[{}],industryDesc:[{}],RecordItem: [{}] ", area, name, industryDesc, item.toString());
        }
        for (RecordItem item : recordItemList) {
//            LOGGER.info("[核名扣分入参] :[{}] ", item.toString());
            RecordResult recordResult = this.calculateRecord(item, userCompanyName);
            LOGGER.info("[核名扣分结果] area:[{}] name:[{}],pinyin: [{}], industryDesc:[{}],recordResult:[{}] ", area, item.getName(), item.getNamePinYinList(), item.getIndustryDesc(), recordResult.toString());
            perfectScores = (perfectScores.subtract(recordResult.getTotalMinusScore())).setScale(2, BigDecimal.ROUND_HALF_UP);
            recordResults.add(recordResult);
            if (perfectScores.compareTo(BigDecimal.ZERO) < 0) {
                LOGGER.info("[核名扣分超过阀值] ");
                perfectScores = BigDecimal.ZERO;
                break;
            }
        }
        //4、计算最高的三条数据
        CompanyVerifyResult companyVerifyResult = this.statCompanyNameRankScores(recordResults);
        this.statCompanyItemScores(recordResults, companyVerifyResult);
        companyVerifyResult.setTotalScores(perfectScores);
        List<RecordResult> maxScoreRecords = this.getMaxScoreRecord(recordResults);
        companyVerifyResult.setMaxScoreRecords(maxScoreRecords);
        this.storeCompanyNameRecord(companyNameRecords);


        return companyVerifyResult;
    }

    private UserCompanyName createUserCompanyName(String area, String name, String industryDesc) {
        UserCompanyName userCompanyName = new UserCompanyName();
        userCompanyName.setArea(area);
        userCompanyName.setIndustryDesc(industryDesc);
        userCompanyName.setName(name);
        List<String> pinyin = Arrays.asList(StringUtils.split(PinyinProcUtils.getPinyin(name, ","), ","));
        userCompanyName.setNamePinYinList(pinyin);
        return userCompanyName;
    }

    private List<RecordResult> getMaxScoreRecord(List<RecordResult> recordResultList) {

        List<RecordResult> maxScoreRecordList = recordResultList.parallelStream()
                .filter(r -> r.getTotalMinusScore().compareTo(this.getMaxMinusScores()) > 0)
                .collect(Collectors.toList());
        return maxScoreRecordList;
    }

    private CompanyVerifyResult statCompanyItemScores(List<RecordResult> recordResultList, CompanyVerifyResult companyVerifyResult) {
        BigDecimal pinYinDupMinusScore = recordResultList
                .parallelStream()
                .map(r -> r.getPinYinDupMinusScore().setScale(2, BigDecimal.ROUND_HALF_UP))
                .reduce(BigDecimal::add).get();
        BigDecimal wordDupMinusScore = recordResultList
                .parallelStream()
                .map(r -> r.getWordDupMinusScore().setScale(2, BigDecimal.ROUND_HALF_UP))
                .reduce(BigDecimal::add).get();
        BigDecimal pinYinPosMinusScore = recordResultList
                .parallelStream()
                .map(r -> r.getPinYinPosMinusScore().setScale(2, BigDecimal.ROUND_HALF_UP))
                .reduce(BigDecimal::add).get();
        BigDecimal wordPosMinusScore = recordResultList
                .parallelStream()
                .map(r -> r.getWordPosMinusScore().setScale(2, BigDecimal.ROUND_HALF_UP))
                .reduce(BigDecimal::add).get();
        BigDecimal industryDescMinusScore = recordResultList
                .parallelStream()
                .map(r -> r.getIndustryDescMinusScore().setScale(2, BigDecimal.ROUND_HALF_UP))
                .reduce(BigDecimal::add).get();
        companyVerifyResult.setWordDupScores(wordDupMinusScore);
        companyVerifyResult.setWordPosScores(wordPosMinusScore);
        companyVerifyResult.setPinYinPosScores(pinYinPosMinusScore);
        companyVerifyResult.setPinyinDupScores(pinYinDupMinusScore);
        companyVerifyResult.setIndustryScores(industryDescMinusScore);

        return companyVerifyResult;
    }

    private CompanyVerifyResult statCompanyNameRankScores(List<RecordResult> recordResultList) {
        List<CompanyNameScore> pinYinDupMinusScoreRank = recordResultList
                .parallelStream()
                .filter(r -> r.getTotalMinusScore().compareTo(this.getMaxMinusScores()) < 0)
                .filter(r -> r.getPinYinDupMinusScore().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != 0)
                .sorted((r1, r2) -> r2.getPinYinDupMinusScore().compareTo(r1.getPinYinDupMinusScore()))
                .map(r -> CompanyNameScore.createNameScore(r.getFullName(), r.getPinYinDupMinusScore()))
                .collect(Collectors.toList());
        List<CompanyNameScore> wordDupMinusScoreRank = recordResultList
                .parallelStream()
                .filter(r -> r.getTotalMinusScore().compareTo(this.getMaxMinusScores()) < 0)
                .filter(r -> r.getWordDupMinusScore().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != 0)
                .sorted((r1, r2) -> r2.getWordDupMinusScore().compareTo(r1.getWordDupMinusScore()))
                .map(r -> CompanyNameScore.createNameScore(r.getFullName(), r.getWordDupMinusScore()))
                .collect(Collectors.toList());
        List<CompanyNameScore> pinYinPosMinusScoreRank = recordResultList
                .parallelStream()
                .filter(r -> r.getTotalMinusScore().compareTo(this.getMaxMinusScores()) < 0)
                .filter(r -> r.getPinYinPosMinusScore().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != 0)
                .sorted((r1, r2) -> r2.getPinYinPosMinusScore().compareTo(r1.getPinYinPosMinusScore()))
                .map(r -> CompanyNameScore.createNameScore(r.getFullName(), r.getPinYinPosMinusScore()))
                .collect(Collectors.toList());
        List<CompanyNameScore> wordPosMinusScoreRank = recordResultList
                .parallelStream()
                .filter(r -> r.getTotalMinusScore().compareTo(this.getMaxMinusScores()) < 0)
                .filter(r -> r.getWordPosMinusScore().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != 0)
                .sorted((r1, r2) -> r2.getWordPosMinusScore().compareTo(r1.getWordPosMinusScore()))
                .map(r -> CompanyNameScore.createNameScore(r.getFullName(), r.getWordPosMinusScore()))
                .collect(Collectors.toList());
        List<CompanyNameScore> industryDescMinusScoreRank = recordResultList
                .parallelStream()
                .filter(r -> r.getTotalMinusScore().compareTo(this.getMaxMinusScores()) < 0)
                .filter(r -> r.getIndustryDescMinusScore().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() != 0)
                .sorted((r1, r2) -> r2.getIndustryDescMinusScore().compareTo(r1.getIndustryDescMinusScore()))
                .map(r -> CompanyNameScore.createNameScore(r.getFullName(), r.getIndustryDescMinusScore()))
                .collect(Collectors.toList());

        List<List<CompanyNameScore>> pinYinDupMinusScoreList = Lists.partition(pinYinDupMinusScoreRank, 3);
        List<List<CompanyNameScore>> wordDupMinusScoreList = Lists.partition(wordDupMinusScoreRank, 3);
        List<List<CompanyNameScore>> pinYinPosMinusScoreList = Lists.partition(pinYinPosMinusScoreRank, 3);
        List<List<CompanyNameScore>> wordPosMinusScoreList = Lists.partition(wordPosMinusScoreRank, 3);
        List<List<CompanyNameScore>> industryDescMinusScoreList = Lists.partition(industryDescMinusScoreRank, 3);


        CompanyVerifyResult companyVerifyResult = new CompanyVerifyResult();
        if (pinYinDupMinusScoreRank.size() != 0) {
            companyVerifyResult.setPinyinDupRecords(pinYinDupMinusScoreList.get(0));
        }
        if (wordDupMinusScoreRank.size() != 0) {
            companyVerifyResult.setWordDupRecords(wordDupMinusScoreList.get(0));
        }
        if (pinYinPosMinusScoreRank.size() != 0) {
            companyVerifyResult.setPinYinPosDupRecords(pinYinPosMinusScoreList.get(0));
        }
        if (wordPosMinusScoreRank.size() != 0) {
            companyVerifyResult.setWordPosRecords(wordPosMinusScoreList.get(0));
        }
        if (industryDescMinusScoreRank.size() != 0) {
            companyVerifyResult.setIndustryDescRecords(industryDescMinusScoreList.get(0));
        }


        return companyVerifyResult;

    }


}

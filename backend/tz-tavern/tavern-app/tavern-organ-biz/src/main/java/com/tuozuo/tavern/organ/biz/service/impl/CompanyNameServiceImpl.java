package com.tuozuo.tavern.organ.biz.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tuozuo.tavern.organ.biz.dao.CompanyNameCountDao;
import com.tuozuo.tavern.organ.biz.dao.CompanyNameRecordDao;
import com.tuozuo.tavern.organ.biz.exeception.ExecuteException;
import com.tuozuo.tavern.organ.biz.exeception.QccException;
import com.tuozuo.tavern.organ.biz.executor.PythonExecutor;
import com.tuozuo.tavern.organ.biz.facade.qcc.model.CompanyBizData;
import com.tuozuo.tavern.organ.biz.facade.qcc.model.CompanyBizResult;
import com.tuozuo.tavern.organ.biz.facade.service.QccCompanyDataService;
import com.tuozuo.tavern.organ.biz.model.*;
import com.tuozuo.tavern.organ.biz.service.CalculateRecordService;
import com.tuozuo.tavern.organ.biz.store.CompanyPropertyStore;
import com.tuozuo.tavern.organ.biz.util.CharacterProcUtils;
import com.tuozuo.tavern.organ.biz.util.FilterUtils;
import com.tuozuo.tavern.organ.biz.util.PinyinProcUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/20 <br>
 */
@Service
public class CompanyNameServiceImpl extends CompanyNameTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyNameServiceImpl.class);

    private final int pageStart = 1;
    private final int pageSize = 20;


    //    @Value("${company.name.build.script.path:tavern-app/tavern-organ-biz/generateName/main.py}")
    @Value("${company.name.build.script.path:/opt/tuozuo/script/generateName/main.py}")
    private String scriptPath;
    @Value("${company.perfect.scores:100}")
    private int perfectScores;
    @Value("${company.max.minus.scores:3}")
    private int maxMinusScores;

    @Autowired
    PythonExecutor pythonExecutor;

    @Autowired
    private CompanyNameRecordDao companyNameRecordDao;
    @Autowired
    private QccCompanyDataService qccCompanyDataService;
    @Autowired
    private CalculateRecordService calculateRecordService;
    @Autowired
    private FilterUtils filterUtils;
    @Autowired
    private CompanyNameCountDao companyNameCountDao;
    @Autowired
    private CompanyPropertyStore companyPropertyStore;


    @Cacheable(value = "build_company_name", key = "#area +'.'+ #industry+'.'+ #source+'.'+ #preferWord+'.'+ #isTwoWords+'.'+ #type")
    @Override
    public List<CompanyName> queryCompanyName(String source,
                                              String area,
                                              String industry,
                                              String preferWord,
                                              String isTwoWords,
                                              String type) throws ExecuteException, IOException {

        List<String> resultList = this.pythonExecutor.executor(scriptPath, source, area, industry, convertPreferWord(preferWord), isTwoWords, type);
        List<CompanyName> companyNameList = resultList
                .stream()
                .map(r -> CompanyName.create(r, isTwoWords))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        for (int i = 0; i < 5; i++) {
            Collections.shuffle(companyNameList);
        }
        return companyNameList;
    }

    //兼容python程序中对特殊字符的处理
    private String convertPreferWord(String preferWord) {
        String defWord = StringUtils.isEmpty(preferWord) ? "nul" : preferWord;
        return defWord;
    }


    @Override
    public CompanyVerifyResult getSimilarResult(String area, String name, String industryDesc) throws QccException {
        String perfectName = StringUtils.join(area, name, industryDesc);
        String pinyin = StringUtils.join(StringUtils.split(PinyinProcUtils.getPinyin(name, ","), ","));
        List<String> recordResults = Lists.newArrayList();
        List<RecordResult> results = Lists.newArrayList();
        List<CompanyNameRecord> dbNames = this.companyNameRecordDao.queryCompanyRecordsByName(perfectName);
        if (!dbNames.isEmpty()) {
            LOGGER.info("get similar result from db");
            List<String> dbNameList = dbNames.stream().map(CompanyNameRecord::getFullName).collect(Collectors.toList());
            recordResults.addAll(dbNameList);
        } else {
            CompanyNameArea companyNameArea = companyPropertyStore.getQccAreaCodeMap().get(area);
            CompanyBizResult companyBizResult = this.getCompanyBizResult(companyNameArea.getProvinceCode(), companyNameArea.getCityCode(), perfectName);
            if (companyBizResult.getBizData() == null || companyBizResult.getBizData().size() == 0) {
                return null;
            }
            List<String> qccNameList = companyBizResult.getBizData().stream().map(CompanyBizData::getName).collect(Collectors.toList());
            recordResults.addAll(qccNameList);
        }

        for (String bizData : recordResults) {
            String fullName = filterUtils.filterSpecialChar(bizData);
            List<String> splitList = CharacterProcUtils.splitSearchCharacters(fullName);
            String filterArea = filterUtils.getAreaChar(splitList);
            splitList = filterUtils.filterAreaChar(splitList);
            splitList = filterUtils.filterTypeChar(splitList);
            String industryType = filterUtils.getIndustryChar(splitList);
            String rootName = filterUtils.getCompanySimpleName(splitList);
            String perfectResult = StringUtils.join(filterArea, rootName, industryType);
            if (perfectResult.equals(perfectName)) {
                LOGGER.info("[完全匹配核名名称] name: [{}],result name: [{}]", perfectName, fullName);
                results.add(RecordResult.perfectMatchResult(perfectResult, industryType, bizData));
            }
        }

        if (results.size() == 0) {
            return null;
        } else {
            List<CompanyNameRecord> companyNameRecords = results
                    .stream()
                    .map(r -> CompanyNameRecord.defRecord(area, pinyin, r.getFullName(), r.getRecordName(), r.getIndustryDesc()))
                    .collect(Collectors.toList());
            this.storeCompanyNameRecord(companyNameRecords);
            return CompanyVerifyResult.perfectCompanyNameRecord(results);
        }

    }


    @Override
    public BigDecimal getPerfectScores() {
        return BigDecimal.valueOf(perfectScores);
    }

    @Override
    public BigDecimal getMaxMinusScores() {
        return BigDecimal.valueOf(maxMinusScores);
    }

    @Override
    public List<String> splitName(String name) {
        List<String> list = Lists.newArrayList();
        list.add(name);
        if (name.length() == 2) {
            String reverseName = CharacterProcUtils.createReverseCharacter(name);
            list.add(reverseName);
        } else {
            List<String> splitNames = CharacterProcUtils.splitCharacters(name);
            list.addAll(splitNames);
        }
        list = list.stream().distinct().collect(Collectors.toList());
        return list;
    }

    @Override
    public List<CompanyNameRecord> transferPinyin(String area, List<String> name) {
        List<CompanyNameRecord> pinyinList = name.stream().map(s -> {
            String pinyin = PinyinProcUtils.getPinyin(s, ",");
            CompanyNameRecord record = new CompanyNameRecord();
            record.setPinyin(StringUtils.join(StringUtils.split(pinyin, ",")));
            record.setName(s);
            record.setArea(area);
            return record;
        }).collect(Collectors.toList());
        return pinyinList;
    }

    @Override
    public List<CompanyNameRecord> getCompanyNameByFullName(String area, String name, String industryDesc) throws QccException {
        List<CompanyNameRecord> names = Lists.newArrayList();
        String keyword = StringUtils.join(area,name, industryDesc);
        String pinyin = StringUtils.join(StringUtils.split(PinyinProcUtils.getPinyin(keyword, ","), ","));
        CompanyNameArea companyNameArea = companyPropertyStore.getQccAreaCodeMap().get(area);
        List<CompanyNameRecord> results = this.getSingleCompanyName(companyNameArea.getProvinceCode(), companyNameArea.getCityCode(), keyword, pinyin, keyword, area, 1);
        names.addAll(results);
        return names;
    }

    @Override
    public List<CompanyNameRecord> getCompanyName(List<CompanyNameRecord> recordList) throws QccException {
        List<CompanyNameRecord> names = Lists.newArrayList();
        for (CompanyNameRecord record : recordList) {
            CompanyNameArea companyNameArea = companyPropertyStore.getQccAreaCodeMap().get(record.getArea());
            List<CompanyNameRecord> results = this.getSingleCompanyName(companyNameArea.getProvinceCode(), companyNameArea.getCityCode(), record.getPinyin(), record.getPinyin(), record.getName(), record.getArea(), 5);
            names.addAll(results);
        }
        return names;
    }


    private List<CompanyNameRecord> getSingleCompanyName(String keyword, String pinyin, String name, String area, int times) throws QccException {
        return getSingleCompanyName(null, null, keyword, pinyin, name, area, times);
    }

    private List<CompanyNameRecord> getSingleCompanyName(String provinceCode, String cityCode, String keyword, String pinyin, String name, String area, int times) throws QccException {

        List<CompanyNameRecord> records;
        //1、先查数据库，有则取数据库
        List<CompanyNameRecord> dbNames = this.companyNameRecordDao.queryCompanyRecords(pinyin, area);
        //是否需要更新数据
        CompanyNameCount companyNameCount = this.companyNameCountDao.queryCompanyNameCount(pinyin);
        boolean isUpdate = false;
        if (companyNameCount != null) {
            int cnt = companyNameCount.getQueryCnt();
            int mod = cnt % 10;
            if (mod == 0) {
                isUpdate = true;
            }
        }
//            List<CompanyNameRecord> dbNames = Lists.newArrayList();
        if (!dbNames.isEmpty() && dbNames.size() > pageSize && !isUpdate) {
            LOGGER.info("get company name result from db");
            records = dbNames;
        } else {
            //2、api接口查询
            List<CompanyNameRecord> qccNames = this.getCompanyFromQcc(provinceCode, cityCode, keyword, name, pinyin, area, times);
            records = qccNames;
        }
        //更新调用次数
        if (companyNameCount == null) {
            companyNameCount = CompanyNameCount.create(pinyin, name);
        }
        companyNameCount.setQueryCnt(companyNameCount.getQueryCnt() + 1);
        this.companyNameCountDao.updateCompanyNameCount(companyNameCount);
        return records;
    }

    @Override
    public List<RecordItem> processCompanyName(List<CompanyNameRecord> companyNameList) {

        Map<String, RecordItem> dupRecordMap = Maps.newConcurrentMap();

        //拆词
        //1、过滤特殊字符
        //2、过滤地方字符
        //3、过滤类型字符
        //4、筛出行业：无行业传空字符串
        //5、筛出名称
        List<RecordItem> recordItemList = Lists.newArrayList();
        for (CompanyNameRecord record : companyNameList) {
            String name = filterUtils.filterSpecialChar(record.getFullName());
            List<String> splitList = CharacterProcUtils.splitSearchCharacters(name);
            splitList = filterUtils.filterAreaChar(splitList);
            splitList = filterUtils.filterTypeChar(splitList);
            String industryType = filterUtils.getIndustryChar(splitList);
            String rootName = filterUtils.getCompanySimpleName(splitList);
            record.setIndustry(industryType);
            RecordItem item = new RecordItem();
            item.setIndustryDesc(industryType);
            item.setName(rootName);
//            LOGGER.info("[industryType]: {},[rootName]: {},[fullName]: {}", industryType, rootName, record.getFullName());
            if (StringUtils.isEmpty(rootName)) {
                continue;
            }
            List<String> pinyinList = Arrays.asList(StringUtils.split(PinyinProcUtils.getPinyin(rootName, ","), ","));
            List<String> recordPinyinList = Arrays.asList(StringUtils.split(PinyinProcUtils.getPinyin(record.getName(), ","), ","));
            //若读音不一致，则删除
            if (!pinyinDupItem(pinyinList, recordPinyinList)) {
                continue;
            }
            item.setNamePinYinList(pinyinList);
            item.setFullName(record.getFullName());
            RecordMark recordMark = new RecordMark();
            recordMark.setWord(record.getName());
            recordMark.setWordPinYinList(recordPinyinList);
            if (dupRecordMap.containsKey(record.getFullName())) {
                RecordItem dupItem = dupRecordMap.get(record.getFullName());
                List<RecordMark> recordMarks = dupItem.getMarkers();
                recordMarks.add(recordMark);
                item.setMarkers(recordMarks);
                continue;
            } else {
                List<RecordMark> recordMarks = Lists.newArrayList();
                recordMarks.add(recordMark);
                item.setMarkers(recordMarks);
            }
            dupRecordMap.put(record.getFullName(), item);
            recordItemList.add(item);
        }
        return recordItemList;
    }

    private boolean pinyinDupItem(List<String> pinyinList, List<String> recordPinyinList) {
        int size = pinyinList.stream()
                .map(t -> recordPinyinList.stream().filter(s -> Objects.nonNull(s) && Objects.nonNull(s) && Objects.equals(t, s)).findAny().orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList())
                .size();

        return size > 0;
    }


    @Override
    public RecordResult calculateRecord(RecordItem recordItem, UserCompanyName userCompanyName) {
        return this.calculateRecordService.handleRecord(recordItem, userCompanyName);
    }

    @Override
    public void storeCompanyNameRecord(List<CompanyNameRecord> companyNameRecordList) {
        this.companyNameRecordDao.mergeCompanyRecords(companyNameRecordList);
    }


    private int getPageNum(int pageSize, int total) {
        int pageNum = (total % pageSize == 0) ? (total / pageSize) : (total / pageSize + 1);
        return pageNum;
    }

    private List<CompanyNameRecord> getCompanyFromQcc(String keyword, String name, String pinyin, String area, int times) throws QccException {
        return getCompanyFromQcc(null, null, keyword, name, pinyin, area, times);
    }

    private List<CompanyNameRecord> getCompanyFromQcc(String provinceCode, String cityCode, String keyword, String name, String pinyin, String area, int times) throws QccException {
        List<CompanyNameRecord> result = Lists.newArrayList();

        CompanyBizResult companyBizResult = this.getCompanyBizResult(provinceCode, cityCode, keyword);
        if (companyBizResult.getBizData() == null || companyBizResult.getBizData().size() == 0) {
            return result;
        }
        createCompanyRecord(name, pinyin, area, result, companyBizResult);
        int pageNum = this.getPageNum(pageSize, companyBizResult.getPage().getTotal());
        if (pageNum <= 1) {
            return result;
        } else {
            for (int i = 2; i <= pageNum; i++) {
                if (i > times) {
                    break;
                }
                CompanyBizResult pageResult = this.qccCompanyDataService.queryCompanyData(provinceCode, cityCode, keyword, i, pageSize);
                if (companyBizResult.getBizData() == null || companyBizResult.getBizData().size() == 0) {
                    return result;
                }
                createCompanyRecord(name, pinyin, area, result, pageResult);
            }
        }
        return result;
    }

    private CompanyBizResult getCompanyBizResult(String keyword) throws QccException {
        return getCompanyBizResult(null, null, keyword);
    }

    private CompanyBizResult getCompanyBizResult(String provinceCode, String cityCode, String keyword) throws QccException {
        CompanyBizResult companyBizResult = this.qccCompanyDataService.queryCompanyData(provinceCode, cityCode, keyword, pageStart, pageSize);
        if (Objects.isNull(companyBizResult)) {
            LOGGER.error("[企查查查询接口返回异常] error: null");
            throw new QccException("企查查查询接口返回异常");
        }
        if (HttpCodeRegex.isAbnormalRequest(companyBizResult.getStatus())) {
            LOGGER.error("[企查查查询接口返回异常] error: {}", companyBizResult.toString());
            throw new QccException("企查查查询接口返回异常");
        }
        return companyBizResult;
    }

    private void createCompanyRecord(String name, String pinyin, String area, List<CompanyNameRecord> result, CompanyBizResult pageResult) {
        List<CompanyNameRecord> pageName = pageResult.getBizData().stream().map(r -> {
            CompanyNameRecord record = new CompanyNameRecord();
            record.setName(name);
            record.setPinyin(pinyin);
            record.setFullName(r.getName());
            record.setArea(area);
            return record;
        }).collect(Collectors.toList());
        result.addAll(pageName);
    }
}

// 获取返回码 Res Code
class HttpCodeRegex {
    private static final String ABNORMAL_REGIX = "(101)|(102)";
    private static final Pattern pattern = Pattern.compile(ABNORMAL_REGIX);

    protected static boolean isAbnormalRequest(final String status) {
        return pattern.matcher(status).matches();
    }
}
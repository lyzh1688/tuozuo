package com.tuozuo.tavern.organ.biz.service.impl;

import com.google.common.collect.Lists;
import com.tuozuo.tavern.organ.biz.dao.CompanyNameRecordDao;
import com.tuozuo.tavern.organ.biz.exeception.ExecuteException;
import com.tuozuo.tavern.organ.biz.executor.PythonExecutor;
import com.tuozuo.tavern.organ.biz.facade.qcc.model.CompanyBizResult;
import com.tuozuo.tavern.organ.biz.facade.service.QccCompanyDataService;
import com.tuozuo.tavern.organ.biz.model.CompanyName;
import com.tuozuo.tavern.organ.biz.model.CompanyNameRecord;
import com.tuozuo.tavern.organ.biz.model.RecordItem;
import com.tuozuo.tavern.organ.biz.service.CalculateRecordService;
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
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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


    @Value("${company.name.build.script.path:tavern-app/tavern-organ-biz/generateName/main.py}")
    private String scriptPath;

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
    public List<String> transferPinyin(List<String> name) {
        List<String> pinyinList = name.stream().map(PinyinProcUtils::getPinyin).collect(Collectors.toList());
        return pinyinList;
    }

    @Override
    public List<CompanyNameRecord> getCompanyName(List<String> pinyinList) {
        List<CompanyNameRecord> names = Lists.newArrayList();
        for (String pinyin : pinyinList) {
            //1、先查数据库，有则取数据库
            List<CompanyNameRecord> dbNames = this.companyNameRecordDao.queryCompanyRecords(pinyin);
            if (!dbNames.isEmpty()) {
                names.addAll(dbNames);
            } else {
                //2、api接口查询
                List<CompanyNameRecord> qccNames = this.getCompanyFromQcc(pinyin);
                names.addAll(qccNames);
            }
        }
        return names;
    }

    @Override
    public List<RecordItem> processCompanyName(List<CompanyNameRecord> companyNameList) {
        //拆词
        //1、过滤特殊字符
        //2、过滤地方字符
        //3、过滤类型字符
        //4、筛出行业：无行业传空字符串
        //5、筛出名称
        for (CompanyNameRecord record : companyNameList) {
           String name = filterUtils.filterSpecialChar(record.getFullName());
           List<String> splitList = CharacterProcUtils.splitSearchCharacters(name);
            splitList = filterUtils.filterAreaChar(splitList);
            splitList = filterUtils.filterTypeChar(splitList);
            String industryType = filterUtils.getIndustryChar(splitList);
            if(industryType == null){
                int length = splitList.size();
                //1、1：直接拿，2
            }



        }


        return null;
    }


    private int getPageNum(int pageSize, int total) {
        int pageNum = (total % pageSize == 0) ? (total / pageSize) : (total / pageSize + 1);
        return pageNum;
    }

    private List<CompanyNameRecord> getCompanyFromQcc(String pinyin) {
        List<CompanyNameRecord> result = Lists.newArrayList();
        CompanyBizResult companyBizResult = this.qccCompanyDataService.queryCompanyData(pinyin, pageStart, pageSize);
        if (Objects.isNull(companyBizResult)) {
            return Collections.emptyList();
        }
        if (HttpCodeRegex.isAbnormalRequest(companyBizResult.getStatus())) {
            LOGGER.error("[企查查查询接口返回异常] error: {}", companyBizResult.toString());
            return Collections.emptyList();
        }

        List<CompanyNameRecord> firstPage = companyBizResult.getBizData().stream().map(r -> {
            CompanyNameRecord record = new CompanyNameRecord();
            record.setPinyin(pinyin);
            record.setFullName(r.getName());
            return record;
        }).collect(Collectors.toList());
        result.addAll(firstPage);
        int pageNum = this.getPageNum(pageSize, companyBizResult.getPage().getTotal());
        if (pageNum == 1) {
            return result;
        } else {
            for (int i = 2; i <= pageNum; i++) {
                if (i > 5) {
                    break;
                }
                CompanyBizResult pageResult = this.qccCompanyDataService.queryCompanyData(pinyin, i, pageSize);
                List<CompanyNameRecord> pageName = pageResult.getBizData().stream().map(r -> {
                    CompanyNameRecord record = new CompanyNameRecord();
                    record.setPinyin(pinyin);
                    record.setFullName(r.getName());
                    return record;
                }).collect(Collectors.toList());
                result.addAll(pageName);
            }
        }
        return result;
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
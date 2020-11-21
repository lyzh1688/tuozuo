package com.tuozuo.tavern.organ.biz.service.impl;

import com.tuozuo.tavern.organ.biz.dto.BuildNameDTO;
import com.tuozuo.tavern.organ.biz.exeception.ExecuteException;
import com.tuozuo.tavern.organ.biz.executor.PythonExecutor;
import com.tuozuo.tavern.organ.biz.model.CompanyName;
import com.tuozuo.tavern.organ.biz.service.CompanyNameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/20 <br>
 */
@Service
public class CompanyNameServiceImpl implements CompanyNameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyNameServiceImpl.class);

    @Value("${company.name.build.script.path:tavern-app/tavern-organ-biz/generateName/main.py}")
    private String scriptPath;

    @Autowired
    PythonExecutor pythonExecutor;


    @Cacheable(value = "build_company_name", key = "#area +'.'+ #industry+'.'+ #source+'.'+ #preferWord+'.'+ #isTwoWords+'.'+ #type")
    @Override
    public List<CompanyName> queryCompanyName(String source,
                                              String area,
                                              String industry,
                                              String preferWord,
                                              String isTwoWords,
                                              String type) throws ExecuteException, IOException {
        List<String> resultList = this.pythonExecutor.executor(scriptPath, source, area, industry, preferWord, isTwoWords, type);
        List<CompanyName> companyNameList = resultList
                .stream()
                .map(r -> CompanyName.create(r, isTwoWords))
                .filter(c -> c != null)
                .collect(Collectors.toList());
        for (int i = 0; i < 5; i++) {
            Collections.shuffle(companyNameList);
        }
        return companyNameList;
    }


}

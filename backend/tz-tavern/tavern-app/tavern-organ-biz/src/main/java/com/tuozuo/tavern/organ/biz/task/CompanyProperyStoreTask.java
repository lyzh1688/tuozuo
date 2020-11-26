package com.tuozuo.tavern.organ.biz.task;

import com.tuozuo.tavern.organ.biz.store.CompanyPropertyStore;
import com.tuozuo.tavern.organ.biz.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/24 <br>
 */
@Component
@EnableScheduling
public class CompanyProperyStoreTask implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyProperyStoreTask.class);

    @Value("${company.industry.path:tavern-app/tavern-organ-biz/industry.txt}")
    private String industryPath;
    @Value("${company.area.path:tavern-app/tavern-organ-biz/area.txt}")
    private String areaPath;
    @Value("${company.type.path:tavern-app/tavern-organ-biz/type.txt}")
    private String typePath;

    @Autowired
    private CompanyPropertyStore companyPropertyStore;

    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void companyIndustryStore() {
        try {
            List<String> industryList = FileUtils.getWordsFromFile(industryPath);
            for (String industry : industryList) {
                this.companyPropertyStore.updateIndustryMap(industry);
            }
        } catch (IOException e) {
            LOGGER.error("[companyIndustryStore] error: ", e);
        }
    }

    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void companyTypeStore() {
        try {
            List<String> typeList = FileUtils.getWordsFromFile(typePath);
            for (String type : typeList) {
                this.companyPropertyStore.updateTypeMap(type);
            }
        } catch (IOException e) {
            LOGGER.error("[companyTypeStore] error: ", e);
        }
    }

    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void companyAreaStore() {
        try {
            List<String> areaList = FileUtils.getWordsFromFile(areaPath);
            for (String area : areaList) {
                this.companyPropertyStore.updateAreaMap(area);
            }
        } catch (IOException e) {
            LOGGER.error("[companyAreaStore] error: ", e);
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
//        this.companyIndustryStore();
//        this.companyAreaStore();
//        this.companyTypeStore();
    }
}

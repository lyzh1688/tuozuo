package com.tuozuo.tavern.shuiruyi.task;

import com.tuozuo.tavern.shuiruyi.service.CustomInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/23 <br>
 */
@Component
@EnableScheduling
public class BusinessStatisticTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessStatisticTask.class);

    @Autowired
    private CustomInfoService customInfoService;

    @Scheduled(fixedRate = 60000)
    public void customStatisticTask() {
        try {
            LOGGER.info("start customStatistic");
            this.customInfoService.countCustomStatistic();
        } catch (Exception e) {
            LOGGER.error("customStatistic: ", e);
        }


    }

}

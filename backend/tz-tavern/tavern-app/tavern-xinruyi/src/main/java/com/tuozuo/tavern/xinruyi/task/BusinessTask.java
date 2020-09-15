package com.tuozuo.tavern.xinruyi.task;

import com.tuozuo.tavern.xinruyi.dao.ProjectInfoDao;
import com.tuozuo.tavern.xinruyi.service.ProjectInfoService;
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
public class BusinessTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessTask.class);

    @Autowired
    private ProjectInfoService projectInfoService;

    @Scheduled(fixedRate = 60000)
    public void projectStatusTask() {
        try {
            LOGGER.info("start projectStatusTask");
            this.projectInfoService.modifyProjectStatusTask();
        } catch (Exception e) {
            LOGGER.error("projectStatusTask: ", e);
        }


    }

}

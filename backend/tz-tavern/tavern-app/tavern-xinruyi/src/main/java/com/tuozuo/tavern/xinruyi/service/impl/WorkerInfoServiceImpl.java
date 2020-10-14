package com.tuozuo.tavern.xinruyi.service.impl;

import com.tuozuo.tavern.xinruyi.dao.WorkerInfoDao;
import com.tuozuo.tavern.xinruyi.model.WorkerSummaryInfo;
import com.tuozuo.tavern.xinruyi.service.WorkerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/10/14 <br>
 */
@Service
public class WorkerInfoServiceImpl implements WorkerInfoService {

    @Autowired
    private WorkerInfoDao workerInfoDao;

    @Override
    public WorkerSummaryInfo queryWorkerSumInfo(String registerId) {
        return this.workerInfoDao.selectWorkerSumInfo(registerId);
    }
}

package com.tuozuo.tavern.xinruyi.dao;

import com.tuozuo.tavern.xinruyi.model.WorkerInfo;
import com.tuozuo.tavern.xinruyi.model.WorkerSummaryInfo;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/10/14 <br>
 */
public interface WorkerInfoDao {

    WorkerSummaryInfo selectWorkerSumInfo(String registerId);

    void insert(WorkerInfo workerInfo);

}

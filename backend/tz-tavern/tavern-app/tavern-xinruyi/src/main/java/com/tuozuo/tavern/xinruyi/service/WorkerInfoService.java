package com.tuozuo.tavern.xinruyi.service;

import com.tuozuo.tavern.xinruyi.model.WorkerSummaryInfo;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/10/14 <br>
 */
public interface WorkerInfoService {

    WorkerSummaryInfo queryWorkerSumInfo(String registerId);
}

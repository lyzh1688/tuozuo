package com.tuozuo.tavern.xinruyi.service;

import com.tuozuo.tavern.xinruyi.model.WorkerInfo;
import com.tuozuo.tavern.xinruyi.model.WorkerSummaryInfo;
import com.tuozuo.tavern.xinruyi.vo.WorkerAuthVO;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/10/14 <br>
 */
public interface WorkerInfoService {

    WorkerSummaryInfo queryWorkerSumInfo(String registerId);

    void addWorker(WorkerAuthVO vo) throws Exception;

    void quitProject(String registerId,String projectId,String reason);

    WorkerInfo queryWorkerInfo(String registerId);

    void auditForWorkerInfo(String registerId,String remark,String result) throws Exception;
}

package com.tuozuo.tavern.xinruyi.dao;

import com.tuozuo.tavern.xinruyi.model.WorkerInfo;
import com.tuozuo.tavern.xinruyi.model.WorkerStaffRel;
import com.tuozuo.tavern.xinruyi.model.WorkerSummaryInfo;

import java.util.List;
import java.util.Optional;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/10/14 <br>
 */
public interface WorkerInfoDao {

    WorkerSummaryInfo selectWorkerSumInfo(String registerId);

    void insertOrUpdate(WorkerInfo workerInfo);

    List<WorkerStaffRel> selectWorkerStaffRel(String registerId);

    WorkerInfo selectById(String registerId);

    void insertStaffRel(WorkerStaffRel rel);

    Optional<WorkerStaffRel> selectWorkerStaffRelById(String registerId, String staffId);

}

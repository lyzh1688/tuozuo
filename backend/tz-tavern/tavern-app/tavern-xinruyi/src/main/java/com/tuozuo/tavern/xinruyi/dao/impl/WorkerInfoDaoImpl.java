package com.tuozuo.tavern.xinruyi.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.xinruyi.dao.WorkerInfoDao;
import com.tuozuo.tavern.xinruyi.mapper.WorkerInfoMapper;
import com.tuozuo.tavern.xinruyi.model.WorkerInfo;
import com.tuozuo.tavern.xinruyi.model.WorkerSummaryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/10/14 <br>
 */
@Repository
public class WorkerInfoDaoImpl extends ServiceImpl<WorkerInfoMapper, WorkerInfo> implements WorkerInfoDao {

    @Autowired
    private WorkerInfoMapper workerInfoMapper;


    @Override
    public WorkerSummaryInfo selectWorkerSumInfo(String registerId) {
        return this.workerInfoMapper.selectWorkerSumInfo(registerId);
    }

    @Override
    public void insert(WorkerInfo workerInfo) {
        this.saveOrUpdate(workerInfo);
    }
}

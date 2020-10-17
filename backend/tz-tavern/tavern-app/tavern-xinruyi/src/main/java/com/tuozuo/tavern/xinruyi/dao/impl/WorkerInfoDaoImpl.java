package com.tuozuo.tavern.xinruyi.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.xinruyi.dao.WorkerInfoDao;
import com.tuozuo.tavern.xinruyi.mapper.WorkerInfoMapper;
import com.tuozuo.tavern.xinruyi.mapper.WorkerStaffRelMapper;
import com.tuozuo.tavern.xinruyi.model.WorkerInfo;
import com.tuozuo.tavern.xinruyi.model.WorkerStaffRel;
import com.tuozuo.tavern.xinruyi.model.WorkerSummaryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/10/14 <br>
 */
@Repository
public class WorkerInfoDaoImpl extends ServiceImpl<WorkerInfoMapper, WorkerInfo> implements WorkerInfoDao {

    @Autowired
    private WorkerInfoMapper workerInfoMapper;
    @Autowired
    private WorkerStaffRelMapper workerStaffRelMapper;


    @Override
    public WorkerSummaryInfo selectWorkerSumInfo(String registerId) {
        return this.workerInfoMapper.selectWorkerSumInfo(registerId);
    }

    @Override
    public void insertOrUpdate(WorkerInfo workerInfo) {
        this.saveOrUpdate(workerInfo);
    }

    @Override
    public List<WorkerStaffRel> selectWorkerStaffRel(String registerId) {
        return this.workerStaffRelMapper.selectStaffProject(registerId);
    }

    @Override
    public WorkerInfo selectById(String registerId) {
        return this.workerInfoMapper.selectById(registerId);
    }

    @Override
    public void insertStaffRel(WorkerStaffRel rel) {
        this.workerStaffRelMapper.insert(rel);
    }
}

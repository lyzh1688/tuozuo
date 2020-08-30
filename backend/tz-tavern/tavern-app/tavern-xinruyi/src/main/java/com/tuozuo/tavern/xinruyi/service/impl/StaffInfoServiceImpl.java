package com.tuozuo.tavern.xinruyi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.xinruyi.convert.ModelMapConverter;
import com.tuozuo.tavern.xinruyi.dao.StaffInfoDao;
import com.tuozuo.tavern.xinruyi.model.StaffResourcePool;
import com.tuozuo.tavern.xinruyi.model.StaffSalaryInfo;
import com.tuozuo.tavern.xinruyi.service.StaffInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/28 <br>
 */
@Service
public class StaffInfoServiceImpl implements StaffInfoService {

    @Autowired
    private StaffInfoDao staffInfoDao;
    @Autowired
    ModelMapConverter converter;

    @Override
    public IPage<StaffResourcePool> queryStaffInfo(int pageNo, int pageSize, String companyId) {
        return this.staffInfoDao.selectStaffList(pageNo, pageSize, companyId);
    }

    @Override
    public void addStaffInfo(StaffResourcePool pool) {
        this.staffInfoDao.insert(pool);
    }

    @Override
    public void modifyStaffInfo(StaffResourcePool pool) {
        this.staffInfoDao.update(pool);
    }

    @Override
    public void removeStaff(String staffId) {
        this.staffInfoDao.delete(staffId);
    }

    @Override
    public List<StaffResourcePool> queryStaffByName(String name,String companyId,int queryCnt) {
        return this.staffInfoDao.selectByName(name, companyId, queryCnt);
    }

    @Override
    public IPage<StaffSalaryInfo> queryStaffSalaryInfo(int pageNo, int pageSize, String companyId, String staffId, String projectId, String beginDate, String endDate) {
        return this.staffInfoDao.selectStaffSalaryInfo(pageNo, pageSize, companyId, staffId, projectId, beginDate, endDate);
    }

    @Override
    public StaffResourcePool queryStaffInfo(String staffId) {
        return this.staffInfoDao.selectStaffInfo(staffId);
    }
}

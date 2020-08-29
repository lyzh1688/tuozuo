package com.tuozuo.tavern.xinruyi.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.xinruyi.dao.StaffInfoDao;
import com.tuozuo.tavern.xinruyi.mapper.StaffResourcePoolMapper;
import com.tuozuo.tavern.xinruyi.model.StaffResourcePool;
import com.tuozuo.tavern.xinruyi.model.StaffSalaryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/28 <br>
 */
@Repository
public class StaffInfoDaoImpl implements StaffInfoDao {

    @Autowired
    private StaffResourcePoolMapper staffResourcePoolMapper;

    @Override
    public IPage<StaffResourcePool> selectStaffList(int pageNo, int pageSize, String companyId) {
        Page page = new Page(pageNo, pageSize);
        return this.staffResourcePoolMapper.selectList(page,companyId);
    }

    @Override
    public void insert(StaffResourcePool pool) {
        this.staffResourcePoolMapper.insert(pool);
    }

    @Override
    public void update(StaffResourcePool pool) {
        this.staffResourcePoolMapper.updateById(pool);
    }

    @Override
    public void delete(String staffId) {
        this.staffResourcePoolMapper.delete(staffId);
    }

    @Override
    public List<StaffResourcePool> selectByName(String name,String companyId,int queryCnt) {
        return this.staffResourcePoolMapper.selectList(Wrappers.<StaffResourcePool>query().lambda()
                .eq(StaffResourcePool::getIsValid,"1")
                .eq(StaffResourcePool::getCompanyId,companyId)
                .like(StaffResourcePool::getStaffName, name)
                .last("limit " + queryCnt));
    }

    @Override
    public IPage<StaffSalaryInfo> selectStaffSalaryInfo(int pageNo, int pageSize, String companyId, String staffId, String projectId, String beginDate, String endDate) {
        Page page = new Page(pageNo, pageSize);
        return this.staffResourcePoolMapper.selectSalary(page,companyId,staffId,projectId,beginDate,endDate);
    }



}
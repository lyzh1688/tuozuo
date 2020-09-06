package com.tuozuo.tavern.xinruyi.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.xinruyi.dao.StaffInfoDao;
import com.tuozuo.tavern.xinruyi.mapper.StaffResourcePoolMapper;
import com.tuozuo.tavern.xinruyi.model.StaffResourcePool;
import com.tuozuo.tavern.xinruyi.model.StaffSalaryInfo;
import org.apache.commons.lang3.StringUtils;
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
        return this.staffResourcePoolMapper.selectByPage(page, companyId);
    }

    @Override
    public void insert(StaffResourcePool pool) {
        this.staffResourcePoolMapper.insert(pool);
    }

    @Override
    public void update(StaffResourcePool pool) {
        this.staffResourcePoolMapper.update(pool, Wrappers.<StaffResourcePool>query().lambda()
                .eq(StaffResourcePool::getCompanyId, pool.getCompanyId())
                .eq(StaffResourcePool::getStaffId, pool.getStaffId()));
    }

    @Override
    public void delete(String staffId) {
        this.staffResourcePoolMapper.delete(staffId);
    }

    @Override
    public List<StaffResourcePool> selectAllStaff(String name) {
        return this.staffResourcePoolMapper.selectList(Wrappers.<StaffResourcePool>query().lambda()
                .eq(StaffResourcePool::getIsValid, "1")
                .like(StringUtils.isNoneEmpty(name), StaffResourcePool::getStaffName, name));
    }

    @Override
    public List<StaffResourcePool> selectAllCustomStaff(String name, String companyId) {
        return this.staffResourcePoolMapper.selectList(Wrappers.<StaffResourcePool>query().lambda()
                .eq(StaffResourcePool::getIsValid, "1")
                .eq(StaffResourcePool::getCompanyId, companyId)
                .like(StringUtils.isNoneEmpty(name), StaffResourcePool::getStaffName, name));
    }

    @Override
    public List<StaffResourcePool> selectStaff(String name, int queryCnt) {
        return this.staffResourcePoolMapper.selectList(Wrappers.<StaffResourcePool>query().lambda()
                .eq(StaffResourcePool::getIsValid, "1")
                .like(StringUtils.isNoneEmpty(name), StaffResourcePool::getStaffName, name)
                .last("limit " + queryCnt));

    }

    @Override
    public List<StaffResourcePool> selectCustomStaff(String name, String companyId, int queryCnt) {
        return this.staffResourcePoolMapper.selectList(Wrappers.<StaffResourcePool>query().lambda()
                .eq(StaffResourcePool::getIsValid, "1")
                .eq(StaffResourcePool::getCompanyId, companyId)
                .like(StringUtils.isNoneEmpty(name), StaffResourcePool::getStaffName, name)
                .last("limit " + queryCnt));
    }


    @Override
    public IPage<StaffSalaryInfo> selectStaffSalaryInfo(int pageNo, int pageSize, String companyId, String staffId, String projectId, String beginDate, String endDate) {
        Page page = new Page(pageNo, pageSize);
        return this.staffResourcePoolMapper.selectSalary(page, companyId, staffId, projectId, beginDate, endDate);
    }

    @Override
    public StaffResourcePool selectStaffInfo(String staffId) {
        return this.staffResourcePoolMapper.selectOne(Wrappers.<StaffResourcePool>query().lambda()
                .eq(StaffResourcePool::getStaffId, staffId));
    }


}

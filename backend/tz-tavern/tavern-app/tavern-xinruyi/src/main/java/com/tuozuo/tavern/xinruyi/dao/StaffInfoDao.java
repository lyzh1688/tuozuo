package com.tuozuo.tavern.xinruyi.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.xinruyi.model.StaffResourcePool;
import com.tuozuo.tavern.xinruyi.model.StaffSalaryInfo;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/28 <br>
 */
public interface StaffInfoDao {

    IPage<StaffResourcePool> selectStaffList(int pageNo, int pageSize, String companyId);

    void insert(StaffResourcePool pool);

    void update(StaffResourcePool pool);

    void delete(String staffId);

    List<StaffResourcePool> selectByName(String name, String companyId, int queryCnt);

    IPage<StaffSalaryInfo> selectStaffSalaryInfo(int pageNo, int pageSize,
                                                 String companyId,
                                                 String staffId,
                                                 String projectId,
                                                 String beginDate,
                                                 String endDate);
    StaffResourcePool selectStaffInfo(String staffId);
}

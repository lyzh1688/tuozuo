package com.tuozuo.tavern.xinruyi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.xinruyi.model.StaffResourcePool;
import com.tuozuo.tavern.xinruyi.model.StaffSalaryInfo;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/28 <br>
 */
public interface StaffInfoService {

    IPage<StaffResourcePool> queryStaffInfo(int pageNo, int pageSize, String companyId);

    void addStaffInfo(StaffResourcePool pool);

    void modifyStaffInfo(StaffResourcePool pool);

    void removeStaff(String staffId);

    List<StaffResourcePool> queryStaffByName(String name,String companyId,int queryCnt);

    IPage<StaffSalaryInfo> queryStaffSalaryInfo(int pageNo, int pageSize,
                                                 String companyId,
                                                 String staffId,
                                                 String projectId,
                                                 String beginDate,
                                                 String endDate);
    StaffResourcePool queryStaffInfo(String staffId);


    


}

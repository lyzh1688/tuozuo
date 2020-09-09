package com.tuozuo.tavern.xinruyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.xinruyi.model.StaffResourcePool;
import com.tuozuo.tavern.xinruyi.model.StaffSalaryInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StaffResourcePoolMapper extends BaseMapper<StaffResourcePool> {

    IPage<StaffResourcePool> selectByPage(Page page, @Param("companyId") String companyId);

    void delete(@Param("staffId") String staffId);


    IPage<StaffSalaryInfo> selectSalary(Page page,
                                        @Param("companyId") String companyId,
                                        @Param("staffId") String staffId,
                                        @Param("projectId") String projectId,
                                        @Param("beginDate") String beginDate,
                                        @Param("endDate") String endDate);

    StaffResourcePool select(@Param("staffId") String staffId);

}
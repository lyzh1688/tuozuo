package com.tuozuo.tavern.xinruyi.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.xinruyi.model.CompanyEventInfo;
import com.tuozuo.tavern.xinruyi.model.ProjectEventInfo;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/9 <br>
 */
public interface EventTodoListDao {

    IPage<ProjectEventInfo> selectProjects(int pageNo, int pageSize,
                                           String companyId,
                                           String projectId,
                                           String status,
                                           String beginDate,
                                           String endDate);

    IPage<CompanyEventInfo> selectCompanies(int pageNo, int pageSize,
                                            String companyId,
                                            String industryId,
                                            String province,
                                            String city,
                                            String district,
                                            String status,
                                            String beginDate,
                                            String endDate);
}

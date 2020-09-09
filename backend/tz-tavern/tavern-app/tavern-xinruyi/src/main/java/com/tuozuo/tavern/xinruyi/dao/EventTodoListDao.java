package com.tuozuo.tavern.xinruyi.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.xinruyi.model.ProjectEventInfo;
import org.apache.ibatis.annotations.Param;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/9 <br>
 */
public interface EventTodoListDao {

    IPage<ProjectEventInfo> selectProjects(int pageNo,int pageSize,
                                           @Param("companyId")String companyId,
                                           @Param("projectId")String projectId,
                                           @Param("status")String status,
                                           @Param("beginDate")String beginDate,
                                           @Param("endDate")String endDate);
}

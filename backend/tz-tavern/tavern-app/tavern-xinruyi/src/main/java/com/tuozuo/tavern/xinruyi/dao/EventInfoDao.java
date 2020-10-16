package com.tuozuo.tavern.xinruyi.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.xinruyi.model.CompanyEventInfo;
import com.tuozuo.tavern.xinruyi.model.EventFinishList;
import com.tuozuo.tavern.xinruyi.model.EventTodoList;
import com.tuozuo.tavern.xinruyi.model.ProjectEventInfo;

import java.util.List;
import java.util.Optional;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/9 <br>
 */
public interface EventInfoDao {

    IPage<ProjectEventInfo> selectProjects(int pageNo, int pageSize,
                                           String companyId,
                                           String projectId,
                                           String status,
                                           String industryType,
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

    IPage<EventTodoList> selectEventTodoList(int pageNo, int pageSize,
                                             String companyId,
                                             String projectId,
                                             String eventId,
                                             String customType);

    IPage<EventFinishList> selectEventFinishList(int pageNo, int pageSize,
                                                 String companyId,
                                                 String projectId,
                                                 String eventId,
                                                 String customType);

    void insertEventTodo(EventTodoList eventTodoList);

    void insertEventFinish(EventFinishList eventFinishList);

    void updateEventTodoByProject(EventTodoList eventTodoList);

    void delEventTodo(String eventId);

    EventTodoList selectCompanyTodo(String companyId, String eventType);

    EventTodoList selectProjectTodo(String projectId, String eventType);

    EventTodoList selectWorkerTodo(String registerId, String eventType);

    Optional<EventTodoList> selectProjectPaymentTodo(String projectId, String eventType,String paymentId);

    Optional<EventTodoList> selectStaffFiredTodo(String projectId, String staffId, String eventType);

    List<EventTodoList> selectEventTodo(String eventType);

    Optional<EventTodoList> selectCompanyAuthTodo(String companyId, String eventType);

    Optional<EventTodoList> selectProjectFinishTodo(String companyId, String projectId, String eventType);

    Optional<EventTodoList> selectProjectStaffFiredTodo(String companyId, String projectId, String staffId,String eventType);
}

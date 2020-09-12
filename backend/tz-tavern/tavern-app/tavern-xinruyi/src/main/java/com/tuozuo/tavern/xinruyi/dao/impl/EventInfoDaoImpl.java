package com.tuozuo.tavern.xinruyi.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.xinruyi.dao.EventInfoDao;
import com.tuozuo.tavern.xinruyi.mapper.EventFinishListMapper;
import com.tuozuo.tavern.xinruyi.mapper.EventTodoListMapper;
import com.tuozuo.tavern.xinruyi.model.CompanyEventInfo;
import com.tuozuo.tavern.xinruyi.model.EventFinishList;
import com.tuozuo.tavern.xinruyi.model.EventTodoList;
import com.tuozuo.tavern.xinruyi.model.ProjectEventInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/9 <br>
 */
@Repository
public class EventInfoDaoImpl implements EventInfoDao {

    @Autowired
    private EventTodoListMapper eventTodoListMapper;
    @Autowired
    private EventFinishListMapper eventFinishListMapper;

    @Override
    public IPage<ProjectEventInfo> selectProjects(int pageNo, int pageSize, String companyId, String projectId, String status, String beginDate, String endDate) {
        Page page = new Page(pageNo, pageSize);
        return this.eventTodoListMapper.selectProjects(page, companyId, projectId, status, beginDate, endDate);
    }

    @Override
    public IPage<CompanyEventInfo> selectCompanies(int pageNo, int pageSize, String companyId, String industryId, String province, String city, String district, String status, String beginDate, String endDate) {
        Page page = new Page(pageNo, pageSize);
        return this.eventTodoListMapper.selectCompanies(page, companyId, industryId, province, city, district, status, beginDate, endDate);
    }

    @Override
    public IPage<EventTodoList> selectEventTodoList(int pageNo, int pageSize, String companyId, String projectId, String eventId,String customType) {
        Page page = new Page(pageNo, pageSize);
        return this.eventTodoListMapper.selectList(page, companyId, projectId, eventId,customType);
    }

    @Override
    public IPage<EventFinishList> selectEventFinishList(int pageNo, int pageSize, String companyId, String projectId, String eventId,String customType) {
        Page page = new Page(pageNo, pageSize);
        return this.eventFinishListMapper.selectList(page, companyId, projectId, eventId,customType);
    }

    @Override
    public void insertEventTodo(EventTodoList eventTodoList) {
        this.eventTodoListMapper.insert(eventTodoList);
    }

    @Override
    public void insertEventFinish(EventFinishList eventFinishList) {
        this.eventFinishListMapper.insert(eventFinishList);
    }

    @Override
    public void updateEventTodoByProject(EventTodoList eventTodoList) {
        this.eventTodoListMapper.update(eventTodoList, Wrappers.<EventTodoList>query()
                .lambda()
                .eq(EventTodoList::getProjectId, eventTodoList.getProjectId()));
    }

    @Override
    public void delEventTodo(String eventId) {
        this.eventTodoListMapper.deleteById(eventId);
    }

    @Override
    public EventTodoList selectCompanyAuthTodo(String companyId, String eventType) {
        return this.eventTodoListMapper.selectOne(Wrappers.<EventTodoList>query()
                .lambda()
                .eq(EventTodoList::getCompanyId, companyId)
                .eq(EventTodoList::getEventType, eventType));
    }
}

package com.tuozuo.tavern.xinruyi.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

import java.util.List;
import java.util.Optional;

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
    public IPage<ProjectEventInfo> selectProjects(int pageNo, int pageSize, String companyId, String projectId, String status,String industryType, String beginDate, String endDate) {
        Page page = new Page(pageNo, pageSize);
        return this.eventTodoListMapper.selectProjects(page, companyId, projectId, status,industryType, beginDate, endDate);
    }

    @Override
    public IPage<CompanyEventInfo> selectCompanies(int pageNo, int pageSize, String companyId, String industryId, String province, String city, String district, String status, String beginDate, String endDate) {
        Page page = new Page(pageNo, pageSize);
        return this.eventTodoListMapper.selectCompanies(page, companyId, industryId, province, city, district, status, beginDate, endDate);
    }

    @Override
    public IPage<EventTodoList> selectEventTodoList(int pageNo, int pageSize, String companyId, String projectId, String eventId, String customType) {
        Page page = new Page(pageNo, pageSize);
        return this.eventTodoListMapper.selectEventList(page, companyId, projectId, eventId, customType);
    }

    @Override
    public IPage<EventFinishList> selectEventFinishList(int pageNo, int pageSize, String companyId, String projectId, String eventId, String customType) {
        Page page = new Page(pageNo, pageSize);
        return this.eventFinishListMapper.selectEventList(page, companyId, projectId, eventId, customType);
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
                .eq(EventTodoList::getProjectId, eventTodoList.getProjectId())
                .eq(EventTodoList::getEventType, eventTodoList.getEventType()));
    }

    @Override
    public void delEventTodo(String eventId) {
        this.eventTodoListMapper.deleteById(eventId);
    }

    @Override
    public EventTodoList selectCompanyTodo(String companyId, String eventType) {
        return this.eventTodoListMapper.selectOne(Wrappers.<EventTodoList>query()
                .lambda()
                .eq(EventTodoList::getCompanyId, companyId)
                .eq(EventTodoList::getEventType, eventType));
    }

    @Override
    public EventTodoList selectProjectTodo(String projectId, String eventType) {
        return this.eventTodoListMapper.selectOne(Wrappers.<EventTodoList>query()
                .lambda()
                .eq(EventTodoList::getProjectId, projectId)
                .eq(EventTodoList::getEventType, eventType));
    }

    @Override
    public Optional<EventTodoList> selectStaffFiredTodo(String projectId, String staffId, String eventType) {
        return this.eventTodoListMapper.selectList(Wrappers.<EventTodoList>query()
                .lambda()
                .eq(EventTodoList::getProjectId, projectId)
                .eq(EventTodoList::getEventType, eventType))
                .stream()
                .filter(e -> {
                    JSONObject object = JSON.parseObject(e.getSnapshot());
                    if (object.getString("staffId") != null && object.getString("staffId").equals(staffId)) {
                        return true;
                    } else {
                        return false;
                    }
                }).findFirst();
    }

    @Override
    public List<EventTodoList> selectEventTodo(String eventType) {
        return this.eventTodoListMapper.selectByEventType(eventType);
    }
}

package com.tuozuo.tavern.xinruyi.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
        return this.eventTodoListMapper.selectCompanies(page,companyId,industryId,province,city,district,status,beginDate,endDate);
    }

    @Override
    public IPage<EventTodoList> selectEventTodoList(int pageNo, int pageSize, String companyId, String projectId, String eventId) {
        Page page = new Page(pageNo, pageSize);
        return this.eventTodoListMapper.selectList(page,companyId,projectId,eventId);
    }

    @Override
    public IPage<EventFinishList> selectEventFinishList(int pageNo, int pageSize, String companyId, String projectId, String eventId) {
        Page page = new Page(pageNo, pageSize);
        return this.eventFinishListMapper.selectList(page,companyId,projectId,eventId);
    }
}

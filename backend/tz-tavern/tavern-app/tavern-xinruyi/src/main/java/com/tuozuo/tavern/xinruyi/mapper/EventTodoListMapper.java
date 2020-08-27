package com.tuozuo.tavern.xinruyi.mapper;

import com.tuozuo.tavern.xinruyi.model.EventTodoList;
import java.util.List;

public interface EventTodoListMapper {
    int deleteByPrimaryKey(String eventId);

    int insert(EventTodoList record);

    EventTodoList selectByPrimaryKey(String eventId);

    List<EventTodoList> selectAll();

    int updateByPrimaryKey(EventTodoList record);
}
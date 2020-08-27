package com.tuozuo.tavern.xinruyi.mapper;

import com.tuozuo.tavern.xinruyi.model.EventFinishList;
import java.util.List;

public interface EventFinishListMapper {
    int insert(EventFinishList record);

    List<EventFinishList> selectAll();
}
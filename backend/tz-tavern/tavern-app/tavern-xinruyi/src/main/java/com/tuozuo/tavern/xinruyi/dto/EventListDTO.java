package com.tuozuo.tavern.xinruyi.dto;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/12 <br>
 */
public class EventListDTO {
    private List<EventInfoDTO> events = Lists.newArrayList();
    private long total;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<EventInfoDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventInfoDTO> events) {
        this.events = events;
    }
}

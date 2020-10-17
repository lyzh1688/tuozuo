package com.tuozuo.tavern.xinruyi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.xinruyi.dto.EventInfoDTO;
import com.tuozuo.tavern.xinruyi.model.EventFinishList;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/12 <br>
 */
public interface EventInfoService {

    IPage<EventInfoDTO> queryEventList(int pageNo, int pageSize,
                                       String companyId,
                                       String projectId,
                                       String eventId,
                                       String status,
                                       String customType);

    List<EventFinishList> queryEventRecords(String registerId,String eventId,String eventDate);
}

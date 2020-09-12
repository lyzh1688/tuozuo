package com.tuozuo.tavern.xinruyi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.xinruyi.convert.ModelMapConverterFactory;
import com.tuozuo.tavern.xinruyi.dao.EventInfoDao;
import com.tuozuo.tavern.xinruyi.dto.EventInfoDTO;
import com.tuozuo.tavern.xinruyi.model.EventFinishList;
import com.tuozuo.tavern.xinruyi.model.EventTodoList;
import com.tuozuo.tavern.xinruyi.service.EventInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/12 <br>
 */
@Service
public class EventInfoServiceImpl implements EventInfoService {
    @Autowired
    private EventInfoDao eventInfoDao;
    @Autowired
    private ModelMapConverterFactory factory;


    @Override
    public IPage<EventInfoDTO> queryEventList(int pageNo, int pageSize, String companyId, String projectId, String eventId, String status) {

        if (status.equals("0")) {
            IPage<EventTodoList> page = this.eventInfoDao.selectEventTodoList(pageNo, pageSize, companyId, projectId, eventId);
            List<EventInfoDTO> list = page.getRecords().stream()
                    .map(this.factory::modelToEventInfoDTO)
                    .collect(Collectors.toList());
            IPage<EventInfoDTO> dtoIPage = new Page<>();
            dtoIPage.setRecords(list);
            dtoIPage.setTotal(page.getTotal());

            return dtoIPage;

        } else {
            IPage<EventFinishList> page = this.eventInfoDao.selectEventFinishList(pageNo, pageSize, companyId, projectId, eventId);
            List<EventInfoDTO> list = page.getRecords().stream()
                    .map(this.factory::modelToEventInfoDTO)
                    .collect(Collectors.toList());
            IPage<EventInfoDTO> dtoIPage = new Page<>();
            dtoIPage.setRecords(list);
            dtoIPage.setTotal(page.getTotal());

            return dtoIPage;
        }
    }
}

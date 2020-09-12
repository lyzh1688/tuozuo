package com.tuozuo.tavern.xinruyi.endpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernRequestAuthFields;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.xinruyi.dto.EventInfoDTO;
import com.tuozuo.tavern.xinruyi.dto.EventListDTO;
import com.tuozuo.tavern.xinruyi.service.EventInfoService;
import com.tuozuo.tavern.xinruyi.vo.EventVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/12 <br>
 */
@RestController
@RequestMapping("/tuozuo/xinruyi/v1/event")
public class EventInfoEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventInfoEndpoint.class);

    @Autowired
    private EventInfoService eventInfoService;


    /**
     * 事件列表
     */
    @GetMapping("/list")
    public TavernResponse queryEventList(@ModelAttribute @Valid EventVO vo,
                                         @RequestHeader(TavernRequestAuthFields.USER_ID) String companyId,
                                         @RequestHeader(TavernRequestAuthFields.ROLE_GROUP) String customType
    ) {
        try {
            if (customType.equals("staff")) {
                companyId = vo.getCompanyId();
            }
            EventListDTO dto = new EventListDTO();
            IPage<EventInfoDTO> page = this.eventInfoService.queryEventList(vo.getPageNo(), vo.getPageSize(), companyId, vo.getProjectId(), vo.getEventId(), vo.getStatus());
            dto.setEvents(page.getRecords());
            dto.setTotal(page.getTotal());
            return TavernResponse.ok(dto);
        } catch (Exception e) {
            LOGGER.error("[事件列表查询] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }


}

package com.tuozuo.tavern.xinruyi.endpoint;

import com.tuozuo.tavern.common.protocol.TavernRequestAuthFields;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.xinruyi.dto.BusinessDictDTO;
import com.tuozuo.tavern.xinruyi.service.StaffInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/27 <br>
 */
@RestController
@RequestMapping("/tuozuo/xinruyi/v1/staff")
public class StaffInfoEndpoint {
    private static final Logger LOGGER = LoggerFactory

    @Autowired
    private StaffInfoService staffInfoService;

    /**
     * 员工查询
     */
    @GetMapping("/list")
    public TavernResponse queryBusinessDict(@RequestParam(name = "pageNo") int pageNo,
                                            @RequestParam(name = "pageSize") int pageSize,
                                            @RequestHeader(TavernRequestAuthFields.USER_ID) String customId,
                                            @RequestHeader(TavernRequestAuthFields.ROLE_GROUP) String roleGroup) {
        try {


            return TavernResponse.ok(businessDictList);
        } catch (Exception e) {
            LOGGER.error("[业务字典] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }




}

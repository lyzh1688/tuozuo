package com.tuozuo.tavern.shuiruyi.endpoint;

import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.shuiruyi.convert.BusinessConverter;
import com.tuozuo.tavern.shuiruyi.dto.BusinessDictDTO;
import com.tuozuo.tavern.shuiruyi.dto.CompanyDetailDTO;
import com.tuozuo.tavern.shuiruyi.model.BusinessDict;
import com.tuozuo.tavern.shuiruyi.model.CompanyDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.CompanyInfo;
import com.tuozuo.tavern.shuiruyi.service.BusinessDictService;
import com.tuozuo.tavern.shuiruyi.service.CompanyInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
@RestController
@RequestMapping("/shuiruyi/dict")
public class BusinessDictEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessDictEndpoint.class);

    @Autowired
    private BusinessDictService businessDictService;

    /**
     * 业务字典
     */
    @GetMapping("/{group}")
    public TavernResponse queryCompanyDetail(@PathVariable("group") String group) {
        try {
            List<BusinessDictDTO> businessDictList = this.businessDictService.queryBuinessDicts(group)
                    .stream()
                    .map(BusinessConverter::businessDictToDTO)
                    .collect(Collectors.toList());
            return TavernResponse.ok(businessDictList);
        } catch (Exception e) {
            LOGGER.error("[业务字典] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

}

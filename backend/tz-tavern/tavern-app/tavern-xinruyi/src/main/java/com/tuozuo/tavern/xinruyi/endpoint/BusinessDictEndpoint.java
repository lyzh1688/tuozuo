package com.tuozuo.tavern.xinruyi.endpoint;

import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.xinruyi.convert.ModelConverter;
import com.tuozuo.tavern.xinruyi.dto.BusinessDictDTO;
import com.tuozuo.tavern.xinruyi.service.BusinessDictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/29 <br>
 */
@RestController
@RequestMapping("/tuozuo/xinruyi/v1/dict")
public class BusinessDictEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessDictEndpoint.class);

    @Autowired
    private BusinessDictService businessDictService;
    @Autowired
    private ModelConverter converter;

    /**
     * 业务字典
     */
    @GetMapping("/{group}")
    public TavernResponse queryBusinessDict(@PathVariable("group") String group) {
        try {
            List<BusinessDictDTO> businessDictList = this.businessDictService.queryBusinessDicts(group)
                    .stream()
                    .map(this.converter::modelToBusinessDictDTO)
                    .collect(Collectors.toList());
            return TavernResponse.ok(businessDictList);
        } catch (Exception e) {
            LOGGER.error("[业务字典] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }
}

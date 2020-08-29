package com.tuozuo.tavern.shuiruyi.endpoint;

import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.shuiruyi.dto.AreaInfoDTO;
import com.tuozuo.tavern.shuiruyi.service.BusinessSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/1 <br>
 */
@RestController
@RequestMapping("/tuozuo/shuiruyi/v1/search")
public class BusinessSearchEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessSearchEndpoint.class);

    @Autowired
    private BusinessSearchService businessSearchService;


    /**
     * 区域搜索
     */
    @GetMapping("/area")
    public TavernResponse queryArea(@RequestParam("areaLevel") String areaLevel,
                                             @RequestParam(value = "areaCode", required = false) String areaCode) {
        try {
            List<AreaInfoDTO> areaInfoList = this.businessSearchService.queryAreaInfo(areaCode, areaLevel)
                    .stream()
                    .map(areaInfo -> {
                        AreaInfoDTO areaInfoDTO = new AreaInfoDTO();
                        areaInfoDTO.setAreaCode(areaInfo.getAreaCode());
                        areaInfoDTO.setAreaName(areaInfo.getAreaName());
                        return areaInfoDTO;
                    }).collect(Collectors.toList());
            return TavernResponse.ok(areaInfoList);
        } catch (Exception e) {
            LOGGER.error("[业务字典] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

}

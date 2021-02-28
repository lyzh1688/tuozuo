package com.tuozuo.tavern.corp.assist.endpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.corp.assist.dto.CorporationBusinessDictDTO;
import com.tuozuo.tavern.corp.assist.model.BusinessDict;
import com.tuozuo.tavern.corp.assist.service.BusinessDictService;
import com.tuozuo.tavern.corp.assist.vo.CorporationBusinessDictVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/2 <br>
 */
@RestController
@RequestMapping("/tuozuo/corporation/v1/business/dict")
public class CorporationBusinessDictEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(CorporationBusinessDictEndpoint.class);

    @Autowired
    private BusinessDictService businessDictService;

    /**
     * 添加业务字典
     */
    @PostMapping()
    public TavernResponse addBusinessDict(@RequestBody CorporationBusinessDictVO vo) {
        try {
            this.businessDictService.saveDict(vo);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[添加业务字典] failed", e);
            return TavernResponse.bizFailure("[添加业务字典] 异常");
        }
    }

    /**
     * 删除业务字典
     */
    @DeleteMapping("/{dictId}")
    public TavernResponse removeBusinessDict(@PathVariable("dictId") String dictId) {
        try {
            this.businessDictService.delDict(dictId);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[删除业务字典] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 修改业务字典
     */
    @PutMapping("/{dictId}")
    public TavernResponse modifyBusinessDict(@PathVariable("dictId") String dictId,
                                             @RequestBody CorporationBusinessDictVO vo) {
        try {
            vo.setBusinessId(dictId);
            this.businessDictService.modifyDict(vo);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[修改业务字典] failed", e);
            return TavernResponse.bizFailure("[修改业务字典] 异常");
        }
    }


    /**
     * 业务字典列表
     */
    @GetMapping("")
    public TavernResponse queryBusinessDict(@RequestParam(value = "businessName", required = false) String businessName,
                                            @RequestParam(value = "businessGroup", required = false) String businessGroup) {
        try {
            List<BusinessDict> businessDicts = this.businessDictService.queryDicts(businessName, businessGroup);
            return TavernResponse.ok(businessDicts);
        } catch (Exception e) {
            LOGGER.error("[业务字典列表] failed", e);
            return TavernResponse.bizFailure("[业务字典列表] 异常");
        }
    }

}

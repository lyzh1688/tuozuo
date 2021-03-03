package com.tuozuo.tavern.corp.assist.endpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.corp.assist.convert.ModelMapConverterFactory;
import com.tuozuo.tavern.corp.assist.dto.CorporationDTO;
import com.tuozuo.tavern.corp.assist.dto.CorporationFuzzyDTO;
import com.tuozuo.tavern.corp.assist.dto.CorporationInfoDTO;
import com.tuozuo.tavern.corp.assist.model.CorporationInfo;
import com.tuozuo.tavern.corp.assist.service.CorporationInfoService;
import com.tuozuo.tavern.corp.assist.vo.CorporationInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/2/20 <br>
 */
@RestController
@RequestMapping("/tuozuo/corporation/v1/corp/info")
public class CorporationInfoEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(CorporationInfoEndpoint.class);


    @Autowired
    private CorporationInfoService corporationInfoService;

    @Autowired
    ModelMapConverterFactory factory;

    /**
     * 添加公司
     */
    @PostMapping()
    public TavernResponse addCorporationInfo(@RequestBody @Valid CorporationInfoVO vo) {
        try {
            this.corporationInfoService.addCorporation(vo);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[添加公司] failed", e);
            return TavernResponse.bizFailure("添加公司 异常");
        }
    }

    /**
     * 删除公司
     */
    @DeleteMapping("/{corpId}")
    public TavernResponse removeCorporationInfo(@PathVariable("corpId") String corpId) {
        try {
            this.corporationInfoService.delCorporation(corpId);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[删除公司] failed", e);
            return TavernResponse.bizFailure("删除公司 异常");
        }
    }

    /**
     * 修改公司
     */
    @PutMapping("/{corpId}")
    public TavernResponse modifyCorporationInfo(@PathVariable("corpId") String corpId,
                                                @RequestBody @Valid CorporationInfoVO vo) {
        try {
            vo.setCorpId(corpId);
            this.corporationInfoService.modifyCorporation(vo);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[修改公司] failed", e);
            return TavernResponse.bizFailure("修改公司 异常");
        }
    }


    /**
     * 公司列表
     */
    @GetMapping("")
    public TavernResponse queryCorporationInfo(@RequestParam(value = "corpName", required = false) String corpName,
                                               @RequestParam(value = "clientName", required = false) String clientName,
                                               @RequestParam(value = "pageNo") int pageNo,
                                               @RequestParam(value = "pageSize") int pageSize) {
        try {
            IPage<CorporationDTO> page = this.corporationInfoService.queryCorporations(corpName, clientName, pageNo, pageSize);
            List<CorporationDTO> corporationInfos = page.getRecords();
            CorporationInfoDTO corporationClientInfoDTO = new CorporationInfoDTO();
            corporationClientInfoDTO.setCorps(corporationInfos);
            corporationClientInfoDTO.setTotal((int) page.getTotal());
            return TavernResponse.ok(corporationClientInfoDTO);
        } catch (Exception e) {
            LOGGER.error("[公司列表] failed", e);
            return TavernResponse.bizFailure("公司列表 异常");
        }
    }

    /**
     * 公司列表
     */
    @GetMapping("/detail/{corpId}")
    public TavernResponse queryCorporationInfoDetail(@PathVariable("corpId") String corpId) {
        try {
            CorporationDTO corporationInfo = this.corporationInfoService.queryCorporationDetail(corpId);
            return TavernResponse.ok(corporationInfo);
        } catch (Exception e) {
            LOGGER.error("[公司列表] failed", e);
            return TavernResponse.bizFailure("公司列表 异常");
        }
    }

    /**
     * 公司列表Applet
     */
    @GetMapping("/applet")
    public TavernResponse queryCorporationInfoFromApp(@RequestParam(value = "corpName", required = false) String corpName,
                                                      @RequestParam(value = "clientName", required = false) String clientName,
                                                      @RequestParam(value = "corpId", required = false) String corpId,
                                                      @RequestParam(value = "createTime", required = false) String createTime) {
        try {
            List<CorporationDTO> corporationInfos = this.corporationInfoService.queryCorporationsFromApp(corpName, clientName, corpId, createTime);
            return TavernResponse.ok(corporationInfos);
        } catch (Exception e) {
            LOGGER.error("[公司列表] failed", e);
            return TavernResponse.bizFailure("公司列表 异常");
        }
    }

    /**
     * 公司模糊搜索
     */
    @GetMapping("/fuzzy")
    public TavernResponse fuzzyQueryCorporationInfos(@RequestParam(value = "corpName", required = false) String corpName,
                                                     @RequestParam(value = "queryCnt", required = false, defaultValue = "20") String queryCnt) {
        try {
            List<CorporationInfo> corporationInfos = this.corporationInfoService.fuzzyQuery(corpName, Integer.parseInt(queryCnt));
            List<CorporationFuzzyDTO> dtos = this.factory.corpToDTO(corporationInfos);
            return TavernResponse.ok(dtos);
        } catch (Exception e) {
            LOGGER.error("[公司模糊搜索] failed", e);
            return TavernResponse.bizFailure("公司列表 异常");
        }
    }


}

package com.tuozuo.tavern.corp.assist.endpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.corp.assist.dto.CorporationTagInfoDTO;
import com.tuozuo.tavern.corp.assist.model.CorporationTagInfo;
import com.tuozuo.tavern.corp.assist.service.CorporationTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/2 <br>
 */
@RestController
@RequestMapping("/tuozuo/corporation/v1/tag")
public class CorporationTagEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(CorporationTagEndpoint.class);

    @Autowired
    private CorporationTagService corporationTagService;

    /**
     * 添加标签
     */
    @PostMapping()
    public TavernResponse addCorporationTag(@RequestBody Map<String, String> param) {
        try {
            String tagName = param.get("tagName");
            this.corporationTagService.addTag(tagName);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[添加标签] failed", e);
            return TavernResponse.bizFailure("[添加标签] 异常");
        }
    }

    /**
     * 删除标签
     */
    @DeleteMapping("/{tagId}")
    public TavernResponse removeCorporationTag(@PathVariable("tagId") String tagId) {
        try {
            this.corporationTagService.delTag(tagId);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[删除标签] failed", e);
            return TavernResponse.bizFailure("[删除标签] 异常");
        }
    }

    /**
     * 修改标签
     */
    @PutMapping("/{tagId}")
    public TavernResponse modifyCorporationTag(@PathVariable("tagId") String tagId,
                                               @RequestBody Map<String, String> param) {
        try {
            String tagName = param.get("tagName");
            this.corporationTagService.modifyTag(tagId, tagName);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[修改标签] failed", e);
            return TavernResponse.bizFailure("[修改标签] 异常");
        }
    }


    /**
     * 标签列表
     */
    @GetMapping("")
    public TavernResponse queryCorporationTag(@RequestParam(value = "tagName",required = false) String tagName,
                                              @RequestParam(value = "pageNo") int pageNo,
                                              @RequestParam(value = "pageSize") int pageSize) {
        try {
            IPage<CorporationTagInfo> page = this.corporationTagService.queryTags(tagName,pageNo, pageSize);
            List<CorporationTagInfo> corporationTagInfoList = page.getRecords();
            CorporationTagInfoDTO corporationTagInfoDTO = new CorporationTagInfoDTO();
            corporationTagInfoDTO.setTags(corporationTagInfoList);
            corporationTagInfoDTO.setTotal((int) page.getTotal());
            return TavernResponse.ok(corporationTagInfoDTO);
        } catch (Exception e) {
            LOGGER.error("[标签列表] failed", e);
            return TavernResponse.bizFailure("[标签列表] 异常");
        }
    }
    /**
     * 标签列表
     */
    @GetMapping("/applet")
    public TavernResponse queryCorporationTagFromApp(@RequestParam(value = "tagName",required = false) String tagName,
                                              @RequestParam(value = "tagId",required = false) String tagId) {
        try {
            List<CorporationTagInfo> corporationTagInfoList = this.corporationTagService.queryTagsFromApp(tagName,tagId);
            return TavernResponse.ok(corporationTagInfoList);
        } catch (Exception e) {
            LOGGER.error("[标签列表] failed", e);
            return TavernResponse.bizFailure("[标签列表] 异常");
        }
    }


    /**
     * 标签列表模糊查询
     */
    @GetMapping("/info")
    public TavernResponse queryCorporationTags(@RequestParam(value = "tagName",required = false) String tagName,
                                               @RequestParam(value = "queryCnt", defaultValue = "20") String queryCnt) {
        try {
            List<CorporationTagInfo> corporationTagInfoList = this.corporationTagService.queryTagsByName(tagName, Integer.parseInt(queryCnt));
            return TavernResponse.ok(corporationTagInfoList);
        } catch (Exception e) {
            LOGGER.error("[标签列表模糊查询] failed", e);
            return TavernResponse.bizFailure("[标签列表模糊查询] 异常");
        }
    }

}

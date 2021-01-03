package com.tuozuo.tavern.corp.assist.endpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.corp.assist.dto.CorporationGroupInfoDTO;
import com.tuozuo.tavern.corp.assist.model.CorporationGroupClientInfo;
import com.tuozuo.tavern.corp.assist.service.CorporationGroupInfoService;
import com.tuozuo.tavern.corp.assist.vo.CorporationGroupInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/2 <br>
 */
@RestController
@RequestMapping("/tuozuo/corporation/v1/group/")
public class CorporationGroupInfoEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(CorporationGroupInfoEndpoint.class);

    @Autowired
    private CorporationGroupInfoService corporationGroupInfoService;

    /**
     * 添加客户群
     */
    @PostMapping()
    public TavernResponse addCorporationGroup(@RequestBody @Valid CorporationGroupInfoVO vo) {
        try {
            this.corporationGroupInfoService.addGroup(vo);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[添加客户群] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 删除客户群
     */
    @DeleteMapping("/{groupId}")
    public TavernResponse removeCorporationGroup(@PathVariable("groupId") String groupId) {
        try {
            this.corporationGroupInfoService.delGroup(groupId);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[删除客户群] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 修改客户群
     */
    @PutMapping("/{groupId}")
    public TavernResponse modifyCorporationGroup(@PathVariable("groupId") String groupId,
                                               @RequestBody @Valid CorporationGroupInfoVO vo) {
        try {
            vo.setGroupId(groupId);
            this.corporationGroupInfoService.modifyGroup(vo);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[修改客户群] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }


    /**
     * 客户群列表
     */
    @GetMapping("")
    public TavernResponse queryCorporationGroup(@RequestParam(value = "tagName", required = false) String tagName,
                                              @RequestParam(value = "groupName", required = false) String groupName,
                                              @RequestParam(value = "pageNo") int pageNo,
                                              @RequestParam(value = "pageSize") int pageSize) {
        try {
            IPage<CorporationGroupClientInfo> page = this.corporationGroupInfoService.queryGroups(tagName,groupName,pageNo, pageSize);
            List<CorporationGroupClientInfo> corporationGroupInfoList = page.getRecords();
            CorporationGroupInfoDTO corporationGroupInfoDTO = new CorporationGroupInfoDTO();
            corporationGroupInfoDTO.setGroups(corporationGroupInfoList);
            corporationGroupInfoDTO.setTotal((int) page.getTotal());
            return TavernResponse.ok(corporationGroupInfoDTO);
        } catch (Exception e) {
            LOGGER.error("[客户群列表] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }


}

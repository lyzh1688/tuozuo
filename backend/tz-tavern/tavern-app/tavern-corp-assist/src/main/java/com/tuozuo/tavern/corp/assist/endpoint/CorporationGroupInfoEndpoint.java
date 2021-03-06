package com.tuozuo.tavern.corp.assist.endpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.corp.assist.dto.CorporationGroupClientDTO;
import com.tuozuo.tavern.corp.assist.dto.CorporationGroupInfoDTO;
import com.tuozuo.tavern.corp.assist.model.CorporationGroupClientInfo;
import com.tuozuo.tavern.corp.assist.service.CorporationGroupInfoService;
import com.tuozuo.tavern.corp.assist.vo.CorporationGroupClientVO;
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
@RequestMapping("/tuozuo/corporation/v1/group")
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
            return TavernResponse.bizFailure("[添加客户群] 异常");
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
            return TavernResponse.bizFailure("[修改客户群] 异常");
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
            IPage<CorporationGroupClientInfo> page = this.corporationGroupInfoService.queryGroups(tagName, groupName, pageNo, pageSize);
            List<CorporationGroupClientInfo> corporationGroupInfoList = page.getRecords();
            CorporationGroupInfoDTO corporationGroupInfoDTO = new CorporationGroupInfoDTO();
            corporationGroupInfoDTO.setGroups(corporationGroupInfoList);
            corporationGroupInfoDTO.setTotal((int) page.getTotal());
            return TavernResponse.ok(corporationGroupInfoDTO);
        } catch (Exception e) {
            LOGGER.error("[客户群列表] failed", e);
            return TavernResponse.bizFailure("[客户群列表] 异常");
        }
    }

    /**
     * 客户群列表Applet
     */
    @GetMapping("/applet")
    public TavernResponse queryCorporationGroupFromApp(@RequestParam(value = "tagName", required = false) String tagName,
                                                       @RequestParam(value = "groupName", required = false) String groupName,
                                                       @RequestParam(value = "groupId", required = false) String groupId,
                                                       @RequestParam(value = "createTime", required = false) String createTime) {
        try {
            List<CorporationGroupClientInfo> corporationGroupClientInfos = this.corporationGroupInfoService.queryGroupsFromApp(tagName, groupName, groupId, createTime);
            return TavernResponse.ok(corporationGroupClientInfos);
        } catch (Exception e) {
            LOGGER.error("[客户群列表] failed", e);
            return TavernResponse.bizFailure("[客户群列表] 异常");
        }
    }

    /**
     * 客户群详情
     */
    @GetMapping("/detail/{groupId}")
    public TavernResponse queryCorporationGroupDetail(@PathVariable("groupId") String groupId) {
        try {
            CorporationGroupClientInfo corporationGroupClientInfo = this.corporationGroupInfoService.queryGroupDetail(groupId);
            return TavernResponse.ok(corporationGroupClientInfo);
        } catch (Exception e) {
            LOGGER.error("[客户群详情] failed", e);
            return TavernResponse.bizFailure("[客户群详情] 异常");
        }
    }


    /**
     * 绑定客户群
     */
    @PostMapping("/client/relation")
    public TavernResponse bindCorporationGroup(@RequestBody @Valid CorporationGroupClientVO vo) {
        try {
            this.corporationGroupInfoService.bindGroupClientRel(vo.getGroupId(), vo.getClients());
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[绑定客户群] failed", e);
            return TavernResponse.bizFailure("[绑定客户群] 异常");
        }
    }

    /**
     * 查询微信群客户详情
     */
    @GetMapping("/wechat/detail/{chatId}")
    public TavernResponse queryCorporationGroupClientDetail(@PathVariable("chatId") String chatId) {
        try {
            CorporationGroupClientDTO corporationGroupClientDTO = this.corporationGroupInfoService.queryCorporationGroupClient(chatId);
            return TavernResponse.ok(corporationGroupClientDTO);
        } catch (Exception e) {
            LOGGER.error("[查询微信群客户详情] failed", e);
            return TavernResponse.bizFailure("[查询微信群客户详情] 异常");
        }
    }


}

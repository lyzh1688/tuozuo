package com.tuozuo.tavern.corp.assist.endpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.corp.assist.dto.CorporationClientInfoDTO;
import com.tuozuo.tavern.corp.assist.model.CorporationClientTagInfo;
import com.tuozuo.tavern.corp.assist.service.CorporationClientInfoService;
import com.tuozuo.tavern.corp.assist.vo.CorporationClientTagVO;
import com.tuozuo.tavern.corp.assist.vo.CorporationClientVO;
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
@RequestMapping("/tuozuo/corporation/v1/client")
public class CorporationClientInfoEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(CorporationClientInfoEndpoint.class);

    @Autowired
    private CorporationClientInfoService corporationClientInfoService;

    /**
     * 添加客户
     */
    @PostMapping()
    public TavernResponse addCorporationClient(@RequestBody @Valid CorporationClientVO vo) {
        try {
            this.corporationClientInfoService.addClient(vo);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[添加客户] failed", e);
            return TavernResponse.bizFailure("添加客户 异常");
        }
    }

    /**
     * 删除客户
     */
    @DeleteMapping("/{clientId}")
    public TavernResponse removeCorporationClient(@PathVariable("clientId") String clientId) {
        try {
            this.corporationClientInfoService.delClient(clientId);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[删除客户] failed", e);
            return TavernResponse.bizFailure("删除客户 异常");
        }
    }

    /**
     * 修改客户
     */
    @PutMapping("/{clientId}")
    public TavernResponse modifyCorporationClient(@PathVariable("clientId") String clientId,
                                                  @RequestBody @Valid CorporationClientVO vo) {
        try {
            vo.setClientId(clientId);
            this.corporationClientInfoService.modifyClient(vo);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[修改客户] failed", e);
            return TavernResponse.bizFailure("修改客户 异常");
        }
    }


    /**
     * 客户列表
     */
    @GetMapping("")
    public TavernResponse queryCorporationClient(@RequestParam(value = "tagName", required = false) String tagName,
                                                 @RequestParam(value = "clientName", required = false) String clientName,
                                                 @RequestParam(value = "pageNo") int pageNo,
                                                 @RequestParam(value = "pageSize") int pageSize) {
        try {
            IPage<CorporationClientTagInfo> page = this.corporationClientInfoService.queryClients(tagName, clientName, pageNo, pageSize);
            List<CorporationClientTagInfo> corporationClientInfoList = page.getRecords();
            CorporationClientInfoDTO corporationClientInfoDTO = new CorporationClientInfoDTO();
            corporationClientInfoDTO.setClients(corporationClientInfoList);
            corporationClientInfoDTO.setTotal((int) page.getTotal());
            return TavernResponse.ok(corporationClientInfoDTO);
        } catch (Exception e) {
            LOGGER.error("[客户列表] failed", e);
            return TavernResponse.bizFailure("客户列表 异常");
        }
    }

    /**
     * 客户列表Applet
     */
    @GetMapping("/applet")
    public TavernResponse queryCorporationClientFromApp(@RequestParam(value = "tagName", required = false) String tagName,
                                                        @RequestParam(value = "clientName", required = false) String clientName,
                                                        @RequestParam(value = "clientId", required = false) String clientId,
                                                        @RequestParam(value = "createTime", required = false) String createTime) {
        try {
            List<CorporationClientTagInfo> corporationClientTagInfos = this.corporationClientInfoService.queryClientsFromApp(tagName, clientName, clientId, createTime);
            return TavernResponse.ok(corporationClientTagInfos);
        } catch (Exception e) {
            LOGGER.error("[客户列表] failed", e);
            return TavernResponse.bizFailure("客户列表 异常");
        }
    }

    /**
     * 绑定客户标签
     */
    @PostMapping("/tag/relation")
    public TavernResponse bindCorporationClientTag(@RequestBody @Valid CorporationClientTagVO vo) {
        try {
            this.corporationClientInfoService.bindClientTag(vo.getClientId(), vo.getTags());
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[绑定客户标签] failed", e);
            return TavernResponse.bizFailure("绑定客户标签 异常");
        }
    }

}

package com.tuozuo.tavern.shuiruyi.endpoint;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.shuiruyi.convert.BusinessConverter;
import com.tuozuo.tavern.shuiruyi.dto.*;
import com.tuozuo.tavern.shuiruyi.model.CustomDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.CustomInfo;
import com.tuozuo.tavern.shuiruyi.model.CustomTradeFlow;
import com.tuozuo.tavern.shuiruyi.service.CustomInfoService;
import com.tuozuo.tavern.shuiruyi.vo.CustomAddInfoVO;
import com.tuozuo.tavern.shuiruyi.vo.CustomInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
@RestController
@RequestMapping("/tuozuo/shuiruyi/v1/custom")
public class CustomInfoEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomInfoEndpoint.class);

    @Autowired
    private CustomInfoService customInfoService;


    /**
     * 客户详情信息
     */
    @GetMapping("/detail/{customId}")
    public TavernResponse queryCustomDetail(@PathVariable("customId") String customId) {
        try {
            CustomDetailInfo customDetailInfo = this.customInfoService.queryCustomInfo(customId);
            if (customDetailInfo != null) {
                CustomInfoDetailDTO customInfoDetailDTO = BusinessConverter.customDetailToDTO(customDetailInfo);
                return TavernResponse.ok(customInfoDetailDTO);
            } else {
                return TavernResponse.bizFailure("该客户不存在");
            }
        } catch (Exception e) {
            LOGGER.error("[客户详情信息] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 我的个人及充值费用等信息
     */
    @GetMapping("/tradeflow/{customId}")
    public TavernResponse queryCustomTradeFlow(@PathVariable("customId") String customId,
                                               @RequestParam(value = "pageNo") int pageNo,
                                               @RequestParam(value = "pageSize") int pageSize) {
        try {
            IPage<CustomTradeFlow> page = this.customInfoService.queryCustomTradeFlowList(customId, pageNo, pageSize);
            List<CustomTradeFlowDTO> customTradeFlowDTO = page.getRecords().stream()
                    .map(BusinessConverter::customTradeFlowToDTO)
                    .collect(Collectors.toList());
            CustomTradeFlowListDTO customTradeFlowListDTO = new CustomTradeFlowListDTO();
            customTradeFlowListDTO.setTotal((int) page.getTotal());
            customTradeFlowListDTO.setTradeflow(customTradeFlowDTO);
            return TavernResponse.ok(customTradeFlowListDTO);
        } catch (Exception e) {
            LOGGER.error("[我的个人及充值费用等信息] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }


    /**
     * 客户的模糊搜索
     */
    @GetMapping()
    public TavernResponse queryCompanyDict(@RequestParam(name = "customName", required = false, defaultValue = "") String customName,
                                           @RequestParam(name = "queryCnt", required = false, defaultValue = "20") int queryCnt) {
        try {
            List<CustomInfo> customInfoList = this.customInfoService.fuzzyQueryCustomInfo(customName, queryCnt);
            List<BusinessDictDTO> businessDictList = customInfoList.stream()
                    .map(BusinessConverter::customInfoToDictDTO)
                    .collect(Collectors.toList());

            return TavernResponse.ok(businessDictList);
        } catch (Exception e) {
            LOGGER.error("[客户的模糊搜索] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 创建用户
     */
    @PostMapping()
    public TavernResponse addCustomInfo(@RequestBody @Valid CustomAddInfoVO vo) {
        try {
            this.customInfoService.addCustomInfo(vo);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[创建用户] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 修改用户
     */
    @PutMapping("/{customId}")
    public TavernResponse modifyCustomInfo(@PathVariable("customId") String customId,
                                           @RequestBody @Valid CustomInfoVO vo) {
        try {
            this.customInfoService.modifyCustomInfo(vo, customId);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[修改用户] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }


    /**
     * 我的客户列表
     */
    @GetMapping("/list")
    public TavernResponse queryCustomList(@RequestParam(name = "customName", required = false, defaultValue = "") String customName,
                                          @RequestParam(name = "hasPaid", required = false, defaultValue = "") String hasPaid,
                                          @RequestParam(value = "pageNo") int pageNo,
                                          @RequestParam(value = "pageSize") int pageSize) {
        try {
            IPage<CustomInfo> page = this.customInfoService.queryCustomInfoList(customName, hasPaid, pageNo, pageSize);
            List<CustomInfoDTO> customInfoDTOList = page.getRecords()
                    .stream()
                    .map(BusinessConverter::customInfoToDTO)
                    .collect(Collectors.toList());
            CustomInfoListDTO customInfoListDTO = new CustomInfoListDTO();
            customInfoListDTO.setCustomers(customInfoDTOList);
            customInfoListDTO.setTotal((int) page.getTotal());
            return TavernResponse.ok(customInfoListDTO);
        } catch (Exception e) {
            LOGGER.error("[我的客户列表] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 充值扣款
     */
    @PostMapping("/trade/{customId}")
    public TavernResponse investAndPayment(@PathVariable("customId") String customId,
                                           @RequestParam("customType") String customType,
                                           @RequestParam("event") String event,
                                           @RequestParam("amount") double amount,
                                           @RequestParam("tradeSnapshot") MultipartFile tradeSnapshot) {
        try {
            String tradeFlowId = this.customInfoService.investAndPayment(customId, customType, event, amount, tradeSnapshot);
            JSONObject response = new JSONObject();
            response.put("tradeFlowId", tradeFlowId);
            return TavernResponse.ok(response);
        } catch (Exception e) {
            LOGGER.error("[充值扣款] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

}

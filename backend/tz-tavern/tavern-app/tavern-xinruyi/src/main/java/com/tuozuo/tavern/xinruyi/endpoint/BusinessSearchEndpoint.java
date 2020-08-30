package com.tuozuo.tavern.xinruyi.endpoint;

import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.xinruyi.dto.AreaInfoDTO;
import com.tuozuo.tavern.xinruyi.dto.BankInfoDTO;
import com.tuozuo.tavern.xinruyi.service.BusinessSearchService;
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
@RequestMapping("/tuozuo/xinruyi/v1/search")
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
            LOGGER.error("[区域搜索] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 银行搜索
     */
    @GetMapping("/bank")
    public TavernResponse queryBank(@RequestParam(value = "bankCode",required = false) String bankCode) {
        try {
            List<BankInfoDTO> bankInfoDTOList = this.businessSearchService.queryBankInfo(bankCode)
                    .stream()
                    .map(bankInfo -> {
                        BankInfoDTO bankInfoDTO = new BankInfoDTO();
                        bankInfoDTO.setBankCode(bankInfo.getBankCode());
                        bankInfoDTO.setBankName(bankInfo.getBankName());
                        return bankInfoDTO;
                    }).collect(Collectors.toList());
            return TavernResponse.ok(bankInfoDTOList);
        } catch (Exception e) {
            LOGGER.error("[银行搜索] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

}

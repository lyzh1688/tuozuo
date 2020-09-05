package com.tuozuo.tavern.xinruyi.endpoint;

import com.tuozuo.tavern.common.protocol.TavernRequestAuthFields;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.xinruyi.convert.ModelMapConverterFactory;
import com.tuozuo.tavern.xinruyi.dto.CompanyInfoDTO;
import com.tuozuo.tavern.xinruyi.model.CompanyInfo;
import com.tuozuo.tavern.xinruyi.service.CompanyInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/5 <br>
 */
@RestController
@RequestMapping("/tuozuo/xinruyi/v1/company")
public class CompanyInfoEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyInfoEndpoint.class);

    @Autowired
    private CompanyInfoService companyInfoService;
    @Autowired
    private ModelMapConverterFactory factory;

    /**
     * 我的企业信息
     */
    @GetMapping("/profile")
    public TavernResponse queryCompanyProfile(@RequestHeader(TavernRequestAuthFields.USER_ID) String companyId) {
        try {
            CompanyInfo companyInfo = this.companyInfoService.queryCompanyInfo(companyId);
            CompanyInfoDTO dto = factory.modelToCompanyInfoDTO(companyInfo);
            return TavernResponse.ok(dto);
        } catch (Exception e) {
            LOGGER.error("[我的企业信息] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

}

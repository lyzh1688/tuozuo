package com.tuozuo.tavern.xinruyi.endpoint;

import com.tuozuo.tavern.common.protocol.TavernRequestAuthFields;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.xinruyi.convert.ModelMapConverterFactory;
import com.tuozuo.tavern.xinruyi.dto.BusinessDictDTO;
import com.tuozuo.tavern.xinruyi.dto.CompanyDetailInfoDTO;
import com.tuozuo.tavern.xinruyi.dto.CompanyInfoDTO;
import com.tuozuo.tavern.xinruyi.model.CompanyInfo;
import com.tuozuo.tavern.xinruyi.model.CompanyInfoExt;
import com.tuozuo.tavern.xinruyi.service.CompanyInfoService;
import com.tuozuo.tavern.xinruyi.vo.CompanyAuthInfoVO;
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

    /**
     * 企业认证申请
     */
    @PostMapping("/authentication")
    public TavernResponse companyAuthentication(@ModelAttribute @Valid CompanyAuthInfoVO vo,
                                                @RequestHeader(TavernRequestAuthFields.USER_ID) String companyId,
                                                @RequestParam(name = "businessLicense") MultipartFile businessLicense,
                                                @RequestParam(name = "bossIdPicUp") MultipartFile bossIdPicUp,
                                                @RequestParam(name = "bossIdPicBack") MultipartFile bossIdPicBack
    ) {
        try {
            this.setCompanyAuthInfo(vo, companyId, businessLicense, bossIdPicUp, bossIdPicBack);
            this.companyInfoService.applyForCompanyAuth(vo);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[企业认证申请] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 我的企业详情
     */
    @GetMapping("/detail/{companyId}")
    public TavernResponse queryCompanyDetail(@PathVariable("companyId") String companyId) {
        try {
            CompanyInfoExt companyInfo = this.companyInfoService.queryCompanyDetailInfo(companyId);
            CompanyDetailInfoDTO dto = factory.modelToCompanyDetailInfoDTO(companyInfo);
            return TavernResponse.ok(dto);
        } catch (Exception e) {
            LOGGER.error("[我的企业详情] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }


    /**
     * 企业模糊查询
     */
    @GetMapping("")
    public TavernResponse queryCompanyList(@RequestParam(name = "companyName") String companyName,
                                           @RequestParam(name = "queryCnt", defaultValue = "20") int queryCnt) {
        try {
            List<BusinessDictDTO> businessDictList = this.companyInfoService.queryCompanyList(companyName, queryCnt)
                    .stream()
                    .map(companyInfo -> {
                        BusinessDictDTO dict = new BusinessDictDTO();
                        dict.setId(companyInfo.getCompanyId());
                        dict.setName(companyInfo.getCompanyName());
                        return dict;
                    }).collect(Collectors.toList());
            return TavernResponse.ok(businessDictList);
        } catch (Exception e) {
            LOGGER.error("[企业模糊查询] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    private CompanyAuthInfoVO setCompanyAuthInfo(CompanyAuthInfoVO vo,
                                                 String companyId,
                                                 MultipartFile businessLicense,
                                                 MultipartFile bossIdPicUp,
                                                 MultipartFile bossIdPicBack) {
        vo.setCompanyId(companyId);
        vo.setBusinessLicense(businessLicense);
        vo.setBossIdPicUp(bossIdPicUp);
        vo.setBossIdPicBack(bossIdPicBack);
        return vo;
    }
}

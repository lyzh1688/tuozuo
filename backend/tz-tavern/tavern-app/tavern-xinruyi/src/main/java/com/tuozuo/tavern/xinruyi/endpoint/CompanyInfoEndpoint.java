package com.tuozuo.tavern.xinruyi.endpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernRequestAuthFields;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.xinruyi.convert.ModelMapConverterFactory;
import com.tuozuo.tavern.xinruyi.dto.BusinessDictDTO;
import com.tuozuo.tavern.xinruyi.dto.CompanyDetailInfoDTO;
import com.tuozuo.tavern.xinruyi.dto.CompanyEventListDTO;
import com.tuozuo.tavern.xinruyi.dto.CompanyInfoDTO;
import com.tuozuo.tavern.xinruyi.model.CompanyEventInfo;
import com.tuozuo.tavern.xinruyi.model.CompanyInfo;
import com.tuozuo.tavern.xinruyi.model.CompanyInfoExt;
import com.tuozuo.tavern.xinruyi.service.CompanyInfoService;
import com.tuozuo.tavern.xinruyi.vo.*;
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
     * 企业入驻申请
     */
    @PostMapping("/cooperation")
    public TavernResponse companyCooperation(@RequestBody @Valid CompanyApplyVO vo) {
        try {
            this.companyInfoService.companyApply(vo);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[企业入驻申请] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

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
    @PostMapping("/identification")
    public TavernResponse companyAuthentication(@ModelAttribute @Valid CompanyAuthInfoVO vo,
                                                @RequestParam(name = "businessLicense") MultipartFile businessLicense,
                                                @RequestParam(name = "bossIdPicUp") MultipartFile bossIdPicUp,
                                                @RequestParam(name = "bossIdPicBack") MultipartFile bossIdPicBack,
                                                @RequestHeader(TavernRequestAuthFields.USER_ID) String companyId
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
     * 企业信息修改
     */
    @PutMapping("")
    public TavernResponse modifyCompanyInfo(@ModelAttribute CompanyAuthInfoVO vo,
                                            @RequestHeader(TavernRequestAuthFields.USER_ID) String companyId,
                                            @RequestParam(name = "businessLicense", required = false) MultipartFile businessLicense,
                                            @RequestParam(name = "bossIdPicUp", required = false) MultipartFile bossIdPicUp,
                                            @RequestParam(name = "bossIdPicBack", required = false) MultipartFile bossIdPicBack
    ) {
        try {
            this.setCompanyAuthInfo(vo, companyId, businessLicense, bossIdPicUp, bossIdPicBack);
            this.companyInfoService.modifyCompanyInfo(vo);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[企业信息修改] failed", e);
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

    /**
     * 公司事件列表
     */
    @GetMapping("/event/list")
    public TavernResponse queryCompanyEventList(@ModelAttribute @Valid CompanyEventVO vo) {
        try {
            IPage<CompanyEventInfo> page = this.companyInfoService.queryCompanyEvents(vo);
            CompanyEventListDTO dto = new CompanyEventListDTO();
            dto.setCompanies(page.getRecords());
            dto.setTotal((int) page.getTotal());

            return TavernResponse.ok(dto);
        } catch (Exception e) {
            LOGGER.error("[公司事件列表] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }


    /**
     * 企业认证审核
     */
    @PutMapping("/status/{companyId}")
    public TavernResponse auditCompanyAuth(@PathVariable("companyId") String companyId,
                                           @RequestBody @Valid AuditCompanyAuthVO vo) {
        try {
            this.companyInfoService.auditCompanyAuth(companyId, vo.getStatus(), vo.getRemark());
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[企业认证审核] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 企业入驻审核
     */
    @PutMapping("/spot")
    public TavernResponse auditCompanySettled(@RequestBody @Valid AuditCompanySettledVO vo) {
        try {
            this.companyInfoService.auditCompanySettled(vo);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[企业入驻审核] failed", e);
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

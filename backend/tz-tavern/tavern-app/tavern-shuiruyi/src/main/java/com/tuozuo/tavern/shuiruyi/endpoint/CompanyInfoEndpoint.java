package com.tuozuo.tavern.shuiruyi.endpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernRequestAuthFields;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.shuiruyi.convert.BusinessConverter;
import com.tuozuo.tavern.shuiruyi.dto.BusinessDictDTO;
import com.tuozuo.tavern.shuiruyi.dto.CompanyBriefInfo;
import com.tuozuo.tavern.shuiruyi.dto.CompanyDetailDTO;
import com.tuozuo.tavern.shuiruyi.dto.CompanyInfoListDTO;
import com.tuozuo.tavern.shuiruyi.model.CompanyDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.CompanyInfo;
import com.tuozuo.tavern.shuiruyi.service.CompanyInfoService;
import com.tuozuo.tavern.shuiruyi.vo.CompanyDetailVO;
import com.tuozuo.tavern.shuiruyi.vo.CompanyModifyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/08/10 <br>
 */
@RestController
@RequestMapping("/tuozuo/shuiruyi/v1/company")
public class CompanyInfoEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyInfoEndpoint.class);

    @Autowired
    private CompanyInfoService companyInfoService;

    /**
     * 个独公司模糊查询
     */
    @GetMapping()
    public TavernResponse queryCompanyDict(@RequestParam(name = "companyName", required = false, defaultValue = "") String companyName,
                                           @RequestParam(name = "queryCnt", required = false, defaultValue = "20") int queryCnt,
                                           @RequestParam(name = "showAll", required = false) boolean showAll,
                                           @RequestHeader(TavernRequestAuthFields.USER_ID) String userId,
                                           @RequestHeader(TavernRequestAuthFields.ROLE_GROUP) String roleGroup) {
        try {
            List<CompanyInfo> companyInfoList = this.companyInfoService.fuzzyQueryCompany(companyName, queryCnt, showAll, userId, roleGroup);
            List<BusinessDictDTO> businessDictList = companyInfoList.stream()
                    .map(BusinessConverter::companyInfoToDTO)
                    .collect(Collectors.toList());

            return TavernResponse.ok(businessDictList);
        } catch (Exception e) {
            LOGGER.error("[我的个独公司模糊查询] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 我的个独公司详情
     */
    @GetMapping("/detail/{companyId}")
    public TavernResponse queryCompanyDetail(@PathVariable("companyId") String companyId) {
        try {
            CompanyDetailInfo companyDetailInfo = this.companyInfoService.queryCompanyDetail(companyId);
            if (Objects.isNull(companyDetailInfo)) {
                return TavernResponse.bizFailure("个独公司不存在");
            }
            CompanyDetailDTO companyDetailDTO = BusinessConverter.companyDetailToDTO(companyDetailInfo);
            return TavernResponse.ok(companyDetailDTO);
        } catch (Exception e) {
            LOGGER.error("[我的个独公司详情] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 个独公司列表
     */
    @GetMapping("/list")
    public TavernResponse queryCompanyDict(@RequestParam(name = "companyId", defaultValue = "") String companyId,
                                           @RequestParam(name = "companyStatus", defaultValue = "") String companyStatus,
                                           @RequestParam(name = "registerStatus", defaultValue = "") String registerStatus,
                                           @RequestParam(name = "pageNo") int pageNo,
                                           @RequestParam(name = "pageSize") int pageSize,
                                           @RequestHeader(TavernRequestAuthFields.USER_ID) String customId,
                                           @RequestHeader(TavernRequestAuthFields.ROLE_GROUP) String roleGroup) {
        try {
            IPage<CompanyInfo> page = this.companyInfoService.queryCompanyList(companyId,customId, roleGroup, companyStatus, registerStatus, pageNo, pageSize);
            CompanyInfoListDTO companyInfoListDTO = new CompanyInfoListDTO();

            List<CompanyBriefInfo> companyBriefInfoList = page.getRecords().stream()
                    .map(BusinessConverter::companyInfoToBriefDTO)
                    .collect(Collectors.toList());
            companyInfoListDTO.setCompanies(companyBriefInfoList);
            companyInfoListDTO.setTotal((int) page.getTotal());

            return TavernResponse.ok(companyInfoListDTO);
        } catch (Exception e) {
            LOGGER.error("[个独公司列表] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 创建公司
     */
    @PostMapping("")
    public TavernResponse addCompanyInfo(@ModelAttribute @Valid CompanyDetailVO vo,
                                         @RequestHeader(TavernRequestAuthFields.USER_ID) String customId,
                                         @RequestParam(name = "bossIdPicUp") MultipartFile bossIdPicUp,
                                         @RequestParam(name = "bossIdPicBack") MultipartFile bossIdPicBack,
                                         @RequestParam(name = "cfoIdPicUp") MultipartFile cfoIdPicUp,
                                         @RequestParam(name = "cfoIdPicBack") MultipartFile cfoIdPicBack
    ) {
        try {
            this.setCompanyDetailInfo(vo, customId, bossIdPicUp, bossIdPicBack, cfoIdPicUp, cfoIdPicBack);
            companyInfoService.addCompanyInfo(vo);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[创建公司] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 修改公司
     */
    @PutMapping(value = "/{companyId}")
    public TavernResponse modifyCompanyInfo(@PathVariable("companyId") String companyId,
                                            @ModelAttribute @Valid CompanyDetailVO vo,
                                            @RequestHeader(TavernRequestAuthFields.USER_ID) String customId,
                                            @RequestParam(name = "bossIdPicUp", required = false) MultipartFile bossIdPicUp,
                                            @RequestParam(name = "bossIdPicBack", required = false) MultipartFile bossIdPicBack,
                                            @RequestParam(name = "cfoIdPicUp", required = false) MultipartFile cfoIdPicUp,
                                            @RequestParam(name = "cfoIdPicBack", required = false) MultipartFile cfoIdPicBack) {
        try {
            LOGGER.info("modifyCompanyInfo: companyId: {},CompanyDetailVO: {},customId: {}", companyId, vo.toString(), companyId);
            if (bossIdPicUp != null) {
                LOGGER.info("bossIdPicUp: {}", bossIdPicUp.getOriginalFilename());
            }
            if (bossIdPicBack != null) {
                LOGGER.info("bossIdPicBack: {}", bossIdPicBack.getOriginalFilename());

            }
            if (cfoIdPicUp != null) {
                LOGGER.info("cfoIdPicUp: {}", cfoIdPicUp.getOriginalFilename());
            }
            if (cfoIdPicBack != null) {
                LOGGER.info("cfoIdPicBack: {}", cfoIdPicBack.getOriginalFilename());
            }
            this.setCompanyDetailInfo(vo, customId, bossIdPicUp, bossIdPicBack, cfoIdPicUp, cfoIdPicBack);
            this.companyInfoService.modifyCompanyInfo(vo, companyId);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[修改公司] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 管理员修改公司
     */
    @PutMapping(value = "/management/{companyId}")
    public TavernResponse manageCompanyInfo(@PathVariable("companyId") String companyId,
                                            @RequestBody @Valid CompanyModifyVO vo) {
        try {
            vo.setCompanyId(companyId);
            this.companyInfoService.modifyCompanyInfo(vo);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[修改公司] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    private void setCompanyDetailInfo(CompanyDetailVO vo,
                                      String customId,
                                      MultipartFile bossIdPicUp,
                                      MultipartFile bossIdPicBack,
                                      MultipartFile cfoIdPicUp,
                                      MultipartFile cfoIdPicBack) {
        vo.setCustomId(customId);
        vo.setBossIdPicUp(bossIdPicUp);
        vo.setBossIdPicBack(bossIdPicBack);
        vo.setCfoIdPicUp(cfoIdPicUp);
        vo.setCfoIdPicBack(cfoIdPicBack);
    }
}

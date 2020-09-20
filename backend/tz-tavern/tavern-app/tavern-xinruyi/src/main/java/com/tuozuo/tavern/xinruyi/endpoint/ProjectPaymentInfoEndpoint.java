package com.tuozuo.tavern.xinruyi.endpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernRequestAuthFields;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.common.protocol.UserTypeDict;
import com.tuozuo.tavern.xinruyi.convert.ModelConverterFactory;
import com.tuozuo.tavern.xinruyi.convert.ModelMapConverterFactory;
import com.tuozuo.tavern.xinruyi.dto.*;
import com.tuozuo.tavern.xinruyi.model.ProjectPayment;
import com.tuozuo.tavern.xinruyi.model.StaffSalaryInfo;
import com.tuozuo.tavern.xinruyi.service.PaymentInfoService;
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
 * Dev Time: 2020/9/19 <br>
 */
@RestController
@RequestMapping("/tuozuo/xinruyi/v1/fund")
public class ProjectPaymentInfoEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectInfoEndpoint.class);

    @Autowired
    private PaymentInfoService paymentInfoService;

    @Autowired
    private ModelMapConverterFactory converterFactory;

    /**
     * 资金列表
     */
    @GetMapping("/list")
    public TavernResponse queryProjectPayment(@ModelAttribute @Valid PaymentListVO vo,
                                              @RequestHeader(TavernRequestAuthFields.USER_ID) String companyId,
                                              @RequestHeader(TavernRequestAuthFields.ROLE_GROUP) String roleGroup) {
        try {
            if (roleGroup.equals(UserTypeDict.custom)) {
                vo.setCompanyId(companyId);
            }
            IPage<ProjectPayment> page = this.paymentInfoService.queryProjectPaymentList(vo);
            List<PaymentDTO> projectPayments = page.getRecords()
                    .stream()
                    .map(ModelConverterFactory::modelToPaymentDTO)
                    .collect(Collectors.toList());
            PaymentListDTO dto = new PaymentListDTO();
            dto.setFunds(projectPayments);
            dto.setTotal(page.getTotal());
            return TavernResponse.ok(dto);
        } catch (Exception e) {
            LOGGER.error("[资金列表] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 上传凭证
     */
    @PostMapping("/voucher")
    public TavernResponse uploadPaymentVoucher(@ModelAttribute @Valid PaymentVoucherUploadVO vo,
                                               @RequestHeader(TavernRequestAuthFields.USER_ID) String companyId,
                                               @RequestHeader(TavernRequestAuthFields.ROLE_GROUP) String roleGroup,
                                               @RequestParam(name = "voucher") MultipartFile voucher) {
        try {
            vo.setVoucher(voucher);
            this.paymentInfoService.uploadVoucher(vo, roleGroup, companyId);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[上传凭证] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 资金历史列表
     */
    @GetMapping("/history/{projectId}")
    public TavernResponse queryProjectPaymentHistory(@PathVariable("projectId") String projectId,
                                                     @ModelAttribute @Valid PaymentHistoryVO vo,
                                                     @RequestHeader(TavernRequestAuthFields.USER_ID) String companyId,
                                                     @RequestHeader(TavernRequestAuthFields.ROLE_GROUP) String roleGroup) {
        try {
            if (roleGroup.equals(UserTypeDict.custom)) {
                vo.setCompanyId(companyId);
            }
            vo.setProjectId(projectId);
            IPage<ProjectPayment> page = this.paymentInfoService.queryProjectPaymentHisList(vo);
            List<PaymentHistoryDTO> projectPayments = page.getRecords()
                    .stream()
                    .map(ModelConverterFactory::modelToPaymentHistoryDTO)
                    .collect(Collectors.toList());
            PaymentHistoryListDTO dto = new PaymentHistoryListDTO();
            dto.setSalaries(projectPayments);
            dto.setTotal(page.getTotal());
            return TavernResponse.ok(dto);
        } catch (Exception e) {
            LOGGER.error("[资金历史列表] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 工资明细
     */
    @GetMapping("/salary/detail")
    public TavernResponse querySalaryDetail(@ModelAttribute @Valid StaffSalaryDetailVO vo,
                                            @RequestHeader(TavernRequestAuthFields.USER_ID) String companyId,
                                            @RequestHeader(TavernRequestAuthFields.ROLE_GROUP) String roleGroup) {
        try {
            if (roleGroup.equals(UserTypeDict.custom)) {
                vo.setCompanyId(companyId);
            }
            IPage<StaffSalaryInfo> page = this.paymentInfoService.queryStaffDetail(vo);
            List<StaffSalaryDetailDTO> staffSalaryDetailDTOList = page.getRecords()
                    .stream()
                    .map(this.converterFactory::modelToStaffSalaryDetail)
                    .collect(Collectors.toList());
            StaffSalaryDetailListDTO dto = new StaffSalaryDetailListDTO();
            dto.setStaffs(staffSalaryDetailDTOList);
            dto.setTotal(page.getTotal());
            return TavernResponse.ok(dto);
        } catch (Exception e) {
            LOGGER.error("[工资明细] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 查询项目人员工资信息
     */
    @GetMapping("/project/staff/{projectId}")
    public TavernResponse querySalaryInfo(@PathVariable("projectId") String projectId,
                                          @ModelAttribute @Valid StaffSalaryInfoVO vo) {
        try {
            vo.setProjectId(projectId);
            IPage<StaffSalaryInfo> page = this.paymentInfoService.queryStaffInfo(vo);
            List<StaffSalaryInfoDTO> staffSalaryDetailDTOList = page.getRecords()
                    .stream()
                    .map(this.converterFactory::modelToStaffSalaryInfo)
                    .collect(Collectors.toList());
            StaffSalaryInfoListDTO dto = new StaffSalaryInfoListDTO();
            dto.setStaffs(staffSalaryDetailDTOList);
            dto.setTotal(page.getTotal());
            return TavernResponse.ok(dto);
        } catch (Exception e) {
            LOGGER.error("[查询项目人员工资信息] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 修改发放明细
     */
    @PutMapping("/detail/{paymentId}")
    public TavernResponse modifyPaymentDetail(@PathVariable("paymentId") String paymentId,
                                              @RequestBody @Valid StaffPaymentInfoVO vo) {
        try {
            vo.setPaymentId(paymentId);
            this.paymentInfoService.modifyStaffPayment(vo);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[修改发放明细] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }
}

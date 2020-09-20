package com.tuozuo.tavern.xinruyi.endpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernRequestAuthFields;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.common.protocol.UserTypeDict;
import com.tuozuo.tavern.xinruyi.convert.ModelConverterFactory;
import com.tuozuo.tavern.xinruyi.dto.PaymentDTO;
import com.tuozuo.tavern.xinruyi.dto.PaymentListDTO;
import com.tuozuo.tavern.xinruyi.dto.ProjectStaffInfoListDTO;
import com.tuozuo.tavern.xinruyi.model.ProjectPayment;
import com.tuozuo.tavern.xinruyi.service.PaymentInfoService;
import com.tuozuo.tavern.xinruyi.vo.PaymentListVO;
import com.tuozuo.tavern.xinruyi.vo.PaymentVoucherUploadVO;
import com.tuozuo.tavern.xinruyi.vo.ProjectAddVO;
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

}

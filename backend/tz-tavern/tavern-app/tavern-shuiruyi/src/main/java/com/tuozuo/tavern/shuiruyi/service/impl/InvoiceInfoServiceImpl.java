package com.tuozuo.tavern.shuiruyi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.UserTypeDict;
import com.tuozuo.tavern.shuiruyi.convert.BusinessConverter;
import com.tuozuo.tavern.shuiruyi.dao.CompanyInfoDao;
import com.tuozuo.tavern.shuiruyi.dao.InvoiceInfoDao;
import com.tuozuo.tavern.shuiruyi.model.*;
import com.tuozuo.tavern.shuiruyi.service.CompanyInfoService;
import com.tuozuo.tavern.shuiruyi.service.InvoiceInfoService;
import com.tuozuo.tavern.shuiruyi.utils.FileUtils;
import com.tuozuo.tavern.shuiruyi.utils.UUIDUtil;
import com.tuozuo.tavern.shuiruyi.vo.InvoiceAuditVO;
import com.tuozuo.tavern.shuiruyi.vo.InvoiceInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/18 <br>
 */
@Service
public class InvoiceInfoServiceImpl implements InvoiceInfoService {

    @Value("${shuiruyi.file.url.path:http://119.3.19.171/shuiruyi/file/invoice/file/}")
    private String fileUrlPath;
    @Value("${shuiruyi.company.file.path:/mnt/file/invoice/file/}")
    private String filePath;
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceInfoServiceImpl.class);
    @Autowired
    private InvoiceInfoDao invoiceInfoDao;
    @Autowired
    private CompanyInfoDao companyInfoDao;

    @Override
    public IPage<InvoiceStatistic> queryInvoiceStatistics(String beginMonth, String endMonth, String companyId, String customId, int pageNo, int pageSize) {
        return this.invoiceInfoDao.selectStatistics(beginMonth,
                endMonth,
                companyId,
                customId,
                pageNo,
                pageSize);

    }

    @Override
    public IPage<InvoiceDetailInfo> queryInvoiceInfoList(String companyId, String contractId, String invoiceStatus, int pageNo, int pageSize, String customId, String customGroup) {
        if (customGroup.equals(UserTypeDict.custom)) {
            return this.invoiceInfoDao.selectInvoiceInfoList(companyId, contractId, invoiceStatus, pageNo, pageSize, customId);
        } else {
            return this.invoiceInfoDao.selectInvoiceInfoList(companyId, contractId, invoiceStatus, pageNo, pageSize, null);

        }
    }

    @Transactional
    @Override
    public void createInvoice(InvoiceInfoVO invoiceInfoVO) throws Exception {
        InvoiceInfo invoiceInfo = BusinessConverter.voToInvoiceInfo(invoiceInfoVO);
        invoiceInfo.setInvoiceId(UUIDUtil.randomUUID32());
        invoiceInfo.setInvoiceDate(LocalDateTime.now());
        this.setInvoiceInfoFiles(invoiceInfoVO, invoiceInfo);
        this.invoiceInfoDao.insert(invoiceInfo);
        CompanyDetailInfo companyDetailInfo = this.companyInfoDao.selectDetailInfo(invoiceInfoVO.getCompanyId()).get();
        if (companyDetailInfo.getFreeDelivery() == 0) {
            throw new Exception("公司免费快递次数已用完");
        }
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setCompanyId(companyDetailInfo.getCompanyId());
        companyInfo.setFreeDelivery(companyDetailInfo.getFreeDelivery() - 1);
        this.companyInfoDao.update(companyInfo);
    }

    @Override
    public InvoiceDetailInfo queryInvoiceInfo(String invoiceId) {
        return this.invoiceInfoDao.selectInvoiceInfo(invoiceId);
    }

    @Override
    public void modifyInvoiceInfo(InvoiceInfoVO vo) throws Exception {
        InvoiceInfo invoiceInfo = BusinessConverter.voToInvoiceInfo(vo);
        invoiceInfo.setInvoiceId(vo.getInvoiceId());
        this.setInvoiceInfoFiles(vo, invoiceInfo);
        this.invoiceInfoDao.update(invoiceInfo);
    }

    @Override
    public void auditInvoiceInfo(String invoiceId, String invoiceStatus, String deliveryId, String remark, String invoiceContent, double tax) {
        InvoiceInfo invoiceInfo = new InvoiceInfo();
        invoiceInfo.setInvoiceId(invoiceId);
        invoiceInfo.setInvoiceStatus(invoiceStatus);
        invoiceInfo.setDeliveryId(deliveryId);
        invoiceInfo.setRemark(remark);
        invoiceInfo.setInvoiceContent(invoiceContent);
        invoiceInfo.setTax(new BigDecimal(tax));
        this.invoiceInfoDao.update(invoiceInfo);
    }


    private void setInvoiceInfoFiles(InvoiceInfoVO vo, InvoiceInfo invoiceInfo) throws Exception {
        if (vo.getAuthLetterFile() != null) {
            String authLetterFileUrl = this.storeInvoiceFile(invoiceInfo.getInvoiceId(), vo.getAuthLetterFile());
            LOGGER.info("authLetterFileUrl: {}", authLetterFileUrl);
            invoiceInfo.setAuthLetterFile(authLetterFileUrl);
        }
        if (vo.getBankFlowFile() != null) {
            String bankFlowFileUrl = this.storeInvoiceFile(invoiceInfo.getInvoiceId(), vo.getBankFlowFile());
            LOGGER.info("bankFlowFileUrl: {}", bankFlowFileUrl);
            invoiceInfo.setBankFlowFile(bankFlowFileUrl);

        }

    }

    //path + invoiceId + file
    private String storeInvoiceFile(String invoiceId, MultipartFile file) throws Exception {

        String pathLocation = StringUtils.join(filePath, invoiceId);
        FileUtils.multiPartFileWriter(file, pathLocation);
        return StringUtils.join(fileUrlPath, invoiceId, "/", file.getOriginalFilename());

    }
}

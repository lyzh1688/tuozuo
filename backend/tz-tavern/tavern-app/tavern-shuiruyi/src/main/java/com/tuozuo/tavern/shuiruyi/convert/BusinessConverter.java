package com.tuozuo.tavern.shuiruyi.convert;

import com.tuozuo.tavern.common.protocol.SystemID;
import com.tuozuo.tavern.common.protocol.UserTypeDict;
import com.tuozuo.tavern.shuiruyi.dict.*;
import com.tuozuo.tavern.shuiruyi.dto.*;
import com.tuozuo.tavern.shuiruyi.model.*;
import com.tuozuo.tavern.shuiruyi.utils.BusinessStatusUtil;
import com.tuozuo.tavern.shuiruyi.utils.DateUtils;
import com.tuozuo.tavern.shuiruyi.vo.CompanyDetailVO;
import com.tuozuo.tavern.shuiruyi.vo.CompanyModifyVO;
import com.tuozuo.tavern.shuiruyi.vo.InvoiceInfoVO;
import com.tuuozuo.tavern.authority.spi.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/08/10 <br>
 */
public class BusinessConverter {

    public static BusinessDictDTO companyInfoToDTO(CompanyInfo companyInfo) {
        BusinessDictDTO businessDictDTO = new BusinessDictDTO();
        businessDictDTO.setId(companyInfo.getCompanyId());
        businessDictDTO.setName(companyInfo.getCompanyName());
        return businessDictDTO;
    }

    public static BusinessDictDTO customInfoToDictDTO(CustomInfo customInfo) {
        BusinessDictDTO businessDictDTO = new BusinessDictDTO();
        businessDictDTO.setId(customInfo.getCustomId());
        businessDictDTO.setName(customInfo.getCustomName());
        return businessDictDTO;
    }

    public static CompanyDetailDTO companyDetailToDTO(CompanyDetailInfo companyDetailInfo) {
        if (Objects.isNull(companyDetailInfo)) {
            return null;
        }
        CompanyDetailDTO companyDetailDTO = new CompanyDetailDTO();
        CompanyInfoDTO companyInfoDTO = new CompanyInfoDTO();
        companyInfoDTO.setCustomId(companyDetailInfo.getCustomId());
        companyInfoDTO.setCompanyName(companyDetailInfo.getCompanyName());
        companyInfoDTO.setCompanyStatus(companyDetailInfo.getCompanyStatus());
        companyInfoDTO.setTax(companyDetailInfo.getTax());
        companyInfoDTO.setAddress(companyDetailInfo.getAddress());
        companyInfoDTO.setCompanyType(companyDetailInfo.getCompanyType());
        companyInfoDTO.setTotalInvoiceNum(companyDetailInfo.getTotalInvoiceNum());
        companyInfoDTO.setInvoicedNum(companyDetailInfo.getInvoicedNum());
        companyInfoDTO.setTotalInvoiceAmt(companyDetailInfo.getTotalInvoiceAmt() == null ? 0 : companyDetailInfo.getTotalInvoiceAmt().doubleValue());
        companyInfoDTO.setFreeDeliveryCnt(companyDetailInfo.getFreeDelivery());
        companyInfoDTO.setIncludeCancel(companyDetailInfo.getIncludeCancel() == null ? "0" : "1");
        if (companyDetailInfo.getBeginDate() != null) {
            companyInfoDTO.setBeginDate(DateUtils.formatDate(companyDetailInfo.getBeginDate().toLocalDate(), DateUtils.DEFAULT_DATE_FORMATTER));
        }
        if (companyDetailInfo.getEndDate() != null) {
            companyInfoDTO.setEndDate(DateUtils.formatDate(companyDetailInfo.getEndDate().toLocalDate(), DateUtils.DEFAULT_DATE_FORMATTER));
        }
        companyInfoDTO.setTradeFlow(companyDetailInfo.getTradeFlow());
        companyInfoDTO.setRegisterArea(companyDetailInfo.getRegisterArea());
        companyInfoDTO.setValueAddedRebateRate(companyDetailInfo.getValueAddedRebateRate());
        companyInfoDTO.setIncomeRebateRate(companyDetailInfo.getIncomeRebateRate());

        BossInfoDTO bossInfoDTO = new BossInfoDTO();
        bossInfoDTO.setBossId(companyDetailInfo.getBossId());
        bossInfoDTO.setBossName(companyDetailInfo.getBossName());
        bossInfoDTO.setBossContact(companyDetailInfo.getBossContact());
        bossInfoDTO.setBossIdPicUp(companyDetailInfo.getBossIdPicUp());
        bossInfoDTO.setBossIdPicBack(companyDetailInfo.getBossIdPicBack());

        CfoInfoDTO cfoInfoDTO = new CfoInfoDTO();
        cfoInfoDTO.setCfoId(companyDetailInfo.getCfoId());
        cfoInfoDTO.setCfoName(companyDetailInfo.getCfoName());
        cfoInfoDTO.setCfoContact(companyDetailInfo.getCfoContact());
        cfoInfoDTO.setCfoIdPicUp(companyDetailInfo.getCfoIdPicUp());
        cfoInfoDTO.setCfoIdPicBack(companyDetailInfo.getCfoIdPicBack());

        companyDetailDTO.setCompanyInfo(companyInfoDTO);
        companyDetailDTO.setBossInfo(bossInfoDTO);
        companyDetailDTO.setCfoInfo(cfoInfoDTO);

        return companyDetailDTO;
    }

    public static BusinessDictDTO businessDictToDTO(BusinessDict businessDict) {
        BusinessDictDTO businessDictDTO = new BusinessDictDTO();
        businessDictDTO.setId(businessDict.getBusinessId());
        businessDictDTO.setName(businessDict.getBusinessName());
        return businessDictDTO;
    }

    public static CustomInfoDetailDTO customDetailToDTO(CustomDetailInfo customDetailInfo) {
        CustomInfoDetailDTO customInfoDetailDTO = new CustomInfoDetailDTO();
        customInfoDetailDTO.setCustomType(customDetailInfo.getCustomType());
        customInfoDetailDTO.setCustomName(customDetailInfo.getCustomName());
        customInfoDetailDTO.setCustomContact(customDetailInfo.getCustomContact());
        customInfoDetailDTO.setProvince(customDetailInfo.getProvince());
        customInfoDetailDTO.setCity(customDetailInfo.getCity());
        customInfoDetailDTO.setDistrict(customDetailInfo.getDistrict());
        customInfoDetailDTO.setTotalServerCharge(customDetailInfo.getTotalServerCharge());
        customInfoDetailDTO.setBalance(customDetailInfo.getBalance());
        return customInfoDetailDTO;
    }

    public static CustomTradeFlowDTO customTradeFlowToDTO(CustomTradeFlow customTradeFlow) {
        CustomTradeFlowDTO customTradeFlowDTO = new CustomTradeFlowDTO();
        customTradeFlowDTO.setAmount(customTradeFlow.getAmount());
        customTradeFlowDTO.setBalance(customTradeFlow.getBalance());
        customTradeFlowDTO.setEvent(customTradeFlow.getEvent());
        customTradeFlowDTO.setRemark(customTradeFlow.getRemark());
        if (customTradeFlow.getTradeDate() != null) {
            customTradeFlowDTO.setTradeDate(DateUtils.formatDate(customTradeFlow.getTradeDate()));
        }
        customTradeFlowDTO.setTradeFile(customTradeFlow.getTradeFile());

        return customTradeFlowDTO;
    }

    public static CustomInfoDTO customInfoToDTO(CustomInfo customInfo) {
        CustomInfoDTO customInfoDTO = new CustomInfoDTO();
        BeanUtils.copyProperties(customInfo, customInfoDTO);
        customInfoDTO.setUpdateDate(DateUtils.formatDateTime(customInfo.getUpdateDate(), DateUtils.DEFAULT_DATETIME_FORMATTER));
        return customInfoDTO;
    }

    public static CompanyBriefInfo companyInfoToBriefDTO(CompanyInfo companyInfo) {
        CompanyBriefInfo companyBriefInfo = new CompanyBriefInfo();
        companyBriefInfo.setCustomId(companyInfo.getCustomId());
        companyBriefInfo.setCompanyId(companyInfo.getCompanyId());
        companyBriefInfo.setCompanyName(companyInfo.getCompanyName());
        companyBriefInfo.setCompanyStatus(BusinessStatus.getBusinessStatus(companyInfo.getCompanyStatus()).getStatus());
        companyBriefInfo.setRegisterStatus(BusinessStatusUtil.registeredMap.containsKey(companyInfo.getCompanyStatus()) ? "2" : "1");
        if (companyInfo.getBeginDate() != null) {
            companyBriefInfo.setBeginDate(DateUtils.formatDate(companyInfo.getBeginDate().toLocalDate(), DateUtils.DEFAULT_DATE_FORMATTER));
        }
        if (companyInfo.getEndDate() != null) {
            companyBriefInfo.setEndDate(DateUtils.formatDate(companyInfo.getEndDate().toLocalDate(), DateUtils.DEFAULT_DATE_FORMATTER));
        }
        companyBriefInfo.setCompanyType(CompanyType.getCompanyType(companyInfo.getCompanyType()).getName());
        companyBriefInfo.setTax(companyInfo.getTax());
        return companyBriefInfo;
    }

    public static CompanyInfo voToCompanyInfo(CompanyDetailVO vo) {
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setCustomId(vo.getCustomId());
        companyInfo.setCompanyName(vo.getCompanyName());
        companyInfo.setCompanyType(vo.getCompanyType());
        companyInfo.setAddress(vo.getAddress());
        companyInfo.setTax(vo.getTax());
        companyInfo.setBossName(vo.getBossName());
        companyInfo.setBossId(vo.getBossId());
        companyInfo.setBossName(vo.getBossName());
        companyInfo.setBossContact(vo.getBossContact());
        companyInfo.setCfoId(vo.getCfoId());
        companyInfo.setCfoContact(vo.getCfoContact());
        companyInfo.setCfoName(vo.getCfoName());
        return companyInfo;
    }

    public static CompanyInfo voToCompanyInfo(CompanyModifyVO vo) {

        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setCompanyId(vo.getCompanyId());
        companyInfo.setFreeDelivery(vo.getFreeDeliveryCnt());
        companyInfo.setCompanyStatus(vo.getCompanyStatus());
        companyInfo.setIncludeCancel(vo.getIncludeCancel());
        if (StringUtils.isNoneEmpty(vo.getBeginDate())) {
            companyInfo.setBeginDate(DateUtils.parseDateTime(StringUtils.join(vo.getBeginDate(), " 00:00:00"), DateUtils.DEFAULT_DATETIME_FORMATTER));
        }
        if (StringUtils.isNoneEmpty(vo.getEndDate())) {
            companyInfo.setEndDate(DateUtils.parseDateTime(StringUtils.join(vo.getEndDate(), " 00:00:00"), DateUtils.DEFAULT_DATETIME_FORMATTER));
        }

        companyInfo.setTradeFlow(vo.getTradeFlow());
        companyInfo.setRegisterArea(vo.getRegisterArea());
        companyInfo.setValueAddedRebateRate(new BigDecimal(vo.getValueAddedRebateRate()));
        companyInfo.setIncomeRebateRate(new BigDecimal(vo.getIncomeRebateRate()));

        return companyInfo;
    }

    public static UserVO userToVO(String userId, String customPswd) {
        UserVO user = new UserVO();
        user.setUserId(userId);
        user.setSystemId(SystemID.SYS_ID);
        user.setRoleGroup(UserTypeDict.custom);
        user.setUserPswd(customPswd);
        user.setPrivilege("shuiruyi.custom.normal");
        return user;
    }

    public static InvoiceItemDTO modelToInvoiceItemDTO(InvoiceDetailInfo invoiceDetailInfo) {
        InvoiceItemDTO item = new InvoiceItemDTO();
        item.setInvoiceId(invoiceDetailInfo.getInvoiceId());
        item.setCompanyPartyAName(invoiceDetailInfo.getCompanyPartyAName());
        item.setCompanyPartyBName(invoiceDetailInfo.getCompanyPartyBName());
        item.setContractName(invoiceDetailInfo.getContractName());
        item.setInvoiceAmount(invoiceDetailInfo.getInvoiceAmount() == null ? BigDecimal.ZERO.doubleValue() : invoiceDetailInfo.getInvoiceAmount().doubleValue());
        item.setDeliveryId(invoiceDetailInfo.getDeliveryId());
        item.setInvoiceStatus(InvoiceStatus.getInvoiceStatus(invoiceDetailInfo.getInvoiceStatus()).getName());
        item.setRemark(invoiceDetailInfo.getRemark());
        item.setTax(invoiceDetailInfo.getTax());
        return item;
    }

    public static InvoiceInfo voToInvoiceInfo(InvoiceInfoVO vo) {
        InvoiceInfo invoiceInfo = new InvoiceInfo();
        invoiceInfo.setCompanyId(vo.getCompanyId());
        invoiceInfo.setContractId(vo.getContractId());
        invoiceInfo.setInvoiceType(vo.getInvoiceType());
        invoiceInfo.setInvoiceAmount(new BigDecimal(vo.getInvoiceAmount()));
        invoiceInfo.setRecvAmount(new BigDecimal(vo.getRecvAmount()));
        if (vo.getRecvDate() != null) {
            invoiceInfo.setRecvDate(DateUtils.convert2Date(DateUtils.parseDateTime(vo.getRecvDate() + " 00:00:00", DateUtils.DEFAULT_DATETIME_FORMATTER)));
        }
        invoiceInfo.setTax(new BigDecimal(vo.getTax()));
        return invoiceInfo;
    }

    public static InvoiceInfoDTO modelToInvoiceInfo(InvoiceDetailInfo invoiceDetailInfo) {

        if (invoiceDetailInfo == null) {
            return null;
        }

        InvoiceInfoDTO invoiceInfoDTO = new InvoiceInfoDTO();
        invoiceInfoDTO.setCompanyName(invoiceDetailInfo.getCompanyName());
        invoiceInfoDTO.setContractName(invoiceDetailInfo.getContractName());
        invoiceInfoDTO.setInvoiceType(invoiceDetailInfo.getInvoiceType());
        invoiceInfoDTO.setInvoiceAmount(invoiceDetailInfo.getInvoiceAmount() == null ? BigDecimal.ZERO.doubleValue() : invoiceDetailInfo.getInvoiceAmount().doubleValue());
        invoiceInfoDTO.setRecvAmount(invoiceDetailInfo.getRecvAmount() == null ? BigDecimal.ZERO.doubleValue() : invoiceDetailInfo.getRecvAmount().doubleValue());
        invoiceInfoDTO.setAuthLetterFile(invoiceDetailInfo.getAuthLetterFile());
        invoiceInfoDTO.setBankFlowFile(invoiceDetailInfo.getBankFlowFile());
        invoiceInfoDTO.setInvoiceContent(invoiceDetailInfo.getInvoiceContent());
        invoiceInfoDTO.setRecvDate(DateUtils.formatDate(invoiceDetailInfo.getRecvDate(), DateUtils.DEFAULT_DATETIME_FORMATTER1));
        invoiceInfoDTO.setCompanyId(invoiceDetailInfo.getCompanyId());
        invoiceInfoDTO.setContractId(invoiceDetailInfo.getContractId());
        invoiceInfoDTO.setInvoiceStatus(invoiceDetailInfo.getInvoiceStatus());
        invoiceInfoDTO.setRemark(invoiceDetailInfo.getRemark());
        invoiceInfoDTO.setDeliveryId(invoiceDetailInfo.getDeliveryId());
        invoiceInfoDTO.setTax(invoiceDetailInfo.getTax());

        return invoiceInfoDTO;
    }

    public static ContractItemDTO modelToContractItemDTO(ContractDetailInfo contractDetailInfo) {

        ContractItemDTO itemDTO = new ContractItemDTO();
        itemDTO.setContractId(contractDetailInfo.getContractId());
        itemDTO.setCompanyId(contractDetailInfo.getCompanyId());
        itemDTO.setCompanyPartyAName(contractDetailInfo.getCompanyPartyAName());
        itemDTO.setCompanyPartyBName(contractDetailInfo.getCompanyPartyBName());
        itemDTO.setContractName(contractDetailInfo.getContractName());
        itemDTO.setContractAmount(contractDetailInfo.getContractAmount() == null ? BigDecimal.ZERO.doubleValue() : contractDetailInfo.getContractAmount().doubleValue());
        itemDTO.setInvoiceAmount(contractDetailInfo.getInvoiceAmount() == null ? BigDecimal.ZERO.doubleValue() : contractDetailInfo.getInvoiceAmount().doubleValue());
        itemDTO.setContractStatus(ContractStatus.getInvoiceStatus(contractDetailInfo.getContractStatus()).getName());
        itemDTO.setInvoicePattern(InvoicePattern.getInvoicePattern(contractDetailInfo.getInvoicePattern()).getName());
        itemDTO.setContractFile(contractDetailInfo.getContractFile());
        itemDTO.setRemark(contractDetailInfo.getRemark());
        return itemDTO;
    }

    public static ContractItemDTO modelToContractDetailDTO(ContractDetailInfo contractDetailInfo) {

        ContractItemDTO itemDTO = new ContractItemDTO();
        itemDTO.setContractId(contractDetailInfo.getContractId());
        itemDTO.setCompanyId(contractDetailInfo.getCompanyId());
        itemDTO.setCompanyPartyAName(contractDetailInfo.getCompanyPartyAName());
        itemDTO.setCompanyPartyBName(contractDetailInfo.getCompanyPartyBName());
        itemDTO.setContractName(contractDetailInfo.getContractName());
        itemDTO.setContractAmount(contractDetailInfo.getContractAmount() == null ? BigDecimal.ZERO.doubleValue() : contractDetailInfo.getContractAmount().doubleValue());
        itemDTO.setInvoiceAmount(contractDetailInfo.getInvoiceAmount() == null ? BigDecimal.ZERO.doubleValue() : contractDetailInfo.getInvoiceAmount().doubleValue());
        itemDTO.setContractStatus(contractDetailInfo.getContractStatus());
        itemDTO.setInvoicePattern(contractDetailInfo.getInvoicePattern());
        itemDTO.setContractFile(contractDetailInfo.getContractFile());
        itemDTO.setRemark(contractDetailInfo.getRemark());
        return itemDTO;
    }

    public static ContractTemplateDTO modelToContractTemplateDTO(ContractTemplate contractTemplate) {
        ContractTemplateDTO contractTemplateDTO = new ContractTemplateDTO();
        contractTemplateDTO.setTemplateFile(contractTemplate.getTemplateFile());
        contractTemplateDTO.setTemplateId(contractTemplate.getContractTemplateId());
        contractTemplateDTO.setTemplateName(contractTemplate.getContractTemplateName());
        return contractTemplateDTO;
    }
}

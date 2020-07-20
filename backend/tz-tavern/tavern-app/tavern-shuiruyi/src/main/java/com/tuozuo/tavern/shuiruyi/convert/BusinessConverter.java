package com.tuozuo.tavern.shuiruyi.convert;

import com.tuozuo.tavern.shuiruyi.dict.BusinessStatus;
import com.tuozuo.tavern.shuiruyi.dict.CompanyType;
import com.tuozuo.tavern.shuiruyi.dto.*;
import com.tuozuo.tavern.shuiruyi.model.*;
import com.tuozuo.tavern.shuiruyi.utils.BusinessStatusUtil;
import com.tuozuo.tavern.shuiruyi.utils.DateUtils;

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
        companyInfoDTO.setCompanyName(companyDetailInfo.getCompanyName());
        companyInfoDTO.setCompanyStatus(companyDetailInfo.getCompanyStatus());
        companyInfoDTO.setTax(companyDetailInfo.getTax());
        companyInfoDTO.setAddress(companyDetailInfo.getAddress());
        companyInfoDTO.setCompanyType(companyDetailInfo.getCompanyType());
        companyInfoDTO.setTotalInvoiceNum(companyDetailInfo.getTotalInvoiceNum());
        companyInfoDTO.setInvoicedNum(companyDetailInfo.getInvoicedNum());
        companyInfoDTO.setTotalInvoiceAmt(companyDetailInfo.getTotalInvoiceAmt());
        companyInfoDTO.setFreeDeliveryCnt(companyDetailInfo.getFreeDelivery());
        companyInfoDTO.setIncludeCancel(companyDetailInfo.getIncludeCancel());
        companyInfoDTO.setBeginDate(DateUtils.formatDateTime(companyDetailInfo.getBeginDate(), DateUtils.DEFAULT_DATETIME_FORMATTER));
        companyInfoDTO.setEndDate(DateUtils.formatDateTime(companyDetailInfo.getEndDate(), DateUtils.DEFAULT_DATETIME_FORMATTER));

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
        customInfoDetailDTO.setCustomName(customDetailInfo.getCustomName());
        customInfoDetailDTO.setCustomContact(customDetailInfo.getCustomContact());
        customInfoDetailDTO.setProvince(customDetailInfo.getProvince());
        customInfoDetailDTO.setTotalServerCharge(customDetailInfo.getTotalServerCharge());
        customInfoDetailDTO.setBalance(customDetailInfo.getBalance());
        return customInfoDetailDTO;
    }

    public static CustomTradeFlowDTO customTradeFlowToDTO(CustomTradeFlow customTradeFlow) {
        CustomTradeFlowDTO customTradeFlowDTO = new CustomTradeFlowDTO();
        customTradeFlowDTO.setBalance(customTradeFlow.getBalance());
        customTradeFlowDTO.setEvent(customTradeFlow.getEvent());
        customTradeFlowDTO.setRemark(customTradeFlow.getRemark());
        customTradeFlowDTO.setTradeDate(DateUtils.formatDate(customTradeFlow.getTradeDate()));
        customTradeFlowDTO.setTradeSnapshot(customTradeFlow.getTradeSnapshot());
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
        companyBriefInfo.setCompanyId(companyInfo.getCompanyId());
        companyBriefInfo.setCompanyName(companyInfo.getCompanyName());
        companyBriefInfo.setCompanyStatus(BusinessStatus.getBusinessStatus(companyInfo.getCompanyStatus()).getStatus());
        companyBriefInfo.setRegisterStatus(BusinessStatusUtil.registeredMap.containsKey(companyInfo.getCompanyStatus()) ? "1" : "0");
        companyBriefInfo.setBeginDate(DateUtils.formatDateTime(companyInfo.getBeginDate(), DateUtils.DEFAULT_DATETIME_FORMATTER));
        companyBriefInfo.setEndDate(DateUtils.formatDateTime(companyInfo.getEndDate(), DateUtils.DEFAULT_DATETIME_FORMATTER));
        companyBriefInfo.setCompanyType(CompanyType.getCompanyType(companyInfo.getCompanyType()).getName());
        companyBriefInfo.setTax(companyInfo.getTax());
        return companyBriefInfo;
    }
}

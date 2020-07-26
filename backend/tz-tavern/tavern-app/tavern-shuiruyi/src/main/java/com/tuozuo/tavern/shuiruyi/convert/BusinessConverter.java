package com.tuozuo.tavern.shuiruyi.convert;

import com.tuozuo.tavern.common.protocol.SystemID;
import com.tuozuo.tavern.shuiruyi.dict.BusinessStatus;
import com.tuozuo.tavern.shuiruyi.dict.CompanyType;
import com.tuozuo.tavern.shuiruyi.dto.*;
import com.tuozuo.tavern.shuiruyi.model.*;
import com.tuozuo.tavern.shuiruyi.utils.BusinessStatusUtil;
import com.tuozuo.tavern.shuiruyi.utils.DateUtils;
import com.tuozuo.tavern.shuiruyi.vo.CompanyDetailVO;
import com.tuozuo.tavern.shuiruyi.vo.CompanyModifyVO;
import com.tuozuo.tavern.shuiruyi.vo.CustomAddInfoVO;
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
        companyInfoDTO.setRebateTaxRate(companyDetailInfo.getRebateTaxRate());

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
        companyInfo.setRebateTaxRate(new BigDecimal(vo.getRebateTaxRate()));

        return companyInfo;
    }

    public static UserVO userToVO(CustomAddInfoVO vo) {
        UserVO user = new UserVO();
        user.setUserId(vo.getCustomId());
        user.setSystemId(SystemID.SYS_ID);
        user.setRoleGroup(vo.getCustomType());
        user.setUserPswd(vo.getCustomPswd());
        return user;
    }
}

package com.tuozuo.tavern.shuiruyi.convert;

import com.tuozuo.tavern.shuiruyi.dto.*;
import com.tuozuo.tavern.shuiruyi.model.BusinessDict;
import com.tuozuo.tavern.shuiruyi.model.CompanyDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.CompanyInfo;
import com.tuozuo.tavern.shuiruyi.utils.DateUtils;

import java.util.Objects;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/08/10 <br>
 */
public class BusinessConverter {

    public static BusinessDictDTO companyInfoToDTO(CompanyInfo companyInfo) {
        if (Objects.isNull(companyInfo)) {
            return null;
        }
        BusinessDictDTO businessDictDTO = new BusinessDictDTO();
        businessDictDTO.setId(companyInfo.getCompanyId());
        businessDictDTO.setName(companyInfo.getCompanyName());
        return null;
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

}

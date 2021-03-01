package com.tuozuo.tavern.corp.assist.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuozuo.tavern.corp.assist.convert.ModelMapConverterFactory;
import com.tuozuo.tavern.corp.assist.dao.CorporationInfoDao;
import com.tuozuo.tavern.corp.assist.dict.ValidType;
import com.tuozuo.tavern.corp.assist.dto.CorporationDTO;
import com.tuozuo.tavern.corp.assist.model.BusinessDict;
import com.tuozuo.tavern.corp.assist.model.CorporationGroupClientInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationInfo;
import com.tuozuo.tavern.corp.assist.service.BusinessDictService;
import com.tuozuo.tavern.corp.assist.service.CorporationInfoService;
import com.tuozuo.tavern.corp.assist.utils.DateUtils;
import com.tuozuo.tavern.corp.assist.utils.UUIDUtil;
import com.tuozuo.tavern.corp.assist.vo.CorporationInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/5 <br>
 */
@Service
public class CorporationInfoServiceImpl implements CorporationInfoService {

    @Autowired
    private CorporationInfoDao corporationInfoDao;
    @Autowired
    private ModelMapConverterFactory converterFactory;
    @Autowired
    private BusinessDictService businessDictService;


    @Override
    public boolean addCorporation(CorporationInfoVO corporationInfoVO) {
        CorporationInfo corporationInfo = this.converterFactory.voToCorporationInfo(corporationInfoVO);
        corporationInfo.setCorpId(UUIDUtil.randomUUID32());
        corporationInfo.setValid(ValidType.VALID.getType());
        corporationInfo.setCreateTime(LocalDateTime.now());
        return this.corporationInfoDao.insertCorporation(corporationInfo);
    }

    @Override
    public boolean delCorporation(String corpId) {
        CorporationInfo corporationInfo = CorporationInfo.create(corpId, ValidType.INVALID.getType());
        return this.corporationInfoDao.delCorporation(corporationInfo);
    }

    @Override
    public boolean modifyCorporation(CorporationInfoVO corporationInfoVO) {
        CorporationInfo corporationInfo = this.converterFactory.voToCorporationInfo(corporationInfoVO);
        return this.corporationInfoDao.updateCorporation(corporationInfo);
    }

    @Override
    public IPage<CorporationDTO> queryCorporations(String corpName, String clientName, int pageNo, int pageSize) {
        Page<CorporationInfo> page = new Page<>(pageNo, pageSize);
        IPage<CorporationInfo> iPage = this.corporationInfoDao.selectCorporations(corpName, clientName, page);
        Map<String, Map<String, String>> map = this.businessDictService.queryDicts();
        List<CorporationDTO> list = iPage.getRecords().stream()
                .map(info -> {
                    CorporationDTO corporationDTO = new CorporationDTO();
                    BeanUtils.copyProperties(info, corporationDTO);
                    if (map.containsKey("corpStatus")) {
                        corporationDTO.setCorpStatus(map.get("corpStatus").get(info.getCorpStatus()));
                    }
                    corporationDTO.setCorpStatusId(info.getCorpStatus());
                    if (map.containsKey("gender")) {
                        corporationDTO.setBossGender(map.get("gender").get(info.getBossGender()));
                    }
                    corporationDTO.setBossGenderId(info.getBossGender());
                    if (map.containsKey("registerPark")) {
                        corporationDTO.setRegisterPark(map.get("registerPark").get(info.getRegisterPark()));
                    }
                    corporationDTO.setRegisterParkId(info.getRegisterPark());
                    if (map.containsKey("corpType")) {
                        corporationDTO.setCorpType(map.get("corpType").get(info.getCorpType()));
                    }
                    corporationDTO.setCorpTypeId(info.getCorpType());
                    if (map.containsKey("taxType")) {
                        corporationDTO.setTaxType(map.get("taxType").get(info.getTaxType()));
                    }
                    corporationDTO.setTaxTypeId(info.getTaxType());
                    corporationDTO.setRegisterDate(DateUtils.formatDate(info.getRegisterDate(), DateUtils.SIMPLE_8_FORMATTER));
                    return corporationDTO;
                }).collect(Collectors.toList());
        Page<CorporationDTO> dtoPage = new Page<>();
        dtoPage.setRecords(list);
        dtoPage.setTotal(iPage.getTotal());
        return dtoPage;
    }

    @Override
    public List<CorporationDTO> queryCorporationsFromApp(String corpName, String clientName, String corpId, String createTime) {
        if (StringUtils.isEmpty(createTime)) {
            createTime = DateUtils.formatDateTime(LocalDateTime.now().plusMonths(1), DateUtils.DEFAULT_DATETIME_FORMATTER);
        }
        List<CorporationInfo> corporationInfos = this.corporationInfoDao.selectCorporationsFromApp(corpName, clientName, corpId, createTime);
        Map<String, Map<String, String>> map = this.businessDictService.queryDicts();
        List<CorporationDTO> list = corporationInfos.stream()
                .map(info -> {
                    CorporationDTO corporationDTO = new CorporationDTO();
                    BeanUtils.copyProperties(info, corporationDTO);
                    if (map.containsKey("corpStatus")) {
                        corporationDTO.setCorpStatus(map.get("corpStatus").get(info.getCorpStatus()));
                    }
                    corporationDTO.setCorpStatusId(info.getCorpStatus());
                    if (map.containsKey("gender")) {
                        corporationDTO.setBossGender(map.get("gender").get(info.getBossGender()));
                    }
                    corporationDTO.setBossGenderId(info.getBossGender());
                    if (map.containsKey("registerPark")) {
                        corporationDTO.setRegisterPark(map.get("registerPark").get(info.getRegisterPark()));
                    }
                    corporationDTO.setRegisterParkId(info.getRegisterPark());
                    if (map.containsKey("corpType")) {
                        corporationDTO.setCorpType(map.get("corpType").get(info.getCorpType()));
                    }
                    corporationDTO.setCorpTypeId(info.getCorpType());
                    if (map.containsKey("taxType")) {
                        corporationDTO.setTaxType(map.get("taxType").get(info.getTaxType()));
                    }
                    corporationDTO.setTaxTypeId(info.getTaxType());
                    corporationDTO.setRegisterDate(DateUtils.formatDate(info.getRegisterDate(), DateUtils.SIMPLE_8_FORMATTER));
                    return corporationDTO;
                }).collect(Collectors.toList());
        return list;
    }

    @Override
    public CorporationInfo queryCorporationDetail(String corpId) {
        return this.corporationInfoDao.selectCorporationDetail(corpId);
    }

    @Override
    public List<CorporationInfo> fuzzyQuery(String corpName, int queryCnt) {
        return this.corporationInfoDao.fuzzyQuery(corpName, queryCnt);
    }
}

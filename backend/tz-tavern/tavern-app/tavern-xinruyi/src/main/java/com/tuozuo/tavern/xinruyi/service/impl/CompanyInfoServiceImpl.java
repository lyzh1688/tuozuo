package com.tuozuo.tavern.xinruyi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.UserTypeDict;
import com.tuozuo.tavern.xinruyi.convert.ModelConverterFactory;
import com.tuozuo.tavern.xinruyi.convert.ModelMapConverterFactory;
import com.tuozuo.tavern.xinruyi.dao.CompanyInfoDao;
import com.tuozuo.tavern.xinruyi.dao.EventInfoDao;
import com.tuozuo.tavern.xinruyi.dict.CompanyStatus;
import com.tuozuo.tavern.xinruyi.dict.EventType;
import com.tuozuo.tavern.xinruyi.model.*;
import com.tuozuo.tavern.xinruyi.service.CompanyInfoService;
import com.tuozuo.tavern.xinruyi.utils.FileUtils;
import com.tuozuo.tavern.xinruyi.utils.UUIDUtil;
import com.tuozuo.tavern.xinruyi.vo.CompanyApplyVO;
import com.tuozuo.tavern.xinruyi.vo.CompanyAuthInfoVO;
import com.tuozuo.tavern.xinruyi.vo.CompanyEventVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/4 <br>
 */
@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyInfoServiceImpl.class);

    @Value("${xinruyi.file.url.path:http://119.3.19.171/xinruyi/file/company/file/}")
    private String fileUrlPath;
    @Value("${xinruyi.company.file.path:/mnt/file/xinruyi/company/file/}")
    private String filePath;

    @Autowired
    private CompanyInfoDao companyInfoDao;
    @Autowired
    private EventInfoDao eventInfoDao;
    @Autowired
    private ModelMapConverterFactory factory;

    @Transactional
    @Override
    public void companyApply(CompanyApplyVO vo) {
        //企业申请
        CompanyInfo companyInfo = this.factory.voToCompanyInfo(vo);
        companyInfo.setStatus(CompanyStatus.APPLYING.getStatus());
        this.companyInfoDao.insertCompanyInfo(companyInfo);
        //企业申请事件
        EventTodoList eventTodoList = new EventTodoList();
        //构造snapshot
        JSONObject snapshot = new JSONObject();
        snapshot.put("registerId", companyInfo.getRegisterId());
        eventTodoList.setSnapshot(snapshot.toJSONString());
        eventTodoList.setApplicant(vo.getCompanyName());
        eventTodoList.setEventId(UUIDUtil.randomUUID32());
        eventTodoList.setEventType(EventType.ENTERPRISE_APPLY.getStatus());
        eventTodoList.setRole(UserTypeDict.staff);
        eventTodoList.setEventOwnerName(vo.getCompanyName());
        eventTodoList.setEventDate(LocalDateTime.now());
        this.eventInfoDao.insertEventTodo(eventTodoList);
    }

    @Override
    public CompanyInfo queryCompanyInfo(String companyId) {
        return this.companyInfoDao.selectCompanyInfo(companyId);
    }

    @Transactional
    @Override
    public void applyForCompanyAuth(CompanyAuthInfoVO companyAuthInfoVO) throws Exception {
        //1、companyInfoExt表
        CompanyInfoExt companyInfoExt = ModelConverterFactory.authVOToCompanyInfoExt(companyAuthInfoVO);
        this.setCompanyInfoFiles(companyAuthInfoVO.getBusinessLicense(),
                companyAuthInfoVO.getBossIdPicUp(),
                companyAuthInfoVO.getBossIdPicBack(),
                companyInfoExt);
        this.companyInfoDao.insertCompanyAuthInfo(companyInfoExt);
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setCompanyName(companyAuthInfoVO.getCompanyName());
        companyInfo.setCompanyId(companyAuthInfoVO.getCompanyId());
        companyInfo.setStatus(CompanyStatus.AUTH_APPLYING.getStatus());
        this.companyInfoDao.updateCompanyInfo(companyInfo);


        EventTodoList eventTodoList = new EventTodoList();
        //构造snapshot
        JSONObject snapshot = new JSONObject();
        snapshot.put("companyId", companyAuthInfoVO.getCompanyId());
        eventTodoList.setSnapshot(snapshot.toJSONString());
        eventTodoList.setApplicant(companyAuthInfoVO.getCompanyName());
        eventTodoList.setEventId(UUIDUtil.randomUUID32());
        eventTodoList.setEventType(EventType.ENTERPRISE_AUTH.getStatus());
        eventTodoList.setEventOwnerId(companyAuthInfoVO.getCompanyId());
        eventTodoList.setCompanyId(companyAuthInfoVO.getCompanyId());
        eventTodoList.setRole(UserTypeDict.staff);
        eventTodoList.setEventOwnerName(companyAuthInfoVO.getCompanyName());
        eventTodoList.setEventDate(LocalDateTime.now());
        this.eventInfoDao.insertEventTodo(eventTodoList);

    }

    @Transactional
    @Override
    public void modifyCompanyInfo(CompanyAuthInfoVO companyAuthInfoVO) throws Exception {
        CompanyInfoExt companyInfoExt = ModelConverterFactory.authVOToCompanyInfoExt(companyAuthInfoVO);
        this.setCompanyInfoFiles(companyAuthInfoVO.getBusinessLicense(),
                companyAuthInfoVO.getBossIdPicUp(),
                companyAuthInfoVO.getBossIdPicBack(),
                companyInfoExt);
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setCompanyId(companyAuthInfoVO.getCompanyId());
        companyInfo.setCompanyName(companyAuthInfoVO.getCompanyName());
        this.companyInfoDao.updateCompanyInfo(companyInfo);
        this.companyInfoDao.updateCompanyAuthInfo(companyInfoExt);
    }

    @Override
    public CompanyInfoExt queryCompanyDetailInfo(String companyId) {
        return this.companyInfoDao.selectCompanyDetailInfo(companyId);
    }

    @Override
    public List<CompanyInfo> queryCompanyList(String companyName, int queryCnt) {
        return this.companyInfoDao.selectCompanyList(companyName, queryCnt);
    }

    @Override
    public IPage<CompanyEventInfo> queryCompanyEvents(CompanyEventVO vo) {
        return this.eventInfoDao.selectCompanies(vo.getPageNo(), vo.getPageSize(),
                vo.getCompanyId(),
                vo.getIndustryId(),
                vo.getProvince(),
                vo.getCity(),
                vo.getDistrict(),
                vo.getStatus(),
                vo.getBeginDate(),
                vo.getEndDate());
    }

    @Transactional
    @Override
    public void auditCompanyAuth(String companyId, String status, String remark) {
        //1、认证成功，修改公司状态
        //2、认证失败，修改公司状态
        //3、移动事件至历史
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setCompanyId(companyId);
        companyInfo.setStatus(status);
        this.companyInfoDao.updateCompanyInfo(companyInfo);
        CompanyInfoExt companyInfoExt = new CompanyInfoExt();
        companyInfoExt.setCompanyId(companyId);
        companyInfoExt.setRemark(remark);
        this.companyInfoDao.updateCompanyInfoExt(companyInfoExt);

        EventTodoList eventTodoList = this.eventInfoDao.selectCompanyAuthTodo(companyId, EventType.ENTERPRISE_AUTH.getStatus());
        EventFinishList eventFinishList = new EventFinishList();
        BeanUtils.copyProperties(eventTodoList, eventFinishList);
        eventFinishList.setUpdateDate(LocalDateTime.now());
        this.eventInfoDao.delEventTodo(eventTodoList.getEventId());
        this.eventInfoDao.insertEventFinish(eventFinishList);

    }


    private void setCompanyInfoFiles(MultipartFile businessLicense,
                                     MultipartFile bossIdPicUp,
                                     MultipartFile bossIdPicBack,
                                     CompanyInfoExt companyInfoExt) throws Exception {
        if (businessLicense != null) {
            String businessLicenseUrl = this.storeProjectFile(companyInfoExt.getCompanyId(), businessLicense);
            LOGGER.info("businessLicenseUrl: {}", businessLicenseUrl);
            companyInfoExt.setFileBizLicense(businessLicenseUrl);
        }
        if (bossIdPicUp != null) {
            String bossIdPicUpUrl = this.storeProjectFile(companyInfoExt.getCompanyId(), bossIdPicUp);
            LOGGER.info("bossIdPicUpUrl: {}", bossIdPicUpUrl);
            companyInfoExt.setFileLegalPersonIdcardUp(bossIdPicUpUrl);
        }
        if (bossIdPicBack != null) {
            String bossIdPicBackUrl = this.storeProjectFile(companyInfoExt.getCompanyId(), bossIdPicBack);
            LOGGER.info("bossIdPicBackUrl: {}", bossIdPicBackUrl);
            companyInfoExt.setFileLegalPersonIdcardBack(bossIdPicBackUrl);
        }

    }

    //path + companyId + file
    private String storeProjectFile(String companyId, MultipartFile file) throws Exception {

        String pathLocation = StringUtils.join(filePath, companyId,
                "/");
        String fileName = FileUtils.multiPartFileWriter(file, pathLocation);
        return StringUtils.join(fileUrlPath, companyId,
                "/", fileName);

    }
}

package com.tuozuo.tavern.shuiruyi.service.impl;

import java.util.List;

import com.tuozuo.tavern.shuiruyi.vo.CompanyDetailVO;
import com.tuozuo.tavern.shuiruyi.vo.CompanyModifyVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.UserTypeDict;
import com.tuozuo.tavern.shuiruyi.convert.BusinessConverter;
import com.tuozuo.tavern.shuiruyi.dao.CompanyInfoDao;
import com.tuozuo.tavern.shuiruyi.dict.CompanyFileType;
import com.tuozuo.tavern.shuiruyi.model.CompanyDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.CompanyInfo;
import com.tuozuo.tavern.shuiruyi.service.CompanyInfoService;
import com.tuozuo.tavern.shuiruyi.utils.FileUtils;
import com.tuozuo.tavern.shuiruyi.utils.UUIDUtil;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/08/10 <br>
 */
@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {

    @Value("${shuiruyi.file.url.path:http://119.3.19.171/shuiruyi/file/company/file/}")
    private String fileUrlPath;
    @Value("${shuiruyi.company.file.path:/mnt/file/company/file/}")
    private String filePath;
    @Value("${free.delivery.cnt:12}")
    private int freeDeliveryCnt;

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyInfoServiceImpl.class);

    @Autowired
    private CompanyInfoDao companyInfoDao;

    @Override
    public List<CompanyInfo> fuzzyQueryCompany(String companyName, int queryCnt, boolean isAll, String customId, String roleGroup) {
        if (roleGroup.equals(UserTypeDict.custom)) {

            if (isAll) {
                return this.companyInfoDao.selectAllCustomCompanyList(companyName, customId);
            } else {
                return this.companyInfoDao.selectCustomCompanyList(companyName, queryCnt, customId);
            }

        } else {
            if (isAll) {
                return this.companyInfoDao.selectAllCompanyList(companyName);
            } else {
                return this.companyInfoDao.selectCompanyList(companyName, queryCnt);
            }

        }

    }

    @Override
    public CompanyDetailInfo queryCompanyDetail(String companyId) {
        return this.companyInfoDao.selectDetailInfo(companyId).isPresent() ? this.companyInfoDao.selectDetailInfo(companyId).get() : null;
    }

    @Override
    public IPage<CompanyInfo> queryCompanyList(String companyId, String customId, String roleGroup, String companyStatus, String registerStatus, int pageNo, int pageSize) {
        if (roleGroup.equals(UserTypeDict.custom)) {
            return this.companyInfoDao.selectCompanyList(companyId, customId, companyStatus, registerStatus, pageNo, pageSize);
        } else {
            return this.companyInfoDao.selectCompanyList(companyId, null, companyStatus, registerStatus, pageNo, pageSize);
        }
    }

    @Override
    public void addCompanyInfo(CompanyDetailVO vo) throws Exception {
        CompanyInfo companyInfo = BusinessConverter.voToCompanyInfo(vo);
        companyInfo.setCompanyId(UUIDUtil.randomUUID32());
        this.setCompanyInfoFiles(vo, companyInfo);
        companyInfo.setFreeDelivery(freeDeliveryCnt);
        this.companyInfoDao.insert(companyInfo);
    }

    @Override
    public void modifyCompanyInfo(CompanyDetailVO vo, String companyId) throws Exception {
        CompanyInfo companyInfo = BusinessConverter.voToCompanyInfo(vo);
        companyInfo.setCompanyId(companyId);
        this.setCompanyInfoFiles(vo, companyInfo);
        this.companyInfoDao.update(companyInfo);

    }

    @Override
    public void modifyCompanyInfo(CompanyModifyVO vo) {
        CompanyInfo companyInfo = BusinessConverter.voToCompanyInfo(vo);
        this.companyInfoDao.update(companyInfo);
    }

    private void setCompanyInfoFiles(CompanyDetailVO vo, CompanyInfo companyInfo) throws Exception {
        if (vo.getBossIdPicUp() != null) {
            String bossIdPicUpFileUrl = this.storeCompanyFile(companyInfo.getCompanyId(), CompanyFileType.BOSS, vo.getBossIdPicUp());
            LOGGER.info("bossIdPicUpFileUrl: {}", bossIdPicUpFileUrl);
            companyInfo.setBossIdPicUp(bossIdPicUpFileUrl);
        }
        if (vo.getBossIdPicBack() != null) {
            String bossIdPicBackFileUrl = this.storeCompanyFile(companyInfo.getCompanyId(), CompanyFileType.BOSS, vo.getBossIdPicBack());
            LOGGER.info("bossIdPicBackFileUrl: {}", bossIdPicBackFileUrl);
            companyInfo.setBossIdPicBack(bossIdPicBackFileUrl);

        }
        if (vo.getCfoIdPicUp() != null) {
            String cfoIdPicUpFileUrl = this.storeCompanyFile(companyInfo.getCompanyId(), CompanyFileType.CFO, vo.getCfoIdPicUp());
            LOGGER.info("cfoIdPicUpFileUrl: {}", cfoIdPicUpFileUrl);
            companyInfo.setCfoIdPicUp(cfoIdPicUpFileUrl);
        }
        if (vo.getCfoIdPicBack() != null) {
            String cfoIdPicBackFileUrl = this.storeCompanyFile(companyInfo.getCompanyId(), CompanyFileType.CFO, vo.getCfoIdPicBack());
            LOGGER.info("cfoIdPicBackFileUrl: {}", cfoIdPicBackFileUrl);
            companyInfo.setCfoIdPicBack(cfoIdPicBackFileUrl);
        }

    }

    //path + companyId + key + file
    private String storeCompanyFile(String companyId, CompanyFileType fileType, MultipartFile file) throws Exception {

        String pathLocation = StringUtils.join(filePath, companyId,
                "/", fileType.name());
        String fileName = FileUtils.multiPartFileWriter(file, pathLocation);
        return StringUtils.join(fileUrlPath, companyId,
                "/", fileType.name(), "/", fileName);

    }

}

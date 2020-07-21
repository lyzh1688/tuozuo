package com.tuozuo.tavern.shuiruyi.service.impl;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
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
import com.tuozuo.tavern.shuiruyi.vo.CompanyDetailVO;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/08/10 <br>
 */
@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {

    @Value("${shuiruyi.file.url.path:http://119.3.19.171/shuiruyi/file/company/file}")
    private String fileUrlPath;
    @Value("${shuiruyi.company.file.path:/mnt/file/company/file/}")
    private String filePath;

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
    public IPage<CompanyInfo> queryCompanyList(String customId, String roleGroup, String companyStatus, String registerStatus, int pageNo, int pageSize) {
        if (roleGroup.equals(UserTypeDict.custom)) {
            return this.companyInfoDao.selectCompanyList(customId, companyStatus, registerStatus, pageNo, pageSize);
        } else {
            return this.companyInfoDao.selectCompanyList(null, companyStatus, registerStatus, pageNo, pageSize);
        }
    }

    @Override
    public void modifyCompanyInfo(CompanyDetailVO vo, String companyId) throws Exception {
        CompanyInfo companyInfo = BusinessConverter.voToCompanyInfo(vo);
        if (Objects.isNull(companyId)) {
            companyInfo.setCompanyId(UUIDUtil.randomUUID32());
        } else {
            companyInfo.setCompanyId(companyId);
        }
        String bossIdPicUpFileUrl = this.storeCompanyFile(companyInfo.getCompanyId(), CompanyFileType.BOSS, vo.getBossInfo().getBossIdPicUp());
        String bossIdPicBackFileUrl = this.storeCompanyFile(companyInfo.getCompanyId(), CompanyFileType.BOSS, vo.getBossInfo().getBossIdPicBack());
        String cfoIdPicUpFileUrl = this.storeCompanyFile(companyInfo.getCompanyId(), CompanyFileType.CFO, vo.getCfoInfo().getCfoIdPicUp());
        String cfoIdPicBackFileUrl = this.storeCompanyFile(companyInfo.getCompanyId(), CompanyFileType.CFO, vo.getCfoInfo().getCfoIdPicBack());
        companyInfo.setBossIdPicUp(bossIdPicUpFileUrl);
        companyInfo.setBossIdPicBack(bossIdPicBackFileUrl);
        companyInfo.setCfoIdPicUp(cfoIdPicUpFileUrl);
        companyInfo.setCfoIdPicBack(cfoIdPicBackFileUrl);
        this.companyInfoDao.insert(companyInfo);

    }

    //path + companyId + key + file
    private String storeCompanyFile(String companyId, CompanyFileType fileType, MultipartFile file) throws Exception {

        String pathLocation = StringUtils.join(filePath, companyId,
                "/", fileType.name());
        FileUtils.multiPartFileWriter(file, pathLocation);
        return StringUtils.join(fileUrlPath, companyId,
                "/", fileType.name(), "/", file.getOriginalFilename());

    }

}

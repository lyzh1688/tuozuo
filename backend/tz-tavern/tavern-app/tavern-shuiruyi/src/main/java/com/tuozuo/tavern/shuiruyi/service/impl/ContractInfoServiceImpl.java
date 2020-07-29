package com.tuozuo.tavern.shuiruyi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.UserTypeDict;
import com.tuozuo.tavern.shuiruyi.dao.ContractInfoDao;
import com.tuozuo.tavern.shuiruyi.model.ContractDetailInfo;
import com.tuozuo.tavern.shuiruyi.model.ContractInfo;
import com.tuozuo.tavern.shuiruyi.model.ContractTemplate;
import com.tuozuo.tavern.shuiruyi.service.ContractInfoService;
import com.tuozuo.tavern.shuiruyi.utils.FileUtils;
import com.tuozuo.tavern.shuiruyi.utils.UUIDUtil;
import com.tuozuo.tavern.shuiruyi.vo.ContractInfoVO;
import com.tuozuo.tavern.shuiruyi.vo.ContractModifyVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/25 <br>
 */
@Service
public class ContractInfoServiceImpl implements ContractInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractInfoServiceImpl.class);

    @Autowired
    private ContractInfoDao contractInfoDao;
    @Value("${shuiruyi.file.url.path:http://119.3.19.171/shuiruyi/file/contract/file/}")
    private String fileUrlPath;
    @Value("${shuiruyi.company.file.path:/mnt/file/contract/file/}")
    private String filePath;

    @Override
    public IPage<ContractDetailInfo> queryContractList(String companyId, int pageNo, int pageSize, String customId, String customGroup) {
        if (customGroup.equals(UserTypeDict.custom)) {
            return this.contractInfoDao.queryContractList(companyId, pageNo, pageSize, customId);
        } else {
            return this.contractInfoDao.queryContractList(companyId, pageNo, pageSize, null);
        }
    }

    @Override
    public void addContractInfo(ContractInfoVO vo) throws Exception {
        String contractId = UUIDUtil.randomUUID32();
        ContractInfo contractInfo = new ContractInfo();
        contractInfo.setContractId(contractId);
        contractInfo.setCompanyId(vo.getCompanyId());
        contractInfo.setContractName(vo.getContractName());
        contractInfo.setCompanyPartyAName(vo.getCompanyPartyAName());
        contractInfo.setCompanyPartyBName(vo.getCompanyPartyBName());
        contractInfo.setInvoicePattern(vo.getInvoicePattern());
        contractInfo.setContractAmount(new BigDecimal(vo.getContractAmount()));
        String fileUrl = this.storeContractFile(contractId, vo.getContractFile());
        contractInfo.setContractFile(fileUrl);
        contractInfo.setContractDate(LocalDateTime.now());
        this.contractInfoDao.insert(contractInfo);
    }

    @Override
    public List<ContractTemplate> queryContractTemplateList() {
        return this.contractInfoDao.selectTemplateList();
    }

    @Override
    public List<ContractInfo> fuzzyQueryContractInfo(String contractStatus, String contractName, int queryCnt, String roleGroup, String customId) {
        if (roleGroup.equals(UserTypeDict.custom)) {
            return this.contractInfoDao.fuzzyQueryContractInfo(contractStatus, contractName, queryCnt, customId);
        } else {
            return this.contractInfoDao.fuzzyQueryContractInfo(contractStatus, contractName, queryCnt, null);
        }
    }

    @Override
    public void modifyContractInfo(ContractModifyVO vo) throws Exception {
        ContractInfo contractInfo = new ContractInfo();
        contractInfo.setContractId(vo.getContractId());
        contractInfo.setContractStatus(vo.getContractStatus());
        contractInfo.setCompanyId(vo.getCompanyId());
        contractInfo.setContractName(vo.getContractName());
        contractInfo.setCompanyPartyAName(vo.getCompanyPartyAName());
        contractInfo.setCompanyPartyBName(vo.getCompanyPartyBName());
        contractInfo.setInvoicePattern(vo.getInvoicePattern());
        contractInfo.setContractAmount(new BigDecimal(vo.getContractAmount()));
        String fileUrl = this.storeContractFile(vo.getContractId(), vo.getContractFile());
        contractInfo.setContractFile(fileUrl);
        this.contractInfoDao.update(contractInfo);
    }

    @Override
    public void auditContractInfo(String contractId, String contractStatus, String remark) {
        ContractInfo contractInfo = new ContractInfo();
        contractInfo.setContractId(contractId);
        contractInfo.setContractStatus(contractStatus);
        contractInfo.setRemark(remark);
        this.contractInfoDao.update(contractInfo);
    }


    //path + contractId + file
    private String storeContractFile(String contractId, MultipartFile file) throws Exception {
        if (file == null) {
            return null;
        }
        String pathLocation = StringUtils.join(filePath, contractId);
        FileUtils.multiPartFileWriter(file, pathLocation);
        return StringUtils.join(fileUrlPath, contractId, "/", file.getOriginalFilename());

    }
}

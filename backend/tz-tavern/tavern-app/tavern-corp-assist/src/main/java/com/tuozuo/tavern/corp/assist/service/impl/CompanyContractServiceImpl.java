package com.tuozuo.tavern.corp.assist.service.impl;

import com.tuozuo.tavern.corp.assist.FileType;
import com.tuozuo.tavern.corp.assist.dao.CompanyContractFlowDao;
import com.tuozuo.tavern.corp.assist.dao.CompanyContractTemplateDao;
import com.tuozuo.tavern.corp.assist.model.CompanyContractFlow;
import com.tuozuo.tavern.corp.assist.model.CompanyContractTemplate;
import com.tuozuo.tavern.corp.assist.service.CompanyContractService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/12/24 <br>
 */
@Service
public class CompanyContractServiceImpl implements CompanyContractService {

    @Autowired
    private CompanyContractTemplateDao companyContractTemplateDao;
    @Autowired
    private CompanyContractFlowDao companyContractFlowDao;


    @Override
    public List<CompanyContractTemplate> queryAllCompanyContractTemplate() {
        return this.companyContractTemplateDao.selectAll();
    }

    @Override
    public void previewContractFile(HttpServletResponse httpServletResponse, String templateId, String flowId) throws Exception {
        String filePath;
        if (StringUtils.isNoneEmpty(templateId)) {
            CompanyContractTemplate companyContractTemplate = this.companyContractTemplateDao.selectById(templateId);
            filePath = companyContractTemplate.getTemplateFilePath();
        } else {
            CompanyContractFlow companyContractFlow = this.companyContractFlowDao.selectById(flowId);
            filePath = companyContractFlow.getContractSignedPath();
        }

        File pdf = new File(filePath);
        FileInputStream fis = new FileInputStream(pdf);
        BufferedInputStream bis = new BufferedInputStream(fis);
        byte[] buffer = new byte[bis.available()];
        bis.read(buffer);

        String fileName = pdf.getName();
        String fileType = FileType.pdf.name();//fileName.substring(fileName.indexOf(".") + 1);

        httpServletResponse.reset();
        // 设置response的Header
        httpServletResponse.addHeader("Content-Disposition",
                "inline;filename=" + new String(fileName.getBytes("GB2312"), "iso8859-1"));
        httpServletResponse.addHeader("Content-Length", "" + buffer.length);

        OutputStream toClient = new BufferedOutputStream(httpServletResponse.getOutputStream());
        httpServletResponse.setContentType("application/" + fileType);
        toClient.write(buffer);
        toClient.flush();
        toClient.close();

    }

}

package com.tuozuo.tavern.corp.assist.service.impl;

import com.tuozuo.tavern.corp.assist.dict.FileType;
import com.tuozuo.tavern.corp.assist.dao.CorporationContractFlowDao;
import com.tuozuo.tavern.corp.assist.dao.CorporationContractTemplateDao;
import com.tuozuo.tavern.corp.assist.model.CorporationContractFlow;
import com.tuozuo.tavern.corp.assist.model.CorporationContractTemplate;
import com.tuozuo.tavern.corp.assist.service.CorporationContractService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/12/24 <br>
 */
@Service
public class CorporationContractServiceImpl implements CorporationContractService {

    @Autowired
    private CorporationContractTemplateDao corporationContractTemplateDao;
    @Autowired
    private CorporationContractFlowDao corporationContractFlowDao;


    @Override
    public List<CorporationContractTemplate> queryAllCompanyContractTemplate() {
        return this.corporationContractTemplateDao.selectAll();
    }

    @Override
    public void previewContractFile(HttpServletResponse httpServletResponse, String templateId, String flowId) throws Exception {
        String filePath;
        if (StringUtils.isNoneEmpty(templateId)) {
            CorporationContractTemplate corporationContractTemplate = this.corporationContractTemplateDao.selectById(templateId);
            filePath = corporationContractTemplate.getTemplateFilePath();
        } else {
            CorporationContractFlow corporationContractFlow = this.corporationContractFlowDao.selectById(flowId);
            filePath = corporationContractFlow.getContractSignedPath();
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

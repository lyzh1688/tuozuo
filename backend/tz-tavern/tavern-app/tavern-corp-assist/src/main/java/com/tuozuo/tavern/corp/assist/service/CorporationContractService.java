package com.tuozuo.tavern.corp.assist.service;

import com.tuozuo.tavern.corp.assist.model.CorporationContractTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/12/24 <br>
 */
public interface CorporationContractService {

    List<CorporationContractTemplate> queryAllCompanyContractTemplate();

    void previewContractFile(HttpServletResponse httpServletResponse, String templateId, String flowId) throws FileNotFoundException, Exception;
}

package com.tuozuo.tavern.organ.biz.service;


import com.tuozuo.tavern.organ.biz.exeception.ExecuteException;
import com.tuozuo.tavern.organ.biz.model.CompanyName;

import java.io.IOException;
import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/20 <br>
 */
public interface CompanyNameService {

    List<CompanyName> queryCompanyName(
            String source,
            String area,
            String industry,
            String preferWord,
            String isTwoWords,
            String type) throws ExecuteException, IOException;





}

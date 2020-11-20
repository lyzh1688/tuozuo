package com.tuozuo.tavern.organ.biz.service;


import com.tuozuo.tavern.organ.biz.model.CompanyName;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/20 <br>
 */
public interface CompanyNameService {

    List<CompanyName> queryCompanyName(String area,
                                       String industry,
                                       String source,
                                       String preferWord,
                                       Boolean isTwoWords);

}

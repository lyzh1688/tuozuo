package com.tuozuo.tavern.shuiruyi.endpoint;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tavern.common.TavernResponse;
import com.tavern.shuiruyi.model.CompanyInfo;
import com.tuozuo.tavern.shuiruyi.convert.BusinessConverter;
import com.tuozuo.tavern.shuiruyi.dto.BusinessDictDTO;
import com.tuozuo.tavern.shuiruyi.service.CompanyInfoService;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2019/08/10 <br>
 */
@RestController
@RequestMapping("/shuiruyi/company")
public class BizControlEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(BizControlEndpoint.class);

    @Autowired
    private CompanyInfoService companyInfoService;
    @Autowired
    private BusinessConverter businessConverter;

    /**
     * 我的个独公司模糊查询
     */
    @GetMapping()
    public TavernResponse queryCompanyDict(@RequestParam(name = "companyName", defaultValue = "") String companyName,
            @RequestParam(name = "queryCnt", defaultValue = "20") int queryCnt,
            @RequestParam(name = "showAll", defaultValue = "0") boolean showAll) {
        try {
            List<CompanyInfo> companyInfoList = this.companyInfoService.fuzzyQueryCompany(companyName, queryCnt, showAll);
            List<BusinessDictDTO> businessDictList = companyInfoList.stream()
                    .map(this.businessConverter::companyInfoToDTO)
                    .collect(Collectors.toList());

            return TavernResponse.success(businessDictList);
        } catch (Exception e) {
            LOGGER.error("[我的个独公司模糊查询] failed", e);
            return TavernResponse.failure(e.getMessage());
        }
    }

    /**
     * 我的个独公司详情
     */
    @GetMapping("{companyId}")
    public TavernResponse queryCompanyDetail(@PathVariable("companyId") String companyId) {
        try {

            CompanyInfo companyInfo = this.companyInfoService.queryCompanyDetail(companyId);


            return TavernResponse.success();
        } catch (Exception e) {
            LOGGER.error("[我的个独公司详情] failed", e);
            return TavernResponse.failure(e.getMessage());
        }
    }

}

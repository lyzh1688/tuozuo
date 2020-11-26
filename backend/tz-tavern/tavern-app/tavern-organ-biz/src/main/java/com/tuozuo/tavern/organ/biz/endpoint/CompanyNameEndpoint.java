package com.tuozuo.tavern.organ.biz.endpoint;

import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.organ.biz.dto.BuildNameDTO;
import com.tuozuo.tavern.organ.biz.model.CompanyName;
import com.tuozuo.tavern.organ.biz.model.CompanyVerifyResult;
import com.tuozuo.tavern.organ.biz.service.CompanyNameService;
import com.tuozuo.tavern.organ.biz.vo.BuildNameVO;
import com.tuozuo.tavern.organ.biz.vo.VerifyNameVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/11/20 <br>
 */
@RestController
@RequestMapping("/tuozuo/organbiz/v1/name")
public class CompanyNameEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyNameEndpoint.class);
    @Autowired
    private CompanyNameService companyNameService;


    @GetMapping("/creation")
    public TavernResponse createCompanyName(@ModelAttribute @Valid BuildNameVO vo) {
        try {

            List<CompanyName> resultList = this.companyNameService.queryCompanyName(vo.getSource(), vo.getArea(), vo.getIndustry(), vo.getPreferWord(), vo.getIsTwoWords(), vo.getType());
            List<CompanyName> pageList = resultList.stream().skip((vo.getPageNo() - 1) * vo.getPageSize()).limit(vo.getPageSize()).collect(Collectors.toList());
            BuildNameDTO dto = new BuildNameDTO();
            dto.setNames(pageList);
            dto.setTotalNum(resultList.size());
            return TavernResponse.ok(dto);
        } catch (Exception e) {
            LOGGER.error("[公司起名] failed", e);
            return TavernResponse.bizFailure("系统查询异常，请稍后再试");
        }
    }

    @GetMapping("/verification")
    public TavernResponse verifyCompanyName(@ModelAttribute @Valid VerifyNameVO vo) {
        try {
            CompanyVerifyResult result = this.companyNameService.queryCompanyResult(vo.getArea(), vo.getName(), vo.getIndustry());
            return TavernResponse.ok(result);
        } catch (Exception e) {
            LOGGER.error("[公司核名] failed", e);
            return TavernResponse.bizFailure("系统查询异常，请稍后再试");
        }
    }
}

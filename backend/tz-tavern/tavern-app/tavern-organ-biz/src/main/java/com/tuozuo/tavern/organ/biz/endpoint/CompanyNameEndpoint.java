package com.tuozuo.tavern.organ.biz.endpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.organ.biz.dict.CompanyPropertyType;
import com.tuozuo.tavern.organ.biz.dto.BuildNameDTO;
import com.tuozuo.tavern.organ.biz.dto.CompanyPropertyDTO;
import com.tuozuo.tavern.organ.biz.model.CompanyName;
import com.tuozuo.tavern.organ.biz.model.CompanyNameProperty;
import com.tuozuo.tavern.organ.biz.model.CompanyVerifyResult;
import com.tuozuo.tavern.organ.biz.service.CompanyNameService;
import com.tuozuo.tavern.organ.biz.service.CompanyPropertyService;
import com.tuozuo.tavern.organ.biz.vo.BuildNameVO;
import com.tuozuo.tavern.organ.biz.vo.VerifyNameVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private CompanyPropertyService companyPropertyService;


    @GetMapping("/creation")
    public TavernResponse createCompanyName(@ModelAttribute @Valid BuildNameVO vo) {
        try {

            List<CompanyName> resultList = this.companyNameService.queryCompanyName(vo.getSource(), vo.getArea(), vo.getIndustry(), vo.getPreferWord(), vo.getIsTwoWords(), vo.getType());
            List<CompanyName> pageList = resultList.stream().skip((vo.getPageNo() - 1) * vo.getPageSize()).limit(vo.getPageSize()).collect(Collectors.toList());
            BuildNameDTO dto = new BuildNameDTO();
//            dto.setNames(creat());
            dto.setNames(pageList);
            dto.setTotalNum(resultList.size());
            return TavernResponse.ok(dto);
        } catch (Exception e) {
            LOGGER.error("[公司起名] failed", e);
            return TavernResponse.bizFailure("系统查询异常，请稍后再试");
        }
    }

    private List<CompanyName> creat() {
        List<CompanyName> companyNameList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            CompanyName companyName = new CompanyName();
            companyName.setName("天邦之");
            companyName.setFullName("上海天邦之电子商务有限责任公司");
            companyName.setReference("念餘「邦」「之」橫陷兮，宗鬼神「之」無次。");
            List<Integer> list = Lists.newArrayList();
            list.add(4);
            list.add(11);
            list.add(4);
            companyName.setStrokeNums(list);
            companyNameList.add(companyName);
        }

        return companyNameList;
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

    @GetMapping("/dict/{dict}")
    public TavernResponse queryCompanyDict(@PathVariable("dict") String dict) {
        try {
            List<CompanyNameProperty> properties = this.companyPropertyService.queryCompanyProperty(CompanyPropertyType.valueOf(dict));
            Map<String, List<CompanyNameProperty>> map = properties.parallelStream().collect(Collectors.groupingBy(CompanyNameProperty::getSuperClass));
            List<CompanyPropertyDTO> companyPropertyDTOList = map.entrySet()
                    .stream()
                    .map(entry -> {
                        List<String> companyNameProperties = entry.getValue()
                                .stream()
                                .map(CompanyNameProperty::getSubClass)
                                .filter(c -> c != null && !c.equals(""))
                                .collect(Collectors.toList());
                        CompanyPropertyDTO companyPropertyDTO = new CompanyPropertyDTO();
                        companyPropertyDTO.setSuperClass(entry.getKey());
                        companyPropertyDTO.setSubClass(companyNameProperties);
                        return companyPropertyDTO;
                    }).collect(Collectors.toList());


            return TavernResponse.ok(companyPropertyDTOList);
        } catch (Exception e) {
            LOGGER.error("[公司起名字典] failed", e);
            return TavernResponse.bizFailure("系统查询异常，请稍后再试");
        }
    }
}

package com.tuozuo.tavern.xinruyi.endpoint;

import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.xinruyi.convert.ModelMapConverterFactory;
import com.tuozuo.tavern.xinruyi.dto.IndustryTypeDTO;
import com.tuozuo.tavern.xinruyi.dto.IndustryTypeListDTO;
import com.tuozuo.tavern.xinruyi.model.HotProjectInfo;
import com.tuozuo.tavern.xinruyi.model.IndustryProjectInfo;
import com.tuozuo.tavern.xinruyi.service.BusinessDictService;
import com.tuozuo.tavern.xinruyi.service.ProjectInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/10/11 <br>
 */
@RestController
@RequestMapping("/tuozuo/xinruyi/applet/v1")
public class AppletInfoEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppletInfoEndpoint.class);

    @Autowired
    private ProjectInfoService projectInfoService;
    @Autowired
    private BusinessDictService businessDictService;
    @Autowired
    private ModelMapConverterFactory converter;

    /**
     * 查询热门项目
     */
    @GetMapping("/project/hotProject")
    public TavernResponse queryHotProjects() {
        try {
            List<HotProjectInfo> hotProjectInfos = this.projectInfoService.queryHotProjects();
            return TavernResponse.ok(hotProjectInfos);
        } catch (Exception e) {
            LOGGER.error("[查询热门项目] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 行业分类
     */
    @GetMapping("/market")
    public TavernResponse queryMarketIndustry() {
        try {
            List<IndustryTypeDTO> industryTypeDTOList = this.businessDictService.queryIndustryType()
                    .stream()
                    .map(this.converter::modelToIndustryTypeDTO)
                    .collect(Collectors.toList());
            IndustryTypeListDTO dto = new IndustryTypeListDTO();
            dto.setIndustries(industryTypeDTOList);
            return TavernResponse.ok(dto);
        } catch (Exception e) {
            LOGGER.error("[行业分类] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 项目列表翻页接口
     */
    @GetMapping("/market/project/page")
    public TavernResponse queryIndustryProjects(@RequestParam(value = "industryId") String industryId,
                                                @RequestParam(value = "projectId", required = false) String projectId,
                                                @RequestParam(value = "publishDate", required = false) String publishDate) {
        try {
            List<IndustryProjectInfo> industryProjectInfoList = this.projectInfoService.queryIndustryProject(projectId, publishDate, industryId);
            return TavernResponse.ok(industryProjectInfoList);
        } catch (Exception e) {
            LOGGER.error("[项目列表翻页接口] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 市场页面项目搜索
     */
    @GetMapping("/market/project")
    public TavernResponse fuzzyQueryIndustryProjects(@RequestParam(value = "projectName") String projectName,
                                                     @RequestParam(name = "queryCnt", defaultValue = "20") int queryCnt) {
        try {
            List<IndustryProjectInfo> industryProjectInfoList = this.projectInfoService.queryIndustryProjectByName(projectName, queryCnt);
            return TavernResponse.ok(industryProjectInfoList);
        } catch (Exception e) {
            LOGGER.error("[市场页面项目搜索] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }


}

package com.tuozuo.tavern.xinruyi.endpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernRequestAuthFields;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.xinruyi.convert.ModelConverterFactory;
import com.tuozuo.tavern.xinruyi.convert.ModelMapConverterFactory;
import com.tuozuo.tavern.xinruyi.dto.ProjectDictDTO;
import com.tuozuo.tavern.xinruyi.dto.ProjectInfoDTO;
import com.tuozuo.tavern.xinruyi.dto.ProjectInfoListDTO;
import com.tuozuo.tavern.xinruyi.model.ProjectInfo;
import com.tuozuo.tavern.xinruyi.service.ProjectInfoService;
import com.tuozuo.tavern.xinruyi.vo.ProjectListVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/10/28 <br>
 */
@RestController
@RequestMapping("/tuozuo/xinruyi/v1/portal")
public class PartalInfoEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(PartalInfoEndpoint.class);

    @Autowired
    private ProjectInfoService projectInfoService;
    @Autowired
    private ModelMapConverterFactory converter;

    /**
     * 网站项目列表
     */
    @GetMapping("/project/list")
    public TavernResponse queryPartalProjectList(@ModelAttribute @Valid ProjectListVo vo) {
        try {
            ProjectInfoListDTO dto = new ProjectInfoListDTO();
            IPage<ProjectInfo> page = this.projectInfoService.queryProjectInfo(vo);
            List<ProjectInfoDTO> list = page.getRecords()
                    .stream()
                    .map(ModelConverterFactory::modelToProjectInfoDTO)
                    .collect(Collectors.toList());

            dto.setProjects(list);
            dto.setTotal((int) page.getTotal());

            return TavernResponse.ok(dto);
        } catch (Exception e) {
            LOGGER.error("[项目列表查询] failed", e);
            return TavernResponse.bizFailure("项目列表查询异常");
        }
    }

    /**
     * 我的项目模糊搜索
     */
    @GetMapping("/project")
    public TavernResponse queryProjectDict(@RequestParam(value = "projectName", required = false, defaultValue = "") String projectName,
                                           @RequestParam(value = "queryCnt", required = false, defaultValue = "20") int queryCnt,
                                           @RequestParam(value = "all", required = false) boolean all) {
        try {
            List<ProjectDictDTO> list = this.projectInfoService.queryProjectInfo(projectName, queryCnt, all)
                    .stream()
                    .map(projectInfo -> {
                        ProjectDictDTO dictDTO = new ProjectDictDTO();
                        dictDTO.setId(projectInfo.getProjectId());
                        dictDTO.setName(projectInfo.getProjectName());
                        dictDTO.setStatus(projectInfo.getStatus());
                        return dictDTO;
                    }).collect(Collectors.toList());
            return TavernResponse.ok(list);
        } catch (Exception e) {
            LOGGER.error("[项目搜索] failed", e);
            return TavernResponse.bizFailure("项目搜索异常");
        }
    }

}

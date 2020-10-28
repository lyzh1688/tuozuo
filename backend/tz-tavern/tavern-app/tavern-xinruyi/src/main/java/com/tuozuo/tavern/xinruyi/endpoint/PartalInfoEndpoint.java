package com.tuozuo.tavern.xinruyi.endpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.xinruyi.convert.ModelConverterFactory;
import com.tuozuo.tavern.xinruyi.convert.ModelMapConverterFactory;
import com.tuozuo.tavern.xinruyi.dto.ProjectInfoDTO;
import com.tuozuo.tavern.xinruyi.dto.ProjectInfoListDTO;
import com.tuozuo.tavern.xinruyi.model.ProjectInfo;
import com.tuozuo.tavern.xinruyi.service.ProjectInfoService;
import com.tuozuo.tavern.xinruyi.vo.ProjectListVo;
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
    @GetMapping("/list")
    public TavernResponse queryPartalProjectList(@ModelAttribute @Valid ProjectListVo vo) {
        try {
            ProjectInfoListDTO dto = new ProjectInfoListDTO();
            IPage<ProjectInfo> page = this.projectInfoService.queryProjectInfo(vo, null);
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

}

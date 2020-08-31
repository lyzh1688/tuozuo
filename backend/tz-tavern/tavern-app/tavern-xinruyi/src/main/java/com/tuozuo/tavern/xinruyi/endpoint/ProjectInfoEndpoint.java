package com.tuozuo.tavern.xinruyi.endpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernRequestAuthFields;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.xinruyi.convert.ModelConverter;
import com.tuozuo.tavern.xinruyi.convert.ModelMapConverter;
import com.tuozuo.tavern.xinruyi.dto.*;
import com.tuozuo.tavern.xinruyi.model.*;
import com.tuozuo.tavern.xinruyi.service.ProjectInfoService;
import com.tuozuo.tavern.xinruyi.vo.PageVO;
import com.tuozuo.tavern.xinruyi.vo.ProjectListVo;
import com.tuozuo.tavern.xinruyi.vo.ProjectStaffAddVO;
import com.tuozuo.tavern.xinruyi.vo.ProjectStaffModifyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/29 <br>
 */
@RestController
@RequestMapping("/tuozuo/xinruyi/v1/project")
public class ProjectInfoEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectInfoEndpoint.class);

    @Autowired
    private ProjectInfoService projectInfoService;
    @Autowired
    private ModelMapConverter converter;

    /**
     * 项目列表
     */
    @GetMapping("/list")
    public TavernResponse queryProjectList(@ModelAttribute @Valid ProjectListVo vo) {
        try {
            ProjectInfoListDTO dto = new ProjectInfoListDTO();
            IPage<ProjectInfo> page = this.projectInfoService.queryProjectInfo(vo);
            List<ProjectInfoDTO> list = page.getRecords()
                    .stream()
                    .map(ModelConverter::modelToProjectInfoDTO)
                    .collect(Collectors.toList());

            dto.setProjects(list);
            dto.setTotal((int) page.getTotal());

            return TavernResponse.ok(dto);
        } catch (Exception e) {
            LOGGER.error("[项目列表查询] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 我的项目模糊搜索
     */
    @GetMapping("")
    public TavernResponse queryProjects(@RequestParam(value = "projectName", required = false) String projectName,
                                        @RequestParam(value = "queryCnt", required = false, defaultValue = "20") int queryCnt,
                                        @RequestParam(value = "all", required = false) boolean all,
                                        @RequestHeader(TavernRequestAuthFields.USER_ID) String companyId) {
        try {
            List<BusinessDictDTO> list = this.projectInfoService.queryProjectInfo(companyId, projectName, queryCnt, all)
                    .stream()
                    .map(projectInfo -> {
                        BusinessDictDTO dictDTO = new BusinessDictDTO();
                        dictDTO.setId(projectInfo.getProjectId());
                        dictDTO.setName(projectInfo.getProjectName());
                        return dictDTO;
                    }).collect(Collectors.toList());
            return TavernResponse.ok(list);
        } catch (Exception e) {
            LOGGER.error("[我的项目模糊搜索] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 项目人员
     */
    @GetMapping("/staff/{projectId}")
    public TavernResponse queryProjectStaffList(@PathVariable("projectId") String projectId,
                                                @ModelAttribute @Valid PageVO pageVO,
                                                @RequestHeader(TavernRequestAuthFields.USER_ID) String companyId) {
        try {
            IPage<ProjectStaffInfo> page = this.projectInfoService.queryProjectStaffInfo(pageVO.getPageNo(),
                    pageVO.getPageSize(), companyId, projectId);
            ProjectStaffInfoListDTO staffSalaryListDTO = new ProjectStaffInfoListDTO();
            staffSalaryListDTO.setStaffs(page.getRecords());
            staffSalaryListDTO.setTotal((int) page.getTotal());

            return TavernResponse.ok(staffSalaryListDTO);
        } catch (Exception e) {
            LOGGER.error("[项目人员] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }


    /**
     * 项目人员新增
     */
    @PostMapping("/staff/{projectId}")
    public TavernResponse addProjectStaff(@PathVariable("projectId") String projectId,
                                          @RequestBody ProjectStaffAddVO vo) {
        try {
            ProjectStaff projectStaff = ModelConverter.modifyVoToProjectStaff(vo, projectId);
            this.projectInfoService.addProjectStaff(projectStaff);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[项目人员新增] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 项目人员修改
     */
    @PutMapping("/staff/{staffId}")
    public TavernResponse modifyProjectStaff(@PathVariable("staffId") String staffId,
                                             @RequestBody ProjectStaffModifyVO vo) {
        try {
            ProjectStaff projectStaff = ModelConverter.modifyVoToProjectStaff(vo, staffId);
            this.projectInfoService.modifyProjectStaff(projectStaff);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[项目人员修改] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 项目人员裁员
     */
    @DeleteMapping("/staff/{staffId}")
    public TavernResponse delProjectStaff(@PathVariable("staffId") String staffId,
                                          @RequestParam("projectId") String projectId) {
        try {
            this.projectInfoService.delProjectStaff(staffId, projectId);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[项目人员修改] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }


}

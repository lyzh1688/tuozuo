package com.tuozuo.tavern.xinruyi.endpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernRequestAuthFields;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.xinruyi.convert.ModelConverterFactory;
import com.tuozuo.tavern.xinruyi.convert.ModelMapConverterFactory;
import com.tuozuo.tavern.xinruyi.dto.*;
import com.tuozuo.tavern.xinruyi.model.ProjectInfo;
import com.tuozuo.tavern.xinruyi.model.ProjectStaff;
import com.tuozuo.tavern.xinruyi.model.ProjectStaffInfo;
import com.tuozuo.tavern.xinruyi.service.ProjectInfoService;
import com.tuozuo.tavern.xinruyi.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private ModelMapConverterFactory converter;

    /**
     * 项目列表
     */
    @GetMapping("/list")
    public TavernResponse queryProjectList(@ModelAttribute @Valid ProjectListVo vo,
                                           @RequestHeader(TavernRequestAuthFields.USER_ID) String companyId) {
        try {
            ProjectInfoListDTO dto = new ProjectInfoListDTO();
            IPage<ProjectInfo> page = this.projectInfoService.queryProjectInfo(vo, companyId);
            List<ProjectInfoDTO> list = page.getRecords()
                    .stream()
                    .map(ModelConverterFactory::modelToProjectInfoDTO)
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
    public TavernResponse queryProjectDict(@RequestParam(value = "projectName", required = false,defaultValue = "") String projectName,
                                           @RequestParam(value = "queryCnt", required = false, defaultValue = "20") int queryCnt,
                                           @RequestParam(value = "all", required = false) boolean all,
                                           @RequestHeader(TavernRequestAuthFields.USER_ID) String companyId,
                                           @RequestHeader(TavernRequestAuthFields.ROLE_GROUP) String roleGroup
    ) {
        try {
            List<BusinessDictDTO> list = this.projectInfoService.queryProjectInfo(companyId, projectName, queryCnt, all, roleGroup)
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
    public TavernResponse queryProjectStaff(@PathVariable("projectId") String projectId,
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
            ProjectStaff projectStaff = ModelConverterFactory.modifyVoToProjectStaff(vo, projectId);
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
            ProjectStaff projectStaff = ModelConverterFactory.modifyVoToProjectStaff(vo, staffId);
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
                                          @RequestBody ProjectStaffDelVO vo) {
        try {
            this.projectInfoService.delProjectStaff(vo.getProjectId(), staffId);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[项目人员修改] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }


    /**
     * 项目发布
     */
    @PostMapping("")
    public TavernResponse addProject(@ModelAttribute @Valid ProjectAddVO vo,
                                     @RequestHeader(TavernRequestAuthFields.USER_ID) String companyId,
                                     @RequestParam(name = "projectFile") MultipartFile projectFile) {
        try {
            vo.setProjectFile(projectFile);
            this.projectInfoService.addProjectInfo(vo, companyId);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[项目发布] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 项目详情
     */
    @GetMapping("/detail/{projectId}")
    public TavernResponse queryProjectDetail(@PathVariable("projectId") String projectId) {
        try {

            ProjectInfo projectInfo = this.projectInfoService.queryProjectDetail(projectId);
            ProjectDetailDTO projectDetailDTO = ModelConverterFactory.modelToProjectDetailInfo(projectInfo);
            return TavernResponse.ok(projectDetailDTO);
        } catch (Exception e) {
            LOGGER.error("[项目详情] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 项目修改
     */
    @PutMapping("/{projectId}")
    public TavernResponse modifyProject(@PathVariable("projectId") String projectId,
                                        @ModelAttribute ProjectModifyVO vo,
                                        @RequestHeader(TavernRequestAuthFields.USER_ID) String companyId,
                                        @RequestParam(name = "projectFile", required = false) MultipartFile projectFile) {
        try {
            if (projectFile != null) {
                vo.setProjectFile(projectFile);
            }
            vo.setProjectId(projectId);
            this.projectInfoService.modifyProjectInfo(vo, companyId);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[项目修改] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 项目状态修改
     */
    @PutMapping("/status/{projectId}")
    public TavernResponse modifyProjectStatus(@PathVariable("projectId") String projectId,
                                              @RequestBody @Valid ProjecStatusVO vo) {
        try {
            this.projectInfoService.modifyProjectStatus(vo.getStatus(), projectId);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[项目状态修改] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }
}

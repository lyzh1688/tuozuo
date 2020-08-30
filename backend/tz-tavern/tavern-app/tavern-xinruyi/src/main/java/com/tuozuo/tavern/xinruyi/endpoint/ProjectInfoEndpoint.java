package com.tuozuo.tavern.xinruyi.endpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernRequestAuthFields;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.xinruyi.convert.ModelConverter;
import com.tuozuo.tavern.xinruyi.convert.ModelMapConverter;
import com.tuozuo.tavern.xinruyi.dto.ProjectInfoListDTO;
import com.tuozuo.tavern.xinruyi.dto.ProjectStaffInfoListDTO;
import com.tuozuo.tavern.xinruyi.dto.StaffResourcePoolDTO;
import com.tuozuo.tavern.xinruyi.dto.StaffResourcePoolListDTO;
import com.tuozuo.tavern.xinruyi.model.ProjectInfo;
import com.tuozuo.tavern.xinruyi.model.ProjectStaff;
import com.tuozuo.tavern.xinruyi.model.ProjectStaffInfo;
import com.tuozuo.tavern.xinruyi.model.StaffResourcePool;
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
    public TavernResponse queryProjectList(@ModelAttribute @Valid ProjectListVo vo,
                                           @RequestHeader(TavernRequestAuthFields.USER_ID) String companyId) {
        try {
            ProjectInfoListDTO dto = new ProjectInfoListDTO();
            IPage<ProjectInfo> page = this.projectInfoService.queryProjectInfo(vo.getPageNo(), vo.getPageSize(), companyId);

            return TavernResponse.ok(dto);
        } catch (Exception e) {
            LOGGER.error("[人力资源池员工查询] failed", e);
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
    @PostMapping("/{projectId}")
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


}

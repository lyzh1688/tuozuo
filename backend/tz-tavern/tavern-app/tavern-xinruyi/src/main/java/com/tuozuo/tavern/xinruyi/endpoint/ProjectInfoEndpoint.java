package com.tuozuo.tavern.xinruyi.endpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernRequestAuthFields;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.xinruyi.convert.ModelConverter;
import com.tuozuo.tavern.xinruyi.convert.ModelMapConverter;
import com.tuozuo.tavern.xinruyi.dto.ProjectStaffInfoListDTO;
import com.tuozuo.tavern.xinruyi.model.ProjectStaff;
import com.tuozuo.tavern.xinruyi.model.ProjectStaffInfo;
import com.tuozuo.tavern.xinruyi.service.ProjectInfoService;
import com.tuozuo.tavern.xinruyi.vo.PageVO;
import com.tuozuo.tavern.xinruyi.vo.ProjectStaffAddVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
            ProjectStaff projectStaff = ModelConverter.addVoToProjectStaff(vo, projectId);
            this.projectInfoService.addProjectStaff(projectStaff);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[项目人员新增] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }


        
    }





















}

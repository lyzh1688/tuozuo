package com.tuozuo.tavern.xinruyi.endpoint;

import com.tuozuo.tavern.common.protocol.TavernRequestAuthFields;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.xinruyi.convert.ModelMapConverterFactory;
import com.tuozuo.tavern.xinruyi.dto.*;
import com.tuozuo.tavern.xinruyi.model.*;
import com.tuozuo.tavern.xinruyi.service.*;
import com.tuozuo.tavern.xinruyi.utils.DateUtils;
import com.tuozuo.tavern.xinruyi.vo.ProjectParticipateVO;
import com.tuozuo.tavern.xinruyi.vo.ProjectQuitVO;
import com.tuozuo.tavern.xinruyi.vo.WorkerAuthVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private WorkerInfoService workerInfoService;
    @Autowired
    private PaymentInfoService paymentInfoService;
    @Autowired
    private EventInfoService eventInfoService;
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
                                                     @RequestParam(value = "projectId", required = false) String projectId,
                                                     @RequestParam(value = "publishDate", required = false) String publishDate) {
        try {
            List<IndustryProjectInfo> industryProjectInfoList = this.projectInfoService.queryIndustryProjectByName(projectName, projectId,publishDate);
            return TavernResponse.ok(industryProjectInfoList);
        } catch (Exception e) {
            LOGGER.error("[市场页面项目搜索] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 项目经历
     */
    @GetMapping("/project/experience")
    public TavernResponse queryExperienceProjects(@RequestHeader(value = "userId",defaultValue = "1234") String registerId,
                                                  @RequestParam(value = "projectId", required = false) String projectId,
                                                  @RequestParam(value = "publishDate", required = false) String publishDate,
                                                  @RequestParam(value = "status") String status) {
        try {
            List<ProjectInfo> projectInfoList = this.projectInfoService.queryExperienceProjects(projectId, publishDate, registerId, status);
            List<ProjectExperienceDTO> experienceDTOList = projectInfoList.stream()
                    .map(projectInfo -> {
                        ProjectExperienceDTO dto = this.converter.modelToProjectExperienceDTO(projectInfo);
                        dto.setPublishDate(DateUtils.formatDate(projectInfo.getPublishDate(), DateUtils.DEFAULT_SIMPLE_8__FORMATTER));
                        return dto;
                    }).collect(Collectors.toList());
            return TavernResponse.ok(experienceDTOList);
        } catch (Exception e) {
            LOGGER.error("[项目经历] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 项目详情
     */
    @GetMapping("/project/experience/{projectId}")
    public TavernResponse queryExperienceProjectDetail(@RequestHeader(value = "userId",defaultValue = "1234") String registerId,
                                                       @PathVariable("projectId") String projectId,
                                                       @RequestParam(value = "paymentId", required = false) String paymentId,
                                                       @RequestParam(value = "releaseDate", required = false) String payDate) {
        try {
            ProjectExperienceDetailDTO detailDTO = this.projectInfoService.queryProjectExperienceDetail(registerId, projectId, paymentId, payDate);
            return TavernResponse.ok(detailDTO);
        } catch (Exception e) {
            LOGGER.error("[项目详情] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 我的概览
     */
    @GetMapping("/custom/overview")
    public TavernResponse queryMyInfo(@RequestHeader(value = "userId",defaultValue = "1234") String registerId) {
        try {
            WorkerSummaryInfo workerSummaryInfo = this.workerInfoService.queryWorkerSumInfo(registerId);
            return TavernResponse.ok(workerSummaryInfo);
        } catch (Exception e) {
            LOGGER.error("[我的概览] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 收入记录
     */
    @GetMapping("/custom/salary")
    public TavernResponse queryMySalaryRecord(@RequestHeader(value = "userId",defaultValue = "1234") String registerId,
                                              @RequestParam(value = "paymentId", required = false) String paymentId,
                                              @RequestParam(value = "releaseDate", required = false) String payDate) {
        try {
            List<ProjectPaymentDetail> projectPaymentDetails = this.paymentInfoService.queryProjectPaymentRecord(registerId, null, paymentId, payDate);
            return TavernResponse.ok(projectPaymentDetails);
        } catch (Exception e) {
            LOGGER.error("[收入记录] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 实名认证
     */
    @PostMapping("/custom/identification")
    public TavernResponse workerAuth(@ModelAttribute WorkerAuthVO vo,
                                     @RequestHeader(value = "userId",defaultValue = "1234") String registerId,
                                     @RequestParam(name = "video") MultipartFile video,
                                     @RequestParam(name = "idPicUp") MultipartFile idPicUp,
                                     @RequestParam(name = "idPicDown") MultipartFile idPicDown
    ) {
        try {
            this.setWorkAuthInfo(vo, registerId, video, idPicUp, idPicDown);
            this.workerInfoService.addWorker(vo);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[实名认证] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 退出项目
     */
    @PostMapping("/custom/quit")
    public TavernResponse quiteProject(@RequestBody ProjectQuitVO vo,
                                       @RequestHeader(value = "userId",defaultValue = "1234") String registerId
    ) {
        try {
            this.workerInfoService.quitProject(registerId, vo.getProjectId(), vo.getReason());
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[退出项目] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 加入项目
     */
    @PostMapping("/project/participation")
    public TavernResponse participateProject(@RequestBody ProjectParticipateVO vo,
                                             @RequestHeader(value = "userId",defaultValue = "1234") String registerId
    ) {
        try {
            vo.setRegisterId(registerId);
            this.projectInfoService.applyForProject(vo);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[加入项目] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 申请记录
     */
    @GetMapping("/custom/apply/record")
    public TavernResponse queryApplyRecord(@RequestHeader(value = "userId",defaultValue = "1234") String registerId,
                                           @RequestParam(value = "eventId", required = false) String eventId,
                                           @RequestParam(value = "eventDate", required = false) String eventDate) {
        try {
            List<EventFinishList> eventFinishLists = this.eventInfoService.queryEventRecords(registerId, eventId, eventDate);
            List<WorkerApplyRecord> applyRecords = eventFinishLists.stream()
                    .map(event -> {
                        WorkerApplyRecord record =  this.converter.modelToWorkerApplyRecord(event);
                        record.setEventDate(DateUtils.formatDateTime(event.getEventDate(),DateUtils.DEFAULT_DATETIME_FORMATTER));
                        record.setStatusDesc();
                        return record;
                    })
                    .collect(Collectors.toList());
            return TavernResponse.ok(applyRecords);
        } catch (Exception e) {
            LOGGER.error("[申请记录] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    private void setWorkAuthInfo(WorkerAuthVO vo, String registerId, MultipartFile video,
                                 MultipartFile idPicUp,
                                 MultipartFile idPicDown) {
        vo.setRegisterId(registerId);
        vo.setVideo(video);
        vo.setIdPicUp(idPicUp);
        vo.setIdPicDown(idPicDown);
    }

}

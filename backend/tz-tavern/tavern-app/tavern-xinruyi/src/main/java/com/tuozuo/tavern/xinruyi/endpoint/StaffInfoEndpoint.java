package com.tuozuo.tavern.xinruyi.endpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuozuo.tavern.common.protocol.TavernRequestAuthFields;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.xinruyi.convert.ModelConverter;
import com.tuozuo.tavern.xinruyi.dto.StaffResourcePoolDTO;
import com.tuozuo.tavern.xinruyi.dto.StaffResourcePoolListDTO;
import com.tuozuo.tavern.xinruyi.dto.StaffSalaryListDTO;
import com.tuozuo.tavern.xinruyi.model.StaffResourcePool;
import com.tuozuo.tavern.xinruyi.model.StaffSalaryInfo;
import com.tuozuo.tavern.xinruyi.service.StaffInfoService;
import com.tuozuo.tavern.xinruyi.vo.SalaryHistoryVO;
import com.tuozuo.tavern.xinruyi.vo.StaffInfoVO;
import com.tuozuo.tavern.xinruyi.vo.StaffModifyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/8/27 <br>
 */
@RestController
@RequestMapping("/tuozuo/xinruyi/v1/staff")
public class StaffInfoEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(StaffInfoEndpoint.class);

    @Autowired
    private StaffInfoService staffInfoService;
    @Autowired
    private ModelConverter converter;

    /**
     * 人力资源池员工查询
     */
    @GetMapping("/list")
    public TavernResponse queryStaffPoolList(@RequestParam(name = "pageNo") int pageNo,
                                             @RequestParam(name = "pageSize") int pageSize,
                                             @RequestHeader(TavernRequestAuthFields.USER_ID) String companyId) {
        try {
            StaffResourcePoolListDTO dto = new StaffResourcePoolListDTO();
            IPage<StaffResourcePool> page = this.staffInfoService.queryStaffInfo(pageNo, pageSize, companyId);
            List<StaffResourcePoolDTO> list = page.getRecords()
                    .stream()
                    .map(this.converter::modelToStaffResourcePoolDTO)
                    .collect(Collectors.toList());
            dto.setStaffs(list);
            dto.setTotal((int) page.getTotal());
            return TavernResponse.ok(dto);
        } catch (Exception e) {
            LOGGER.error("[人力资源池员工查询] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 人力资源池员工新增
     */
    @PostMapping("")
    public TavernResponse addStaff(@RequestBody StaffInfoVO staffInfoVO,
                                   @RequestHeader(TavernRequestAuthFields.USER_ID) String companyId) {
        try {
            StaffResourcePool pool = this.converter.addVoToStaffResourcePool(staffInfoVO, companyId);
            this.staffInfoService.addStaffInfo(pool);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[人力资源池员工新增] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 人力资源池员工修改
     */
    @PutMapping("/{staffId}")
    public TavernResponse modifyStaff(@PathVariable("staffId") String staffId,
                                      @RequestBody StaffModifyVO staffModifyVO,
                                      @RequestHeader(TavernRequestAuthFields.USER_ID) String companyId) {
        try {
            StaffResourcePool pool = this.converter.modifyVoToStaffResourcePool(staffModifyVO, companyId, staffId);
            this.staffInfoService.modifyStaffInfo(pool);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[人力资源池员工修改] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 人力资源池员工删除
     */
    @DeleteMapping("/{staffId}")
    public TavernResponse delStaff(@PathVariable("staffId") String staffId) {
        try {
            this.staffInfoService.removeStaff(staffId);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[人力资源池员工删除] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    /**
     * 历史工资单
     */
    @GetMapping("/salary")
    public TavernResponse queryStaffSalaryList(@ModelAttribute @Valid SalaryHistoryVO vo,
                                               @RequestHeader(TavernRequestAuthFields.USER_ID) String companyId) {
        try {
            IPage<StaffSalaryInfo> page = this.staffInfoService.queryStaffSalaryInfo(vo.getPageNo(), vo.getPageSize(), companyId,
                    vo.getStaffId(),
                    vo.getProjectId(),
                    vo.getBeginDate(),
                    vo.getEndDate());
            StaffSalaryListDTO staffSalaryListDTO = new StaffSalaryListDTO();
            staffSalaryListDTO.setPayment(page.getRecords());
            staffSalaryListDTO.setTotal((int) page.getTotal());
            return TavernResponse.ok(staffSalaryListDTO);
        } catch (Exception e) {
            LOGGER.error("[历史工资单] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

}

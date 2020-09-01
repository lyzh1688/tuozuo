package com.tuozuo.tavern.xinruyi.vo;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/1 <br>
 */
public class ProjectModifyVO {
    private String projectId;
    private String projectName;
    private String industryType;
    private String releaseDate;
    private BigDecimal projectCycle;
    private BigDecimal staffNum;
    private String province;
    private String city;
    private String district;
    private String isResident;
    private String contactName;
    private String contact;
    private MultipartFile projectFile;
    private String desc;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public BigDecimal getProjectCycle() {
        return projectCycle;
    }

    public void setProjectCycle(BigDecimal projectCycle) {
        this.projectCycle = projectCycle;
    }

    public BigDecimal getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(BigDecimal staffNum) {
        this.staffNum = staffNum;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getIsResident() {
        return isResident;
    }

    public void setIsResident(String isResident) {
        this.isResident = isResident;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public MultipartFile getProjectFile() {
        return projectFile;
    }

    public void setProjectFile(MultipartFile projectFile) {
        this.projectFile = projectFile;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

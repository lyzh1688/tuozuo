package com.tuozuo.tavern.xinruyi.vo;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/9/1 <br>
 */
public class ProjectAddVO {
    @NotNull(message = "companyName is not null")
    private String projectName;
    @NotNull(message = "industryType is not null")
    private String industryType;
    @NotNull(message = "releaseDate is not null")
    private String releaseDate;
    @NotNull(message = "projectCycle is not null")
    private BigDecimal projectCycle;
    @NotNull(message = "staffNum is not null")
    private BigDecimal staffNum;
    @NotNull(message = "province is not null")
    private String province;
    @NotNull(message = "city is not null")
    private String city;
    @NotNull(message = "district is not null")
    private String district;
    @NotNull(message = "isResident is not null")
    private String isResident;
    @NotNull(message = "contactName is not null")
    private String contactName;
    @NotNull(message = "contact is not null")
    private String contact;
    @NotNull(message = "projectFile is not null")
    private MultipartFile projectFile;
    @NotNull(message = "desc is not null")
    private String desc;

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

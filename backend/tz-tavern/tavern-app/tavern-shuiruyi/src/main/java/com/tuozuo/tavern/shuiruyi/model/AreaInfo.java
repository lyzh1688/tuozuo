package com.tuozuo.tavern.shuiruyi.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

@TableName("area_info")
public class AreaInfo extends Model<AreaInfo> {
    @TableId
    private String areaCode;

    private String areaName;

    private String areaLevel;

    private String areaParentCode;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public String getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel == null ? null : areaLevel.trim();
    }

    public String getAreaParentCode() {
        return areaParentCode;
    }

    public void setAreaParentCode(String areaParentCode) {
        this.areaParentCode = areaParentCode == null ? null : areaParentCode.trim();
    }
}
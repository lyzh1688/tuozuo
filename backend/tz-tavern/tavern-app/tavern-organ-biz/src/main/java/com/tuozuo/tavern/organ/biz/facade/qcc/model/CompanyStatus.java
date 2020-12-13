package com.tuozuo.tavern.organ.biz.facade.qcc.model;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/12/13 <br>
 */
public enum CompanyStatus {

    //存续、在业、吊销、注销、迁入、迁出、停业、清算
    CUNXU("存续"),
    ZAIYE("在业"),
    DIAOXIAO("吊销"),
    ZHUXIAO("注销"),
    QIANRU("迁入"),
    QIANCHU("迁出"),
    TINGYE("停业"),
    QINGSUAN("清算");

    CompanyStatus(String status) {
        this.status = status;
    }

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static CompanyStatus getCompanyStatus(String status) {

        for (CompanyStatus t : CompanyStatus.values()) {
            if (status.equals(t.getStatus())) {
                return t;
            }
        }
        return null;

    }

    public static boolean getOperatingStatus(String status) {
        if (status.equals(CUNXU.getStatus()) || status.equals(ZAIYE.getStatus())) {
            return true;
        } else {
            return false;
        }
    }
}

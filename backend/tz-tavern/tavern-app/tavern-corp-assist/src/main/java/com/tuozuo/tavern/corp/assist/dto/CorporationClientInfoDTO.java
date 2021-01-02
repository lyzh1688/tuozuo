package com.tuozuo.tavern.corp.assist.dto;

import com.google.common.collect.Lists;
import com.tuozuo.tavern.corp.assist.model.CorporationClientInfo;
import com.tuozuo.tavern.corp.assist.model.CorporationClientTagInfo;

import java.util.List;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2021/1/2 <br>
 */
public class CorporationClientInfoDTO {
    private List<CorporationClientTagInfo> clients = Lists.newArrayList();
    private int total;

    public List<CorporationClientTagInfo> getClients() {
        return clients;
    }

    public void setClients(List<CorporationClientTagInfo> clients) {
        this.clients = clients;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

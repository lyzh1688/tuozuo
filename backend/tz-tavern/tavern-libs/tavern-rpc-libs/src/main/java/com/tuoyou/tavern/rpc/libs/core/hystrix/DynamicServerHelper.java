package com.tuoyou.tavern.rpc.libs.core.hystrix;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.netflix.loadbalancer.Server;

/**
 * Created by liuyuezhi on 2018/6/22.
 */
public interface DynamicServerHelper {
    default List<Server> getServerList(String httpServerList){
        String[] servers = httpServerList.split(",");
        List<Server> serverList = Lists.newArrayList();
        for(String serverStr : servers){
            if(StringUtils.isEmpty(serverStr)){
                continue;
            }
            int splitAnchor = serverStr.lastIndexOf(':');
            Server server = new Server(serverStr.substring(0,splitAnchor),Integer.valueOf(serverStr.substring(splitAnchor + 1)));
            server.setAlive(true);
            serverList.add(server);
        }
        return serverList;
    }
}

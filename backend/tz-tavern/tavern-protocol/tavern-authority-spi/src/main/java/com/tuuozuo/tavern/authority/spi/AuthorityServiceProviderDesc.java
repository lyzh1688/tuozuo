package com.tuuozuo.tavern.authority.spi;

import com.tuozuo.tavern.common.protocol.RequestMethods;
import com.tuozuo.tavern.common.protocol.SpiDescription;

import java.util.HashMap;
import java.util.Map;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/26 <br>
 */
public class AuthorityServiceProviderDesc {
    public static Map<Class<?>, Map<String, SpiDescription>> serviceProviderDesc = new HashMap<>();

    static {
        Map<String, SpiDescription> authorityServiceProviderDesc = new HashMap<>();

        SpiDescription createUser = new SpiDescription.Builder()
                .serviceClass(AuthorityService.class)
                .requestMethod(RequestMethods.POST)
                .resourcePath("/tuozuo/auth/v1/user")
                .serviceApi("createUser")
                .build();
        authorityServiceProviderDesc.put("createUser", createUser);

        SpiDescription modifyUser = new SpiDescription.Builder()
                .serviceClass(AuthorityService.class)
                .requestMethod(RequestMethods.PUT)
                .resourcePath("/tuozuo/auth/v1/user")
                .serviceApi("modifyUser")
                .build();
        authorityServiceProviderDesc.put("modifyUser", modifyUser);

        serviceProviderDesc.put(AuthorityService.class, authorityServiceProviderDesc);
    }
}

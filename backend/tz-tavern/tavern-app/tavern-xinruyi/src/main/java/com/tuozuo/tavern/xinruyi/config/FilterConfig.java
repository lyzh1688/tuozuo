package com.tuozuo.tavern.xinruyi.config;

import org.apache.tomcat.util.http.MimeHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 拦截器配置
 */
@Configuration
public class FilterConfig {

    @Value("${def.user.switch:false}")
    private boolean userSwitch;

    @Bean
    public FilterRegistrationBean modifyParametersFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ModifyParametersFilter());
        registration.addUrlPatterns("/tuozuo/xinruyi/applet/v1/*");              // 拦截路径
        registration.setName("modifyParametersFilter"); // 拦截器名称
        registration.setOrder(1);                       // 顺序
        return registration;
    }

    /**
     * 自定义拦截器
     */
    class ModifyParametersFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            // 修改请求头
            Map<String, String> map = new HashMap<>();
            if(userSwitch){
                map.put("X-USER-ID","1234");
            }
            modifyHeaders(map, request);

            ModifyHttpServletRequestWrapper requestWrapper = new ModifyHttpServletRequestWrapper(request);

            // finish
            filterChain.doFilter(requestWrapper, response);
        }
    }

    /**
     * 修改请求头信息
     * @param headerses
     * @param request
     */
    private void modifyHeaders(Map<String, String> headerses, HttpServletRequest request) {
        if (headerses == null || headerses.isEmpty()) {
            return;
        }
        Class<? extends HttpServletRequest> requestClass = request.getClass();
        try {
            Field request1 = requestClass.getDeclaredField("request");
            request1.setAccessible(true);
            Object o = request1.get(request);
            Field coyoteRequest = o.getClass().getDeclaredField("coyoteRequest");
            coyoteRequest.setAccessible(true);
            Object o1 = coyoteRequest.get(o);
            Field headers = o1.getClass().getDeclaredField("headers");
            headers.setAccessible(true);
            MimeHeaders o2 = (MimeHeaders)headers.get(o1);
            for (Map.Entry<String, String> entry : headerses.entrySet()) {
                o2.removeHeader(entry.getKey());
                o2.addValue(entry.getKey()).setString(entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改cookie信息
     */
    class ModifyHttpServletRequestWrapper extends HttpServletRequestWrapper {
        private Map<String, String> mapCookies;
        ModifyHttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
            this.mapCookies = new HashMap<>();
        }
        public void putCookie(String name, String value) {
            this.mapCookies.put(name, value);
        }
        public Cookie[] getCookies() {
            HttpServletRequest request = (HttpServletRequest) getRequest();
            Cookie[] cookies = request.getCookies();
            if (mapCookies == null || mapCookies.isEmpty()) {
                return cookies;
            }
            if (cookies == null || cookies.length == 0) {
                List<Cookie> cookieList = new LinkedList<>();
                for (Map.Entry<String, String> entry : mapCookies.entrySet()) {
                    String key = entry.getKey();
                    if (key != null && !"".equals(key)) {
                        cookieList.add(new Cookie(key, entry.getValue()));
                    }
                }
                if (cookieList.isEmpty()) {
                    return cookies;
                }
                return cookieList.toArray(new Cookie[cookieList.size()]);
            } else {
                List<Cookie> cookieList = new ArrayList<>(Arrays.asList(cookies));
                for (Map.Entry<String, String> entry : mapCookies.entrySet()) {
                    String key = entry.getKey();
                    if (key != null && !"".equals(key)) {
                        for (int i = 0; i < cookieList.size(); i++) {
                            if(cookieList.get(i).getName().equals(key)){
                                cookieList.remove(i);
                            }
                        }
                        cookieList.add(new Cookie(key, entry.getValue()));
                    }
                }
                return cookieList.toArray(new Cookie[cookieList.size()]);
            }
        }
    }

}
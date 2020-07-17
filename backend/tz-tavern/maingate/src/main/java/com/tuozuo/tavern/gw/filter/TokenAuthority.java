package com.tuozuo.tavern.gw.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.tuozuo.tavern.common.protocol.TavernRequestAuthFields;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.libs.auth.AuthTokenFactor;
import com.tuozuo.tavern.libs.auth.JwtAuthenticationProperty;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * Created by 刘悦之 on 2019/7/6.
 */
public class TokenAuthority extends ZuulFilter {

    Logger LOGGER = LoggerFactory.getLogger(TokenAuthority.class);

    public static ObjectMapper objectMapper;

    @Autowired
    private JwtAuthenticationProperty config;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //从RequestContext获取上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        //从上下文获取HttpServletRequest
        HttpServletRequest request = ctx.getRequest();
        String uri = request.getRequestURI();
        if (uri.equals(config.getUrl())) {
            return false;
        }
        return true;
    }

    @Override
    public Object run() {
        //从RequestContext获取上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        //从上下文获取HttpServletRequest
        HttpServletRequest request = ctx.getRequest();
        String token = request.getHeader(config.getHeader());
        if (token != null && token.startsWith(config.getPrefix() + " ")) {
            token = token.replace(config.getPrefix() + " ", "");
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(config.getSecret().getBytes())
                        .parseClaimsJws(token)
                        .getBody();
                if (claims == null) {
                    ctx.setSendZuulResponse(false);
                    ctx.setResponseBody(objectMapper.writeValueAsString(TavernResponse.AUTH_FAILED));
                    ctx.set(ContextDict.isLoginSuccess, false);
                } else {
                    ctx.set(ContextDict.isLoginSuccess, true);
                    ctx.addZuulRequestHeader(TavernRequestAuthFields.USER_ACCNT, claims.get(AuthTokenFactor.Factor.USER_ACCNT, String.class));
                    ctx.addZuulRequestHeader(TavernRequestAuthFields.USER_TYPE, claims.get(AuthTokenFactor.Factor.USER_TYPE, String.class));
                    ctx.addZuulRequestHeader(TavernRequestAuthFields.ROLE_ID, claims.get(AuthTokenFactor.Factor.ROLE_ID, String.class));
                }
            } catch (Exception ignore) {
                ctx.setSendZuulResponse(false);
                try {
                    ctx.setResponseBody(objectMapper.writeValueAsString(TavernResponse.AUTH_FAILED));
                } catch (JsonProcessingException e) {
                    LOGGER.error("token解析失败", e);
                }
                ctx.set(ContextDict.isLoginSuccess, false);
            }
        }
        return null;
    }
}

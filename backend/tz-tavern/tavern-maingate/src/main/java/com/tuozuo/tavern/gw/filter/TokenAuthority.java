package com.tuozuo.tavern.gw.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.tuozuo.tavern.common.protocol.TavernRequestAuthFields;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuozuo.tavern.libs.auth.jwt.JwtAuthenticationProperty;
import com.tuozuo.tavern.libs.auth.session.RedisSession;
import com.tuozuo.tavern.libs.auth.session.SessionManager;
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

    public static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private JwtAuthenticationProperty config;

    @Autowired
    private SessionManager sessionManager;

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
        if (uri.contains(config.getAuthPrefix())) {
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
        String token = request.getHeader(config.getAccessToken());
        if (token != null && token.startsWith(config.getPrefix() + " ")) {
            token = token.replace(config.getPrefix() + " ", "");
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(config.getSecret().getBytes())
                        .parseClaimsJws(token)
                        .getBody();
                if (claims == null) {
                    this.authFailed(ctx);
                } else {
                    //会话超时判断,未过期则续期
                    RedisSession session = new RedisSession(token, claims, config.getTokenTimeout());
                    if (sessionManager.valid(session)) {
                        ctx.set(ContextDict.isLoginSuccess, true);
                        //将用户信息要素写入HTTP HEADER
                        ctx.addZuulRequestHeader(TavernRequestAuthFields.USER_ID, session.getUserId());
                        ctx.addZuulRequestHeader(TavernRequestAuthFields.SYSTEM_ID, session.getSystemId());
                        ctx.addZuulRequestHeader(TavernRequestAuthFields.ROLE_GROUP, session.getRoleId());
                    } else {
                        this.authFailed(ctx);
                    }
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

    private void authFailed(RequestContext ctx) throws JsonProcessingException {
        ctx.setSendZuulResponse(false);
        ctx.setResponseBody(objectMapper.writeValueAsString(TavernResponse.AUTH_FAILED));
        ctx.set(ContextDict.isLoginSuccess, false);
    }
}

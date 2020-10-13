package com.tuozuo.tavern.authority.endpoint;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.tuozuo.tavern.authority.model.WXTokenAuthority;
import com.tuozuo.tavern.authority.service.AuthorityService;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.tuozuo.tavern.common.protocol.RetCodeDict.AUTH_FAILURE;

/**
 * Created by 刘悦之 on 2020/10/10.
 */
@RestController
@RequestMapping("/tuozuo/auth/wx/v1")
public class WeixinAuthEndpoint {

    @Autowired
    AuthorityService authorityService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public TavernResponse<WXTokenAuthority> login(@RequestParam("code") String code,
                                                  @RequestParam("systemId") String systemId,
                                                  @RequestParam("roleGroup") String roleGroup) {

        Preconditions.checkArgument(!Strings.isNullOrEmpty(code), "code 不能为空");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(systemId), "systemId 不能为空");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(roleGroup), "roleGroup 不能为空");

        Optional<WXTokenAuthority> optional = this.authorityService.wxLogin(code, systemId, roleGroup);
        if (optional.isPresent()) {
            WXTokenAuthority tokenAuthority = optional.get();
            if (tokenAuthority.getLoginSuccess()) {
                return TavernResponse.ok(tokenAuthority);
            } else {
                return TavernResponse.createResponse(AUTH_FAILURE, tokenAuthority.getLoginMessage(), tokenAuthority);
            }
        }
        return TavernResponse.bizFailure("内部服务错误");
    }
}

package com.tuozuo.tavern.authority.endpoint;

import com.tuozuo.tavern.authority.model.Password;
import com.tuozuo.tavern.authority.model.RSAPublicKey;
import com.tuozuo.tavern.authority.model.TokenAuthority;
import com.tuozuo.tavern.authority.model.User;
import com.tuozuo.tavern.authority.service.AuthorityService;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static com.tuozuo.tavern.common.protocol.RetCodeDict.AUTH_FAILURE;

/**
 * Created by 刘悦之 on 2020/7/17.
 */
@RestController
@RequestMapping("/tuozuo/auth/v1")
public class AuthorityEndpoint {

    Logger LOGGER = LoggerFactory.getLogger(AuthorityEndpoint.class);

    @Autowired
    AuthorityService authorityService;

    @RequestMapping("/publicKey")
    public TavernResponse<RSAPublicKey> rsaPublicKeys(@RequestParam("userId") String userId,
                                                      @RequestParam("systemId") String systemId,
                                                      @RequestParam("roleGroup") String roleGroup) {
        RSAPublicKey rsaPublicKey = null;
        try {
            rsaPublicKey = authorityService.getRSAPublicKeys(userId, systemId, roleGroup);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("获取公钥失败", e);
        }
        return TavernResponse.ok(rsaPublicKey);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public TavernResponse<TokenAuthority> login(@RequestParam("userId") String userId,
                                                @RequestParam("password") String password,
                                                @RequestParam("systemId") String systemId,
                                                @RequestParam("roleGroup") String roleGroup) {

        Optional<TokenAuthority> optional = this.authorityService.login(userId, new Password(password), systemId, roleGroup);
        if (optional.isPresent()) {
            TokenAuthority tokenAuthority = optional.get();
            if (tokenAuthority.getLoginSuccess()) {
                return TavernResponse.ok(tokenAuthority);
            } else {
                return TavernResponse.createResponse(AUTH_FAILURE, tokenAuthority.getLoginMessage(), tokenAuthority);
            }
        }
        return TavernResponse.bizFailure("内部服务错误");
    }

    @RequestMapping("/logout")
    public TavernResponse<TokenAuthority> logout() {
        TokenAuthority tokenAuthority = new TokenAuthority();
        return TavernResponse.OK;
    }


    @RequestMapping(value = "/user", method = RequestMethod.POST)
    TavernResponse<Void> createUser(@RequestBody User user) {
        boolean ret = this.authorityService.createUser(user);
        if(ret){
            return TavernResponse.OK;
        }
        else {
            return TavernResponse.bizFailure("创建用户失败");
        }
    }
}

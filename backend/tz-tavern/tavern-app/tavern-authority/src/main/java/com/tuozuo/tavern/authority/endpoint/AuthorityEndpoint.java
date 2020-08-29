package com.tuozuo.tavern.authority.endpoint;

import com.tuozuo.tavern.authority.model.*;
import com.tuozuo.tavern.authority.service.AuthorityService;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuuozuo.tavern.authority.spi.vo.UserVO;
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

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public TavernResponse<TokenAuthority> logout(@RequestBody User user,
                                                 @RequestHeader(value = "access-token") String accessToken
    ) {
        boolean ret = this.authorityService.logout(user.getUserId(), user.getSystemId(), user.getRoleGroup(), accessToken);
        if (ret) {
            return TavernResponse.OK;
        } else {
            return TavernResponse.bizFailure("创建用户失败");
        }
    }


    @RequestMapping(value = "/user", method = RequestMethod.POST)
    TavernResponse<Void> createUser(@RequestBody UserVO userVO) {
        User user = this.voToUser(userVO);
        boolean ret = this.authorityService.createUser(user);
        if (ret) {
            return TavernResponse.OK;
        } else {
            return TavernResponse.bizFailure("创建用户失败");
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    TavernResponse<Void> modifyUser(@RequestBody UserVO userVO) {
        try {
            User user = this.voToUser(userVO);
            this.authorityService.modifyUser(user);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[用户修改] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    TavernResponse<Void> removeUser(@RequestParam(value = "userId") String userId) {
        try {
            this.authorityService.remove(userId);
            return TavernResponse.OK;
        } catch (Exception e) {
            LOGGER.error("[用户删除] failed", e);
            return TavernResponse.bizFailure(e.getMessage());
        }
    }

    private User voToUser(UserVO userVO) {
        User user = new User();
        user.setUserId(userVO.getUserId());
        user.setSystemId(userVO.getSystemId());
        user.setRoleGroup(userVO.getRoleGroup());
        user.setUserPswd(userVO.getUserPswd());
        Privilege privilege = new Privilege(userVO.getUserId(), userVO.getSystemId(),userVO.getRoleGroup(),userVO.getPrivilege());
        user.setPrivilege(privilege);
        return user;
    }
}

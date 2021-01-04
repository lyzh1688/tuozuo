package com.tuozuo.tavern.authority.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.google.common.base.Strings;
import com.tuozuo.tavern.authority.dao.AuthorityDao;
import com.tuozuo.tavern.authority.dao.PrivilegeDao;
import com.tuozuo.tavern.authority.model.*;
import com.tuozuo.tavern.libs.auth.encrypt.RSAEncrypt;
import com.tuozuo.tavern.libs.auth.encrypt.RSAKeyPair;
import com.tuozuo.tavern.libs.auth.jwt.JwtAuthenticationProperty;
import com.tuozuo.tavern.libs.auth.session.RedisSession;
import com.tuozuo.tavern.libs.auth.session.SessionManager;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpMaJsCode2SessionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * Created by 刘悦之 on 2020/7/18.
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityServiceImpl.class);

    @Autowired
    JwtAuthenticationProperty property;

    @Autowired
    AuthorityDao authorityDao;

    @Autowired
    PrivilegeDao privilegeDao;

    @Autowired
    SessionManager sessionManager;

    @Autowired
    WxMaService wxMaService;
    @Autowired
    WxCpService wxCpService;

    @Override
    public RSAPublicKey getRSAPublicKeys(String userId, String systemId, String roleGroup) throws NoSuchAlgorithmException {
        RSAKeyPair pair = RSAEncrypt.genKeyPair();
        if (authorityDao.saveRSAKey(userId, systemId, roleGroup, pair)) {
            RSAPublicKey rsaPublicKey = new RSAPublicKey();
            rsaPublicKey.setPublicKey(pair.getPublicKeyString());
            return rsaPublicKey;
        }
        return null;
    }

    @Override
    public Optional<TokenAuthority> login(String userId, Password password, String systemId, String roleGroup) {
        String privateRSAKey = authorityDao.getRSAPrivateKey(userId, systemId, roleGroup);
        if (Strings.isNullOrEmpty(privateRSAKey)) {
            return Optional.of(new TokenAuthority(false, "密钥失效"));
        }
        String inputMD5Pswd = password.getMD5Password(privateRSAKey);
        if (Strings.isNullOrEmpty(inputMD5Pswd)) {
            return Optional.of(new TokenAuthority(false, "密码不能为空"));
        }
        //获取用户
        User user = this.authorityDao.getUser(userId, systemId, roleGroup);
        if (user == null) {
            return Optional.of(new TokenAuthority(false, "用户不存在"));
        }
        //获取用户权限
        Optional<Privilege> privilegeOp = this.privilegeDao.getPrivilege(userId, systemId, roleGroup);
        if (privilegeOp.isPresent()) {
            user.setPrivilege(privilegeOp.get());
        } else {
            //兼容老系统,若无权限，则为默认NORMAL权限
            Privilege defaultPrivilege = Privilege.createDefaultPrivilege(userId, systemId, roleGroup);
            user.setPrivilege(defaultPrivilege);
        }

        TokenAuthority tokenAuthority = user.login(inputMD5Pswd, property);
        if (tokenAuthority.getLoginSuccess()) {
            //登陆成功失败次数清零
            user.setFailedTimes(0);
            this.authorityDao.updateFailedTimes(user);
            RedisSession session = new RedisSession(user.getUserId(), user.getSystemId(), user.getRoleGroup(), tokenAuthority.getAccessToken());
            sessionManager.createOrRefreshSession(session);
        } else {
            //登陆失败增加失败次数
            if (user.getFailedTimes() < User.getMaxFailedTimes()) {
                user.incFailedTimes();
                this.authorityDao.updateFailedTimes(user);
            }
        }

        return Optional.of(tokenAuthority);
    }

    @Override
    public Optional<WXTokenAuthority> wxLogin(String code, String systemId, String roleGroup) {
        //获取openID
        WxMaJscode2SessionResult wxSessionResult = null;
        try {
            wxSessionResult = wxMaService.getUserService().getSessionInfo(code);
            if (wxSessionResult == null) {
                LOGGER.error("微信服务器连接失败...");
                return Optional.empty();
            }
            String openID = wxSessionResult.getOpenid();
            //校验openID（即UserID）是否存在
            User user = null;
            user = this.authorityDao.getUser(openID, systemId, roleGroup);
            if (user == null) {
                user = new User();
            }
            //获取用户权限
            Optional<Privilege> privilegeOp = this.privilegeDao.getPrivilege(openID, systemId, roleGroup);
            if (privilegeOp.isPresent()) {
                user.setPrivilege(privilegeOp.get());
            } else {
                user.setPrivilege(new Privilege(openID, systemId, roleGroup, Privilege.AUTHORITY_VISITOR));
            }
            WXTokenAuthority tokenAuthority = user.wxLogin(openID, systemId, roleGroup, property);
            RedisSession session = new RedisSession(user.getUserId(), user.getSystemId(), user.getRoleGroup(), tokenAuthority.getAccessToken());
            sessionManager.createOrRefreshSession(session);

            return Optional.of(tokenAuthority);
        } catch (WxErrorException e) {
            LOGGER.error("微信服务器连接失败...", e);
            return Optional.empty();
        }

    }

    @Override
    public Optional<WXTokenAuthority> wxCPLogin(String code, String systemId, String roleGroup) {
        //获取openID
        WxCpMaJsCode2SessionResult wxSessionResult = null;
        try {
            wxSessionResult = wxCpService.jsCode2Session(code);
            if (wxSessionResult == null) {
                LOGGER.error("微信服务器连接失败...");
                return Optional.empty();
            }
            String openID = wxSessionResult.getUserId();
            //校验openID（即UserID）是否存在
            User user = null;
            user = this.authorityDao.getUser(openID, systemId, roleGroup);
            if (user == null) {
                user = new User();
            }
            //获取用户权限
            Optional<Privilege> privilegeOp = this.privilegeDao.getPrivilege(openID, systemId, roleGroup);
            if (privilegeOp.isPresent()) {
                user.setPrivilege(privilegeOp.get());
            } else {
                user.setPrivilege(new Privilege(openID, systemId, roleGroup, Privilege.AUTHORITY_VISITOR));
            }
            WXTokenAuthority tokenAuthority = user.wxLogin(openID, systemId, roleGroup, property);
            RedisSession session = new RedisSession(user.getUserId(), user.getSystemId(), user.getRoleGroup(), tokenAuthority.getAccessToken());
            sessionManager.createOrRefreshSession(session);

            return Optional.of(tokenAuthority);
        } catch (WxErrorException e) {
            LOGGER.error("微信服务器连接失败...", e);
            return Optional.empty();
        }

    }

    @Override
    public boolean logout(String userId, String systemId, String roleGroup, String accessToken) {
        try {
            RedisSession session = new RedisSession(userId, systemId, roleGroup, accessToken);
            return this.sessionManager.dispose(session);
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    @Override
    public boolean createUser(User user) {
        try {
            this.authorityDao.createUser(user);
            return this.privilegeDao.addPrivilege(user.getPrivilege());
        } catch (Exception e) {
            LOGGER.error("[创建用户失败] error: ", e);
            throw e;
        }
    }

    @Transactional
    @Override
    public void modifyUser(User user) {
        this.authorityDao.updateUser(user);
        this.privilegeDao.updatePrivilege(user.getPrivilege());
    }

    @Transactional
    @Override
    public void remove(String userId) {
        this.authorityDao.removeUser(userId);
        this.privilegeDao.delPrivilege(userId);
    }
}

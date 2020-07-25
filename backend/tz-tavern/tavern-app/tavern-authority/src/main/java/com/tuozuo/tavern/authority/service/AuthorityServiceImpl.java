package com.tuozuo.tavern.authority.service;

import com.google.common.base.Strings;
import com.tuozuo.tavern.authority.dao.AuthorityDao;
import com.tuozuo.tavern.authority.model.Password;
import com.tuozuo.tavern.authority.model.RSAPublicKey;
import com.tuozuo.tavern.authority.model.TokenAuthority;
import com.tuozuo.tavern.authority.model.User;
import com.tuozuo.tavern.libs.auth.encrypt.RSAEncrypt;
import com.tuozuo.tavern.libs.auth.encrypt.RSAKeyPair;
import com.tuozuo.tavern.libs.auth.jwt.JwtAuthenticationProperty;
import com.tuozuo.tavern.libs.auth.session.RedisSession;
import com.tuozuo.tavern.libs.auth.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * Created by 刘悦之 on 2020/7/18.
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    JwtAuthenticationProperty property;

    @Autowired
    AuthorityDao authorityDao;

    @Autowired
    SessionManager sessionManager;

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
        User user = this.authorityDao.getUser(userId, systemId, roleGroup);
        if (user == null) {
            return Optional.of(new TokenAuthority(false, "用户不存在"));
        }
        TokenAuthority tokenAuthority = user.login(inputMD5Pswd, property);
        if (tokenAuthority.getLoginSuccess()) {
            //登陆成功失败次数清零
            user.setFailedTimes(0);
            this.authorityDao.updateFailedTimes(user);
            RedisSession session = new RedisSession(user.getUserId(),user.getSystemId(),user.getRoleGroup(),tokenAuthority.getAccessToken());
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
    public boolean createUser(User user) {
        return this.authorityDao.createUser(user);
    }
}

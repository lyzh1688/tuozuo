package com.tuozuo.tavern.authority.service;

import com.tuozuo.tavern.authority.dao.AuthorityDao;
import com.tuozuo.tavern.authority.model.Password;
import com.tuozuo.tavern.authority.model.RSAPublicKey;
import com.tuozuo.tavern.authority.model.TokenAuthority;
import com.tuozuo.tavern.authority.model.User;
import com.tuozuo.tavern.libs.auth.encrypt.RSAEncrypt;
import com.tuozuo.tavern.libs.auth.encrypt.RSAKeyPair;
import com.tuozuo.tavern.libs.auth.jwt.JwtAuthenticationProperty;
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
        String inputMD5Pswd = password.getMD5Password(privateRSAKey);
        User user = this.authorityDao.getUser(userId, systemId, roleGroup);
        TokenAuthority tokenAuthority = user.login(inputMD5Pswd, property);
        return Optional.of(tokenAuthority);
    }
}

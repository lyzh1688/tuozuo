package com.tuozuo.tavern.authority.service;

import com.tuozuo.tavern.authority.model.Password;
import com.tuozuo.tavern.authority.model.RSAPublicKey;
import com.tuozuo.tavern.authority.model.TokenAuthority;
import com.tuozuo.tavern.authority.model.User;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * Created by 刘悦之 on 2020/7/18.
 */
public interface AuthorityService {
    RSAPublicKey getRSAPublicKeys(String userId, String systemId, String roleGroup) throws NoSuchAlgorithmException;

    Optional<TokenAuthority> login(String userId, Password password, String systemId, String roleGroup);

    boolean createUser(User user);
}

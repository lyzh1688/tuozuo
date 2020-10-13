package com.tuozuo.tavern.authority.service;

import com.tuozuo.tavern.authority.model.*;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * Created by 刘悦之 on 2020/7/18.
 */
public interface AuthorityService {
    RSAPublicKey getRSAPublicKeys(String userId, String systemId, String roleGroup) throws NoSuchAlgorithmException;

    Optional<TokenAuthority> login(String userId, Password password, String systemId, String roleGroup);

    Optional<WXTokenAuthority> wxLogin(String code, String systemId, String roleGroup);

    boolean logout(String userId, String systemId, String roleGroup, String accessToken);

    boolean createUser(User user);

    void modifyUser(User user);

    void remove(String userId);


}

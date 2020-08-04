package com.tuozuo.tavern.authority.dao;

import com.tuozuo.tavern.authority.model.User;
import com.tuozuo.tavern.libs.auth.encrypt.RSAKeyPair;

/**
 * Created by 刘悦之 on 2020/7/18.
 */
public interface AuthorityDao {
    boolean saveRSAKey(String userId, String systemId, String roleGroup, RSAKeyPair pair);
    String getRSAPrivateKey(String userId, String systemId, String roleGroup);
    User getUser(String userId, String systemId, String roleGroup);
    void updateFailedTimes(User user);
    boolean createUser(User user);
    void updateUser(User user);
}

package com.tuozuo.tavern.authority.dao;

import com.tuozuo.tavern.authority.model.Privilege;

import java.util.Optional;

/**
 * Created by 刘悦之 on 2020/8/29.
 */
public interface PrivilegeDao {
    Optional<Privilege> getPrivilege(String userId, String systemId, String roleGroup);
    boolean addPrivilege(Privilege privilege);
    boolean updatePrivilege(Privilege privilege);
    void delPrivilege(String userId);
}

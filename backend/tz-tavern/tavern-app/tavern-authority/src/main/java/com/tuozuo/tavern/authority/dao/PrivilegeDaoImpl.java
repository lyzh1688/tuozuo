package com.tuozuo.tavern.authority.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.authority.mapper.UserPrivilegeMapper;
import com.tuozuo.tavern.authority.model.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by 刘悦之 on 2020/8/29.
 */
@Repository
public class PrivilegeDaoImpl extends ServiceImpl<UserPrivilegeMapper, Privilege> implements PrivilegeDao {

    @Autowired
    private UserPrivilegeMapper privilegeMapper;

    @Override
    public Optional<Privilege> getPrivilege(String userId, String systemId, String roleGroup) {
        Wrapper<Privilege> wrapper = Wrappers.<Privilege>query()
                .eq("user_id", userId)
                .eq("system_id", systemId)
                .eq("role_group", roleGroup);
        Privilege privilege = this.privilegeMapper.selectOne(wrapper);
        if (null != privilege)
            return Optional.of(privilege);
        else {
            return Optional.empty();
        }
    }

    @Override
    public boolean addPrivilege(Privilege privilege) {
        return this.saveOrUpdate(privilege);
    }

    @Override
    public boolean updatePrivilege(Privilege privilege) {
        Wrapper<Privilege> wrapper = Wrappers.<Privilege>query()
                .eq("user_id", privilege.getUserId())
                .eq("system_id", privilege.getSystemId())
                .eq("role_group", privilege.getRoleGroup());
        return this.privilegeMapper.update(privilege, wrapper) > 0 ? true : false;
    }

    @Override
    public void delPrivilege(String userId) {
        this.privilegeMapper.deleteById(userId);
    }

}

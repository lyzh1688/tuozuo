package com.tuozuo.tavern.authority.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuozuo.tavern.authority.mapper.AuthUserMapper;
import com.tuozuo.tavern.authority.model.User;
import com.tuozuo.tavern.libs.auth.encrypt.RSAKeyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * Created by 刘悦之 on 2020/7/18.
 */

@Repository
public class AuthorityDaoImpl extends ServiceImpl<AuthUserMapper, User> implements AuthorityDao {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean saveRSAKey(String userId, String systemId, String roleGroup, RSAKeyPair pair) {
        String key = String.join(":", "RSAKeyPair", systemId, roleGroup, userId);
        boolean ret = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] oldKey = redisConnection.get(key.getBytes());
                 if (null != oldKey) {
                    redisConnection.del(key.getBytes());
                }
                redisConnection.set(key.getBytes(),
                        pair.getPrivateKeyString().getBytes(),
                        Expiration.from(3, TimeUnit.MINUTES),
                        RedisStringCommands.SetOption.SET_IF_ABSENT);
                return true;
            }
        });
        return ret;
    }

    @Override
    public String getRSAPrivateKey(String userId, String systemId, String roleGroup) {
        String key = String.join(":", "RSAKeyPair", systemId, roleGroup, userId);
        return this.redisTemplate.opsForValue().get(key);
    }

    @Override
    public User getUser(String userId, String systemId, String roleGroup) {
        Wrapper<User> wrapper = Wrappers.<User>query()
                .eq("user_id", userId)
                .eq("system_id", systemId)
                .eq("role_group", roleGroup);
        return this.getOne(wrapper);
    }

    @Override
    public void updateFailedTimes(User user) {
        UpdateWrapper updateWrapper = Wrappers.<User>update()
                .eq("user_id", user.getUserId())
                .eq("system_id", user.getSystemId())
                .eq("role_group", user.getRoleGroup())
                .set("failed_times", user.getFailedTimes());
        this.update(user, updateWrapper);
    }

}

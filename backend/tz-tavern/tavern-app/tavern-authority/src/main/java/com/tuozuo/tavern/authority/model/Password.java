package com.tuozuo.tavern.authority.model;

import com.google.common.base.Strings;
import com.tuozuo.tavern.libs.auth.encrypt.RSAEncrypt;
import org.springframework.util.DigestUtils;

/**
 * Created by 刘悦之 on 2020/7/18.
 */
public class Password {
    String password;

    public Password(String password) {
        this.password = password;
    }

    public String getMD5Password(String privateRSAKey) {
        if (Strings.isNullOrEmpty(privateRSAKey)) {
            return null;
        } else {
            String rawPassword = null;
            try {
                rawPassword = RSAEncrypt.decrypt(password, privateRSAKey);
            } catch (Exception e) {
                return null;
            }
            String md5Password = DigestUtils.md5DigestAsHex(rawPassword.getBytes());
            System.out.println("md5Password:" + md5Password);
            return md5Password;
        }
    }
}

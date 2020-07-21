package com.tuozuo.tavern.authority;

import com.tuozuo.tavern.authority.model.Password;
import com.tuozuo.tavern.authority.model.User;
import com.tuozuo.tavern.libs.auth.encrypt.RSAEncrypt;
import com.tuozuo.tavern.libs.auth.encrypt.RSAKeyPair;
import org.junit.Test;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.DigestUtils;

import java.security.NoSuchAlgorithmException;

/**
 * Created by 刘悦之 on 2020/7/18.
 */
public class RSATest {
    @Test
    public void encript() throws Exception {
        String password = "y2iaciej";
        String md5 = DigestUtils.md5DigestAsHex(password.getBytes());
        System.out.println(md5);
        System.out.println("+++++++++++++++++++++++++");
        RSAKeyPair pair = RSAEncrypt.genKeyPair();
        System.out.println(pair.getPrivateKeyString());
        System.out.println("++++++++++++++++++++");
        String pswd = RSAEncrypt.encrypt(password,pair.getPublicKeyString());
        System.out.println(pswd);


    }

    @Test
    public void decode(){
        String str = "y2iaciej";
        String ret = Base64.encodeBase64URLSafeString(str.getBytes());
        String ori = new String(Base64.decodeBase64(ret));
        System.out.println(ori);
    }
}

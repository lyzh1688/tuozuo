package com.tuozuo.tavern.authority;

import com.tuozuo.tavern.authority.model.Password;
import com.tuozuo.tavern.authority.model.User;
import com.tuozuo.tavern.libs.auth.encrypt.RSAEncrypt;
import com.tuozuo.tavern.libs.auth.encrypt.RSAKeyPair;
import org.junit.Test;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.DigestUtils;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by 刘悦之 on 2020/7/18.
 */
public class RSATest {
    @Test
    public void encript() throws Exception {
        String password = "y2iaciej";
        String md5 = DigestUtils.md5DigestAsHex(password.getBytes());
        System.out.println(md5);
//        System.out.println("+++++++++++++++++++++++++");
//        RSAKeyPair pair = RSAEncrypt.genKeyPair();
//        System.out.println(pair.getPrivateKeyString());
//        System.out.println("++++++++++++++++++++");
//        String pswd = RSAEncrypt.encrypt(password,pair.getPublicKeyString());
//        System.out.println(pswd);


    }

    @Test
    public void test() throws Exception {
//        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLQ3T3YXa2qpqAm1K1hOQftj0UKLKvdNRY8C+GG/Xdbl2JfWyfp3W0ecfsKt4cTmdSe8UZVx/8glQ3F/Aa2Xhs7Uv3BRD8CIOe9ybDdIKahShJECwG1ZVWT3GrVkkGhdpwRwjlO065CU68oWJgrNBicSG9tMmVMfoEg1QML4wAXwIDAQAB";
//        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCUQ1l903HqTtJ+fZqNA5jMqeArXyESi4iTJaZXMncjmvswXeUPt7mC0KUc06upvZgdBygE+LpOTlfRoVyn2k+BMNSRO7H4B/zmH5uSNYXjZYR7UQJfvWpiW2yBZq7Cx7mZstbMDIKjjplAXFMiZnIVmOKhaYPKNPZvl7fd6vuERQIDAQAB";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxawHShpRcuzPbyQZsQDkuFcB02rv/ffTn0rlcZkjAznGNS1Axj6+T668KRIRadTP/OwFo+6VzIPscfLdtmb7gAVDnLbzBUvcjWlVsQypf6YVwiIBLkkd5rqGh6GxXh/kCZpFvYLMxusqVf0uEMso4P7/d6/Qbb2SWbB1Q6C806QIDAQAB";
        String password = "y2iaciej";
        String pswd = RSAEncrypt.encrypt(password,publicKey);
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

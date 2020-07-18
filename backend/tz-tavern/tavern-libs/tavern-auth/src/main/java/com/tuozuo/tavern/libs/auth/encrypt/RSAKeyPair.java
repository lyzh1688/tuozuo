package com.tuozuo.tavern.libs.auth.encrypt;

/**
 * Created by 刘悦之 on 2020/7/18.
 */
public class RSAKeyPair {
    String publicKeyString;
    String privateKeyString;

    public RSAKeyPair(String publicKeyString, String privateKeyString) {
        this.publicKeyString = publicKeyString;
        this.privateKeyString = privateKeyString;
    }

    public String getPublicKeyString() {
        return publicKeyString;
    }

    public String getPrivateKeyString() {
        return privateKeyString;
    }
}

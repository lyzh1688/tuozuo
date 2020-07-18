package com.tuozuo.tavern.libs.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.util.Date;

/**
 * Created by 刘悦之 on 2019/7/7.
 */
public class TokenHelper {

    public static String createToken(JwtAuthenticationProperty config, AuthTokenFactor tokenFactor){
        Instant now = Instant.now();
        String token = Jwts.builder()
                .setSubject(tokenFactor.getUserId())
                .claim(AuthTokenFactor.Factor.USER_ID, tokenFactor.getUserId())
                .claim(AuthTokenFactor.Factor.SYSTEM_ID, tokenFactor.getSystemId())
                .claim(AuthTokenFactor.Factor.ROLE_GROUP, tokenFactor.getRoleGroup())
                .setIssuedAt(Date.from(now))
                .setIssuer("TUOZUO")
                .setExpiration(Date.from(now.plusSeconds(config.getExpiration())))
                .signWith(SignatureAlgorithm.HS256, config.getSecret().getBytes())
                .compact();
        return config.getPrefix() + " " + token;
    }
}

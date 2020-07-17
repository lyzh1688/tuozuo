package com.tuozuo.tavern.authority.endpoint;

import com.tuozuo.tavern.authority.model.RSAPublicKey;
import com.tuozuo.tavern.authority.model.TokenAuthority;
import com.tuozuo.tavern.common.protocol.TavernResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 刘悦之 on 2020/7/17.
 */
@RestController
public class AuthorityEndpoint {

    @RequestMapping("/publicKey")
    public TavernResponse<RSAPublicKey> rsaPublicKeys(@RequestParam("userName") String userName,
                                                      @RequestParam("systemId") String systemId,
                                                      @RequestParam("roleGroup") String roleGroup) {
        RSAPublicKey publicKey = new RSAPublicKey();
        publicKey.setPublicKey("abc");
        return TavernResponse.ok(publicKey);
    }

    @RequestMapping("/login")
    public TavernResponse<TokenAuthority> login(@RequestParam("userName") String userName,
                                                @RequestParam("password") String password,
                                                @RequestParam("systemId") String systemId,
                                                @RequestParam("roleGroup") String roleGroup) {
        TokenAuthority tokenAuthority = new TokenAuthority();
        return TavernResponse.ok(tokenAuthority);
    }

    @RequestMapping("/logout")
    public TavernResponse<TokenAuthority> login(@RequestParam("userName") String userName,
                                                @RequestParam("systemId") String systemId,
                                                @RequestParam("roleGroup") String roleGroup) {
        TokenAuthority tokenAuthority = new TokenAuthority();
        return TavernResponse.OK;
    }
}

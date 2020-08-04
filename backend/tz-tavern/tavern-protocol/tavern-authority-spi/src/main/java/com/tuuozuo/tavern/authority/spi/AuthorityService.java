package com.tuuozuo.tavern.authority.spi;

import com.tuozuo.tavern.common.protocol.TavernResponse;
import com.tuuozuo.tavern.authority.spi.vo.UserVO;

/**
 * Code Monkey: 何彪 <br>
 * Dev Time: 2020/7/26 <br>
 */
public interface AuthorityService {

    TavernResponse createUser(UserVO userVO);
    TavernResponse modifyUser(UserVO userVO);

}

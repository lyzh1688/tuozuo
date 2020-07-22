package com.tuozuo.tavern.libs.auth.session;

/**
 * Created by 刘悦之 on 2020/7/18.
 */
public interface SessionManager {
    boolean valid(Session session);
    void createOrRefreshSession(Session session);
}

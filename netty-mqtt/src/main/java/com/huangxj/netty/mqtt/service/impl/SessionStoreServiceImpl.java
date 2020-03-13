package com.huangxj.netty.mqtt.service.impl;

import com.huangxj.netty.mqtt.pojo.SessionStore;
import com.huangxj.netty.mqtt.service.SessionStoreService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huangxj
 * 会话存储接口类
 */
public class SessionStoreServiceImpl implements SessionStoreService {

    private Map<String, SessionStore> sessionCache = new ConcurrentHashMap<String, SessionStore>();

    @Override
    public void put(String clientId, SessionStore sessionStore) {
        sessionCache.put(clientId, sessionStore);
    }

    @Override
    public SessionStore get(String clientId) {
        return sessionCache.get(clientId);
    }

    @Override
    public boolean containsKey(String clientId) {
        return sessionCache.containsKey(clientId);
    }

    @Override
    public void remove(String clientId) {
        sessionCache.remove(clientId);
    }


}

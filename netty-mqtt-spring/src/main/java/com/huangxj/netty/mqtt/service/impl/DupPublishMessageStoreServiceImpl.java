package com.huangxj.netty.mqtt.service.impl;

import com.huangxj.netty.mqtt.cache.DupPublishMessageCache;
import com.huangxj.netty.mqtt.pojo.DupPublishMessageStore;
import com.huangxj.netty.mqtt.service.DupPublishMessageStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author huangxj
 */
@Service
public class DupPublishMessageStoreServiceImpl implements DupPublishMessageStoreService {

    @Autowired
    private DupPublishMessageCache dupPublishMessageCache;


    @Override
    public void put(String clientId, DupPublishMessageStore dupPublishMessageStore) {
        dupPublishMessageCache.put(clientId,dupPublishMessageStore.getMessageId(),dupPublishMessageStore);
    }

    @Override
    public List<DupPublishMessageStore> get(String clientId) {
        if (dupPublishMessageCache.containsKey(clientId)){
            ConcurrentHashMap<Integer,DupPublishMessageStore> map = dupPublishMessageCache.get(clientId);
            Collection<DupPublishMessageStore> collection = map.values();
            return new ArrayList<>(collection);
        }
        return new ArrayList<>();
    }

    @Override
    public void remove(String clientId, int messageId) {
        dupPublishMessageCache.remove(clientId,messageId);
    }

    @Override
    public void removeByClient(String clientId) {
        dupPublishMessageCache.remove(clientId);
    }
}

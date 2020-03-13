package com.huangxj.netty.mqtt.service.impl;

import com.huangxj.netty.mqtt.cache.DupPubRelMessageCache;
import com.huangxj.netty.mqtt.pojo.DupPubRelMessageStore;
import com.huangxj.netty.mqtt.service.DupPubRelMessageStoreService;
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
public class DupPubRelMessageStoreServiceImpl implements DupPubRelMessageStoreService {
    @Autowired
    private DupPubRelMessageCache dupPubRelMessageCache;

    @Override
    public void put(String clientId, DupPubRelMessageStore dupPubRelMessageStore) {
        dupPubRelMessageCache.put(clientId,dupPubRelMessageStore.getMessageId(),dupPubRelMessageStore);
    }

    @Override
    public List<DupPubRelMessageStore> get(String clientId) {
        if (dupPubRelMessageCache.containsKey(clientId)){
            ConcurrentHashMap<Integer, DupPubRelMessageStore> map = dupPubRelMessageCache.get(clientId);
            Collection<DupPubRelMessageStore> collection = map.values();
            return new ArrayList<>(collection);
        }
        return new ArrayList<>();
    }

    @Override
    public void remove(String clientId, int messageId) {
        dupPubRelMessageCache.remove(clientId,messageId);
    }

    @Override
    public void removeByClient(String clientId) {
        if (dupPubRelMessageCache.containsKey(clientId)){
            dupPubRelMessageCache.remove(clientId);
        }
    }
}

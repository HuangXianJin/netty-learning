package com.huangxj.netty.mqtt.service;

import com.huangxj.netty.mqtt.pojo.SubscribeStore;

import java.util.List;

/**
 * @author james
 * 订阅存储服务接口
 */
public interface SubscribeStoreService {
    /**
     * 存储订阅
     */
    void put(String topicFilter, SubscribeStore subscribeStore);

    /**
     * 删除订阅
     */
    void remove(String topicFilter, String clientId);

    /**
     * 删除clientId的订阅
     */
    void removeForClient(String clientId);

    /**
     * 获取订阅存储集
     */
    List<SubscribeStore> search(String topic);
}

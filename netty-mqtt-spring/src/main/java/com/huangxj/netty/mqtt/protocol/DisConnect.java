package com.huangxj.netty.mqtt.protocol;

import com.huangxj.netty.mqtt.pojo.SessionStore;
import com.huangxj.netty.mqtt.service.DupPubRelMessageStoreService;
import com.huangxj.netty.mqtt.service.DupPublishMessageStoreService;
import com.huangxj.netty.mqtt.service.SessionStoreService;
import com.huangxj.netty.mqtt.service.SubscribeStoreService;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author james
 * @date 2018年10月21日 10:31
 */
@Slf4j
@Service
public class DisConnect {

    @Autowired
    private SessionStoreService sessionStoreService;

    @Autowired
    private DupPublishMessageStoreService dupPublishMessageStoreService;

    @Autowired
    private DupPubRelMessageStoreService dupPubRelMessageStoreService;

    @Autowired
    private SubscribeStoreService subscribeStoreService;


    public void processDisConnect(Channel channel) {
        String clientId = (String) channel.attr(AttributeKey.valueOf("clientId")).get();
        SessionStore sessionStore = sessionStoreService.get(clientId);
        if (sessionStore != null && sessionStore.isCleanSession()) {
            subscribeStoreService.removeForClient(clientId);
            dupPublishMessageStoreService.removeByClient(clientId);
            dupPubRelMessageStoreService.removeByClient(clientId);
        }
        log.info("[断开连接]DISCONNECT - clientId: {}, cleanSession: {}", clientId, sessionStore.isCleanSession());
        sessionStoreService.remove(clientId);
        channel.close();
    }
}

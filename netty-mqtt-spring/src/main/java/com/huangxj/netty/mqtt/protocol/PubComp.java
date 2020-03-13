package com.huangxj.netty.mqtt.protocol;

import com.huangxj.netty.mqtt.service.DupPubRelMessageStoreService;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.MqttMessageIdVariableHeader;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author james
 * @date 2018年10月21日19:08
 * PUBCOMP连接处理
 */
@Slf4j
@Service
public class PubComp {

    @Autowired
    private DupPubRelMessageStoreService dupPubRelMessageStoreService;

    public void processPubComp(Channel channel, MqttMessageIdVariableHeader variableHeader){
        int messageId = variableHeader.messageId();
        log.info("[发布完成]PUBCOMP - clientId: {}, messageId: {}", (String) channel.attr(AttributeKey.valueOf("clientId")).get(), messageId);
        dupPubRelMessageStoreService.remove((String)channel.attr(AttributeKey.valueOf("clientId")).get(), variableHeader.messageId());
    }
}

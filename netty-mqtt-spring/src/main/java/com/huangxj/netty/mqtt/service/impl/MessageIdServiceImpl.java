package com.huangxj.netty.mqtt.service.impl;

import com.huangxj.netty.mqtt.service.MessageIdService;
import org.springframework.stereotype.Service;

/**
 * @author huangxj
 */
@Service
public class MessageIdServiceImpl implements MessageIdService {
    @Override
    public int getNextMessageId() {
        return 0;
    }
}

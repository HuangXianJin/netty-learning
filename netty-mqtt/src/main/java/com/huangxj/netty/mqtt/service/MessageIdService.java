package com.huangxj.netty.mqtt.service;
/**
 * @author huangxj
 * 分布式生成报文标识符
 */
public interface MessageIdService {
    /**
     * 获取报文标识符
     */
    int getNextMessageId();
}

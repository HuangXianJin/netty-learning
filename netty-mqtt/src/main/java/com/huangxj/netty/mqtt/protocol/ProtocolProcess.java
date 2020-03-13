package com.huangxj.netty.mqtt.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.mqtt.*;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import static io.netty.handler.codec.mqtt.MqttMessageType.CONNACK;
import static io.netty.handler.codec.mqtt.MqttQoS.AT_MOST_ONCE;

/**
 * @author james
 * 协议处理
 * ├── Connect -- 连接服务端
 *   ├── DisConnect -- 断开连接
 *   ├── PingReq -- 心跳请求
 *   ├── PubAck -- 发布确认
 *   ├── PubComp -- 发布完成(QoS2,第散步)
 *   ├── Publish -- 发布消息
 *   ├── PubRec -- 发布收到(QoS2,第一步)
 *   ├── PubRel -- 发布释放(QoS2,第二步)
 *   ├── Subscribe -- 订阅主题
 *   ├── UnSubscribe -- 取消订阅
 */
@Slf4j
public class ProtocolProcess {

    /**
     * Connect -- 连接服务端
     */
    public void connect(ChannelHandlerContext ctx, MqttConnectMessage msg) {
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(CONNACK, false, AT_MOST_ONCE, false, 0);
        MqttConnAckVariableHeader mqttConnAckVariableHeader = new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_ACCEPTED, true);
        ctx.writeAndFlush(new MqttConnAckMessage(mqttFixedHeader, mqttConnAckVariableHeader));
    }

    public void publish(ChannelHandlerContext ctx, MqttPublishMessage msg) {
    }

    public void pubAck(ChannelHandlerContext ctx, MqttMessageIdVariableHeader variableHeader) {
    }

    public void pubRec(ChannelHandlerContext ctx, MqttMessageIdVariableHeader variableHeader) {
    }

    public void pubRel(ChannelHandlerContext ctx, MqttMessageIdVariableHeader variableHeader) {
    }

    public void pubComp(ChannelHandlerContext ctx, MqttMessageIdVariableHeader variableHeader) {
    }

    public void subscribe(ChannelHandlerContext ctx, MqttSubscribeMessage msg) {
    }

    public void unSubscribe(ChannelHandlerContext ctx, MqttUnsubscribeMessage msg) {
    }

    public void pingReq(ChannelHandlerContext ctx, MqttMessage msg) {
        MqttMessage pingRespMessage = MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.PINGRESP, false, MqttQoS.AT_MOST_ONCE, false, 0),
                null,
                null);
        log.info("PINGREQ - clientId: {}", ctx.channel().attr(AttributeKey.valueOf("clientId")));
        ctx.channel().writeAndFlush(pingRespMessage);
    }

    public void disConnect(ChannelHandlerContext ctx, MqttMessage msg) {
        ctx.channel().close();
    }
}

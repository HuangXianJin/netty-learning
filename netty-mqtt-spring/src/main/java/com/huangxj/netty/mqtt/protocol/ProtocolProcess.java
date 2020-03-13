package com.huangxj.netty.mqtt.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.mqtt.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author james
 * 协议处理
 * ├── Connect -- 连接服务端
 * ├── DisConnect -- 断开连接
 * ├── PingReq -- 心跳请求
 * ├── PubAck -- 发布确认
 * ├── PubComp -- 发布完成(QoS2,第散步)
 * ├── Publish -- 发布消息
 * ├── PubRec -- 发布收到(QoS2,第一步)
 * ├── PubRel -- 发布释放(QoS2,第二步)
 * ├── Subscribe -- 订阅主题
 * ├── UnSubscribe -- 取消订阅
 */
@Slf4j
@Component
public class ProtocolProcess {

    @Autowired
    Connect connect;

    @Autowired
    DisConnect disConnect;

    @Autowired
    PubAck pubAck;

    @Autowired
    PubComp pubComp;

    @Autowired
    PubRec pubRec;

    @Autowired
    PubRel pubRel;

    @Autowired
    PingReq pingReq;

    @Autowired
    Publish publish;

    @Autowired
    Subscribe subscribe;

    @Autowired
    UnSubscribe unSubscribe;

    public void process(ChannelHandlerContext ctx, MqttMessage msg) {
        if (msg.decoderResult().isFailure()) {
            Throwable cause = msg.decoderResult().cause();
            if (cause instanceof MqttUnacceptableProtocolVersionException) {
                ctx.writeAndFlush(MqttMessageFactory.newMessage(
                        new MqttFixedHeader(MqttMessageType.CONNACK, false, MqttQoS.AT_MOST_ONCE, false, 0),
                        new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_REFUSED_UNACCEPTABLE_PROTOCOL_VERSION, false),
                        null));
            } else if (cause instanceof MqttIdentifierRejectedException) {
                ctx.writeAndFlush(MqttMessageFactory.newMessage(
                        new MqttFixedHeader(MqttMessageType.CONNACK, false, MqttQoS.AT_MOST_ONCE, false, 0),
                        new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_REFUSED_IDENTIFIER_REJECTED, false),
                        null));
            }
            ctx.close();
            return;
        }

        switch (msg.fixedHeader().messageType()) {
            case CONNECT:
                connect(ctx, (MqttConnectMessage) msg);
                break;
            case CONNACK:
                break;
            case PUBLISH:
                publish(ctx, (MqttPublishMessage) msg);
                break;
            case PUBACK:
                pubAck(ctx, (MqttMessageIdVariableHeader) msg.variableHeader());
                break;
            case PUBREC:
                pubRec(ctx, (MqttMessageIdVariableHeader) msg.variableHeader());
                break;
            case PUBREL:
                pubRel(ctx, (MqttMessageIdVariableHeader) msg.variableHeader());
                break;
            case PUBCOMP:
                pubComp(ctx, (MqttMessageIdVariableHeader) msg.variableHeader());
                break;
            case SUBSCRIBE:
                subscribe(ctx, (MqttSubscribeMessage) msg);
                break;
            case SUBACK:
                break;
            case UNSUBSCRIBE:
                unSubscribe(ctx, (MqttUnsubscribeMessage) msg);
                break;
            case UNSUBACK:
                break;
            case PINGREQ:
                pingReq(ctx, msg);
                break;
            case PINGRESP:
                break;
            case DISCONNECT:
                disConnect(ctx, msg);
                break;
            default:
                break;
        }
    }

    /**
     * Connect -- 连接服务端
     */
    public void connect(ChannelHandlerContext ctx, MqttConnectMessage msg) {
        connect.processConnect(ctx.channel(), msg);
    }

    public void publish(ChannelHandlerContext ctx, MqttPublishMessage msg) {
        publish.processPublish(ctx.channel(), msg);
    }

    public void pubAck(ChannelHandlerContext ctx, MqttMessageIdVariableHeader variableHeader) {
        pubAck.processPubAck(ctx.channel(), variableHeader);
    }

    public void pubRec(ChannelHandlerContext ctx, MqttMessageIdVariableHeader variableHeader) {
        pubRec.processPubRec(ctx.channel(), variableHeader);
    }

    public void pubRel(ChannelHandlerContext ctx, MqttMessageIdVariableHeader variableHeader) {
        pubRel.processPubRel(ctx.channel(), variableHeader);
    }

    public void pubComp(ChannelHandlerContext ctx, MqttMessageIdVariableHeader variableHeader) {
        pubComp.processPubComp(ctx.channel(), variableHeader);
    }

    public void subscribe(ChannelHandlerContext ctx, MqttSubscribeMessage msg) {
        subscribe.processSubscribe(ctx.channel(), msg);
    }

    public void unSubscribe(ChannelHandlerContext ctx, MqttUnsubscribeMessage msg) {
        unSubscribe.processUnSubscribe(ctx.channel(), msg);
    }

    public void pingReq(ChannelHandlerContext ctx, MqttMessage msg) {
        pingReq.processPingReq(ctx.channel(), msg);
    }

    public void disConnect(ChannelHandlerContext ctx, MqttMessage msg) {
        disConnect.processDisConnect(ctx.channel());
    }
}

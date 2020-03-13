package com.huangxj.netty.mqtt;


import com.huangxj.netty.mqtt.protocol.ProtocolProcess;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.mqtt.*;
import lombok.extern.java.Log;

import java.io.IOException;

/**
 * @author huangxj
 */
@Log
@ChannelHandler.Sharable
public class MqttServerHandler extends SimpleChannelInboundHandler<MqttMessage> {

    ProtocolProcess protocol = new ProtocolProcess();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException) {
            // 远程主机强迫关闭了一个现有的连接的异常
            ctx.close();
        } else {
            super.exceptionCaught(ctx, cause);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MqttMessage msg) throws Exception {
        log.info(msg.toString());
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
                protocol.connect(ctx,(MqttConnectMessage) msg);
                break;
            case CONNACK:
                break;
            case PUBLISH:
                protocol.publish(ctx,(MqttPublishMessage) msg);
                break;
            case PUBACK:
                protocol.pubAck(ctx ,(MqttMessageIdVariableHeader) msg.variableHeader());
                break;
            case PUBREC:
                protocol.pubRec(ctx, (MqttMessageIdVariableHeader) msg.variableHeader());
                break;
            case PUBREL:
                protocol.pubRel(ctx, (MqttMessageIdVariableHeader) msg.variableHeader());
                break;
            case PUBCOMP:
                protocol.pubComp(ctx, (MqttMessageIdVariableHeader) msg.variableHeader());
                break;
            case SUBSCRIBE:
                protocol.subscribe(ctx, (MqttSubscribeMessage) msg);
                break;
            case SUBACK:
                break;
            case UNSUBSCRIBE:
                protocol.unSubscribe(ctx, (MqttUnsubscribeMessage) msg);
                break;
            case UNSUBACK:
                break;
            case PINGREQ:
                protocol.pingReq(ctx, msg);
                break;
            case PINGRESP:
                break;
            case DISCONNECT:
                protocol.disConnect(ctx, msg);
                break;
            default:
                break;
        }


    }
}

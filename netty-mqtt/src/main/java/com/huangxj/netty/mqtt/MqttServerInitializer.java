package com.huangxj.netty.mqtt;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;


public class MqttServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();

        p.addLast("decoder", new MqttDecoder(65536));
        p.addLast("encoder", MqttEncoder.INSTANCE);
        p.addLast(new HttpServerExpectContinueHandler());
        p.addLast(new MqttServerHandler());
    }
}

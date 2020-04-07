package com.huangxj.netty.server;


import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;
import java.util.Date;

/**
 * @ClassName ServerHandler2
 * @Description TODO
 * @Author: huangxj
 * @Create: 2020-03-11 11:36
 * @Version V1.0
 **/
@ChannelHandler.Sharable
public class ServerHandler1 extends SimpleChannelInboundHandler<String> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 为新连接发送庆祝
        ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
        ctx.write("It is " + new Date() + " now.\r\n");
        ctx.flush();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String request) throws Exception {
        String response;
        System.out.println("this is server1");
        // response = request + "this is server1";
        // ctx.fireChannelRead(response);
        ctx.fireChannelRead(request);
        // ctx.flush();
        // 收到消息直接打印输出1

//        System.out.println(ctx.channel().remoteAddress() + " Say : " + request);
//
//        // 返回客户端消息 - 我已经接收到了你的消息
//        ctx.writeAndFlush("Received your message !\n");

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

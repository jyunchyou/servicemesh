package com.alibaba.dubbo.performance.demo.agent.net;

import com.alibaba.dubbo.performance.demo.agent.handler.HttpServerInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ConsumerChannelInitalizer extends ChannelInitializer<SocketChannel>
{
    HttpServerInboundHandler httpServerInboundHandler = null;

    public ConsumerChannelInitalizer(HttpServerInboundHandler httpServerInboundHandler)
    {
        this.httpServerInboundHandler = httpServerInboundHandler;
    }

    public void initChannel(SocketChannel ch)
            throws Exception
    {
    }
}
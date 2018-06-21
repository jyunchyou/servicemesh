package com.alibaba.dubbo.performance.demo.agent.net;

import com.alibaba.dubbo.performance.demo.agent.handler.HttpServerInboundHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HttpServer
{
    public void start(int port)
            throws Exception
    {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            ((ServerBootstrap)b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)).childHandler
                    (new ConsumerChannelInitalizer(new HttpServerInboundHandler()))
                    .option(ChannelOption.SO_BACKLOG, Integer.valueOf(128));

            ChannelFuture localChannelFuture = b.bind(port).sync();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        HttpServer server = new HttpServer();

        server.start(8844);
    }
}
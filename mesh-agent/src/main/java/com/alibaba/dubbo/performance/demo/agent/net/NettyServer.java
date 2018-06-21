package com.alibaba.dubbo.performance.demo.agent.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.util.concurrent.locks.Lock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServer
        implements NettyBase
{
    Logger logger;
    private EventLoopGroup work;
    private EventLoopGroup boss;

    public NettyServer()
    {
        this.logger = LoggerFactory.getLogger("NettyServer");

        this.work = new NioEventLoopGroup(3);

        this.boss = new NioEventLoopGroup(1);
    }

    public void bind(int port)
    {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(this.boss, this.work);

        bootstrap.channel(NioServerSocketChannel.class);

        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, Boolean.valueOf(true));

        bootstrap.childHandler(new NewServerChannelInitializer());


        ChannelFuture channelFuture = null;
        try
        {
            channelFuture = bootstrap.bind(port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

    public Channel start(String address, int port, Lock lock)
    {
        return null;
    }
}
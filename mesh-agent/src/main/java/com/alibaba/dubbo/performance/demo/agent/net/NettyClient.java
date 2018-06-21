package com.alibaba.dubbo.performance.demo.agent.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.concurrent.locks.Lock;

public class NettyClient
        implements NettyBase
{
    private EventLoopGroup group;

    public NettyClient()
    {
        this.group = new NioEventLoopGroup(1);
    }

    public void bind(int port)
    {
    }

    public Channel start(String address, int port, Lock lock)
    {
        Bootstrap bootstrap = new Bootstrap();

        ((Bootstrap)((Bootstrap)((Bootstrap)((Bootstrap)bootstrap.group(this.group)).channel
                (NioSocketChannel.class))
                .option
                        (ChannelOption.TCP_NODELAY,
                                Boolean.valueOf(true)))
                .option
                        (ChannelOption.SO_KEEPALIVE,
                                Boolean.valueOf(true)))
                .option(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT)
                .handler
                        (new NewClientChannelInitializer(lock));

        Channel channel = null;
        try {
            ChannelFuture future = bootstrap.connect(address, port);
            channel = future.channel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return channel;
    }
}
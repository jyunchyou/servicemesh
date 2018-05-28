package com.alibaba.dubbo.performance.demo.agent.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient implements NettyBase{


    private EventLoopGroup group = new NioEventLoopGroup();


    @Override
    public void bind(int port) {

    }

    public Channel start(String address,int port){


        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new NewClientChannelInitializer());


            Channel channel = null;
        try {
            ChannelFuture future = bootstrap.connect(address,port);
            System.out.println(address);
            System.out.println(port);
            channel = future.channel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return channel;
    }


}
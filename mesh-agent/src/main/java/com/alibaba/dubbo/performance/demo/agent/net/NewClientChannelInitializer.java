package com.alibaba.dubbo.performance.demo.agent.net;

import com.alibaba.dubbo.performance.demo.agent.handler.NettyClientHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import java.util.concurrent.locks.Lock;

public class NewClientChannelInitializer extends ChannelInitializer<SocketChannel> {



    private Lock lock = null;
    public NewClientChannelInitializer(Lock lock){

        this.lock = lock;
    }
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();



        pipeline.addLast(new NettyClientHandlerAdapter(lock));

    }
}
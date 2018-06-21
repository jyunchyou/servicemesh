package com.alibaba.dubbo.performance.demo.agent.net;

import com.alibaba.dubbo.performance.demo.agent.handler.NettyServerHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;




public class NewServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private NettyServerHandlerAdapter nettyServerHandlerAdapter = null;



    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline pipeline = socketChannel.pipeline();


        pipeline.addLast(new NettyServerHandlerAdapter());
    }

}
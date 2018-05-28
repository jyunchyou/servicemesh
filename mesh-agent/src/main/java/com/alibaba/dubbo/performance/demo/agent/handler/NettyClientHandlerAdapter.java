package com.alibaba.dubbo.performance.demo.agent.handler;

import com.alibaba.dubbo.performance.demo.agent.code.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandlerAdapter extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg){
           byte[] responseData = (byte[]) msg;
        }
    }


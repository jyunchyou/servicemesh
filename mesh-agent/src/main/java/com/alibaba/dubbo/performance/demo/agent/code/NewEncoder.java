package com.alibaba.dubbo.performance.demo.agent.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;



public class NewEncoder extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {
        if(message == null){
            throw new Exception("未获得消息内容");
        }



        byte[] b = message.getMsgBody();

        byteBuf.writeInt(b.length);
        byteBuf.writeBytes(b);


    }
}
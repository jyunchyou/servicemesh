package com.alibaba.dubbo.performance.demo.agent.code;

import com.alibaba.dubbo.performance.demo.agent.code.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class NewDecoder extends LengthFieldBasedFrameDecoder {


    private static final int HEADER_SIZE = 2;

    private int length;
    private String msgBody;


    /**
     *
     * @param maxFrameLength   网络字节序，默认为大端字节序
     * @param lengthFieldOffset 消息中长度字段偏移的字节数
     * @param lengthFieldLength 数据帧的最大长度
     * @param lengthAdjustment 该字段加长度字段等于数据帧的长度
     * @param initialBytesToStrip 从数据帧中跳过的字节数
     * @param failFast 如果为true，则表示读取到长度域，TA的值的超过maxFrameLength，就抛出一个 TooLongFrameException
     */
    public NewDecoder(int maxFrameLength, int lengthFieldOffset,
                      int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip,
                      boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength,
                lengthAdjustment, initialBytesToStrip, failFast);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if(in == null){
            return null;
        }
        if(in.readableBytes() < HEADER_SIZE){
            throw new Exception("错误的消息");
        }

        /**
         * 通过源码我们能看到在读的过程中
         * 每读一次读过的字节即被抛弃
         * 即指针会往前跳
         */


        length = in.readInt();


        if(in.readableBytes() < length){
            throw new Exception("消息不正确");
        }

        ByteBuf buf = in.readBytes(length);
        byte[] b = new byte[buf.readableBytes()];
        buf.readBytes(b);


        Message msg = new Message(length,b);
        return msg;
    }
}
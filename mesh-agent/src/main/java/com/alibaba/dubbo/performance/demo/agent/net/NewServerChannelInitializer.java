package com.alibaba.dubbo.performance.demo.agent.net;

import com.alibaba.dubbo.performance.demo.agent.code.NewDecoder;
import com.alibaba.dubbo.performance.demo.agent.handler.NettyServerHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class NewServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    private  final int MAX_FRAME_LENGTH;
    private  final int LENGTH_FIELD_LENGTH;
    private  final int LENGTH_FIELD_OFFSET;
    private  final int LENGTH_ADJUSTMENT;
    private  final int INITIAL_BYTES_TO_STRIP;

    public NewServerChannelInitializer(int MAX_FRAME_LENGTH, int LENGTH_FIELD_LENGTH, int LENGTH_FIELD_OFFSET, int LENGTH_ADJUSTMENT, int INITIAL_BYTES_TO_STRIP) {
        this.MAX_FRAME_LENGTH = MAX_FRAME_LENGTH;
        this.LENGTH_FIELD_LENGTH = LENGTH_FIELD_LENGTH;
        this.LENGTH_FIELD_OFFSET = LENGTH_FIELD_OFFSET;
        this.LENGTH_ADJUSTMENT = LENGTH_ADJUSTMENT;
        this.INITIAL_BYTES_TO_STRIP = INITIAL_BYTES_TO_STRIP;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new NewDecoder(MAX_FRAME_LENGTH,LENGTH_FIELD_LENGTH,LENGTH_FIELD_OFFSET,LENGTH_ADJUSTMENT,INITIAL_BYTES_TO_STRIP,false));
        // 自己的逻辑Handler
        pipeline.addLast("handler", new NettyServerHandlerAdapter());
    }

}
package com.alibaba.dubbo.performance.demo.agent.handler;

import com.alibaba.dubbo.performance.demo.agent.dubbo.RpcClient;
import com.alibaba.dubbo.performance.demo.agent.registry.EtcdRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandlerAdapter extends ChannelInboundHandlerAdapter
{
    private RpcClient rpcClient;
    private ByteBuf buffer;
    boolean test;
    private Object lock;

    public NettyServerHandlerAdapter()
    {
        this.rpcClient = new RpcClient(EtcdRegistry.etcdRegistry);

        this.buffer = null;

        this.test = false;
        this.lock = new Object();
    }

    public void channelActive(ChannelHandlerContext ctx)
            throws Exception
    {
    }

    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg)
            throws InterruptedException
    {
//        synchronized (this.lock)
//        {
//            ByteBuf byteBuf = (ByteBuf)msg;
//
//            if (this.buffer != null) {
//                this.buffer.writeBytes(byteBuf);
//
//                byteBuf = this.buffer;
//                this.buffer = null;
//            }
//
//            while (byteBuf.readableBytes() >= 4) {
//                int len = byteBuf.readInt();
//
//                if (len > byteBuf.readableBytes()) {
//                    this.buffer = byteBuf;
//                    return; }
//                if (len == byteBuf.readableBytes()) {
//                    parameter = new byte[len];
//                    byteBuf.readBytes(parameter);
//
//                    result = new byte[0];
//
//                    ByteBuf byteBuf1 = Unpooled.buffer(result.length + 4);
//                    byteBuf1.writeInt(result.length);
//                    byteBuf1.writeBytes(result);
//
//                    channelHandlerContext.writeAndFlush(byteBuf1);
//
//                    return;
//                }
//
//                byte[] parameter = new byte[len];
//                byteBuf.readBytes(parameter);
//
//                byte[] result = new byte[0];
//
//                int test = NettyClientHandlerAdapter.toInt(result);
//
//                ByteBuf byteBuf1 = Unpooled.buffer(result.length + 4);
//                byteBuf1.writeInt(result.length);
//                byteBuf1.writeBytes(result);
//
//                channelHandlerContext.writeAndFlush(byteBuf1);
//            }
//
//            this.buffer = byteBuf;
//        }
//    }
}}
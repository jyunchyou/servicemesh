package com.alibaba.dubbo.performance.demo.agent.handler;

import com.alibaba.dubbo.performance.demo.agent.net.NioServer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Hashtable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NettyClientHandlerAdapter extends ChannelInboundHandlerAdapter
{
    private volatile ByteBuf buffer = null;

    private Object lock = new Object();

    private ReentrantLock reentrantLock = null;

    OutputStream channelHandlerContext1;

    private String afterLine = System.getProperty("line.separator");

    public NettyClientHandlerAdapter(Lock lock){



        this.reentrantLock = (ReentrantLock) lock;
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws InterruptedException, IOException {



        synchronized (lock) {

            ByteBuf byteBuf = (ByteBuf) msg;

            if (buffer != null) {
                byte[] b = new byte[buffer.readableBytes()];
                buffer.readBytes(b);
                ByteBuf bb = Unpooled.buffer(b.length + byteBuf.readableBytes());
                bb.writeBytes(b);
                bb.writeBytes(byteBuf);



                byteBuf = bb;
                buffer = null;

            }

            while (byteBuf.readableBytes() > 1) {

                byteBuf.markReaderIndex();
                int len = byteBuf.readByte();


                if (len > byteBuf.readableBytes()) {
                    byteBuf.resetReaderIndex();
                    buffer = byteBuf;
                    return;
                } else if (len == byteBuf.readableBytes()) {

                      try {
                        int backDataLen = byteBuf.readByte();
                        byte[] backData = new byte[backDataLen];
                        byteBuf.readBytes(backData);


                        int hashCodeLen = byteBuf.readByte();
                        byte[] hashCode = new byte[hashCodeLen];
                        byteBuf.readBytes(hashCode);


                        String hash = new String(hashCode).trim();

                        channelHandlerContext1 = (OutputStream) NioServer.concurrentHashMap.remove(hash);



                        StringBuilder  sendStr = new StringBuilder();

                          sendStr.append("Http/1.1 200 Ok" + afterLine + "Content-Type:text/html;" + afterLine + afterLine);


                          sendStr.append(new String(backData).trim());

                          channelHandlerContext1.write(sendStr.toString().getBytes());
                          channelHandlerContext1.flush();
                          channelHandlerContext1.close();

                        return;
                    }catch (Exception e){
                        return;
                    }



                } else {


                    int backDataLen = byteBuf.readByte();




                    try {
                        byte[] backData = new byte[backDataLen];


                        byteBuf.readBytes(backData);


                        int hashCodeLen = byteBuf.readByte();
                        byte[] hashCode = new byte[hashCodeLen];
                        byteBuf.readBytes(hashCode);


                        String hash = new String(hashCode).trim();


                        channelHandlerContext1 = (OutputStream) NioServer.concurrentHashMap.remove(hash);




                        StringBuilder  sendStr = new StringBuilder();

                        sendStr.append("Http/1.1 200 Ok" + afterLine + "Content-Type:text/html;" + afterLine + afterLine);

                        sendStr.append(new String(backData).trim());

                        channelHandlerContext1.write(sendStr.toString().getBytes());
                        channelHandlerContext1.flush();
                        channelHandlerContext1.close();
                        continue;

                    }catch (Exception e){

                        return;
                    }
                }

            }

            buffer = byteBuf;


        }

    }
}
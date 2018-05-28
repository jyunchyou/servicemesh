package com.alibaba.dubbo.performance.demo.agent.handler;

import com.alibaba.dubbo.performance.demo.agent.code.Message;
import com.alibaba.dubbo.performance.demo.agent.dubbo.RpcClient;
import com.alibaba.dubbo.performance.demo.agent.registry.EtcdRegistry;
import com.alibaba.dubbo.performance.demo.agent.registry.IRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.locks.Lock;

/**
 * Created by fbhw on 18-5-27.
 */
public class NettyServerHandlerAdapter extends SimpleChannelInboundHandler {

    Logger logger = LoggerFactory.getLogger(NettyServerHandlerAdapter.class);
    private IRegistry registry = new EtcdRegistry(System.getProperty("etcd.url"));

    private RpcClient rpcClient = new RpcClient(registry);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        logger.info("method channelActive has executed");
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg){
        Message message = (Message) msg;
        byte[] body = message.getMsgBody();
        int index = 0;
        byte interfaceNameByteLen = body[index++];
        int interfaceNameLen = interfaceNameByteLen;
        byte[] interfaceNameByte = new byte[interfaceNameLen];
        for (int i = 0;i<interfaceNameLen;i++) {
            interfaceNameByte[i] = body[index++];

        }
        byte methodByteLen = body[index++];
        int methodLen = methodByteLen;
        byte[] methodByte = new byte[methodLen];
        for (int i = 0;i<methodByteLen;i++) {
            methodByte[i] = body[index++];

        }
        byte parameterTypeStringByteLen = body[index++];
        int parameterTypeLen = parameterTypeStringByteLen;
        byte[] parameterTypeByte = new byte[parameterTypeLen];
        for (int i = 0;i<parameterTypeLen;i++) {
            parameterTypeByte[i] = body[index++];

        }
        byte parameterByteLen = body[index++];
        int parameterLen = methodByteLen;
        byte[] parameterByte = new byte[parameterLen];
        for (int i = 0;i<parameterLen;i++) {
            parameterByte[i] = body[index++];

        }

        String parameterTypesString = new String(parameterTypeByte);
        String parameterString = new String(parameterByte);
        String interfaceNameString = new String(interfaceNameByte);
        String methodString = new String(methodByte);
        System.out.println(parameterString);

        System.out.println(parameterTypesString);
        System.out.println(methodString);
        System.out.println(interfaceNameString);



        byte[] result = new byte[0];
        try {
            result = (byte[]) rpcClient.invoke(interfaceNameString,methodString,parameterTypesString,parameterString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        channelHandlerContext.write(result);


    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

        System.out.println("channelRead0");
    }


}

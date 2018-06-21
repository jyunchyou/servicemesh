package com.alibaba.dubbo.performance.demo.agent.net;

import com.alibaba.dubbo.performance.demo.agent.registry.EtcdRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecuteRunnable implements Runnable {



    ServerSocket serverSocket = null;

    Random random = new Random();

    AtomicInteger atomicInteger = null;

    private int BYTE_LENTH = 2;
    public ExecuteRunnable(ServerSocket serverSocket,AtomicInteger atomicInteger){
        this.serverSocket = serverSocket;
        this.atomicInteger = atomicInteger;
    }
    @Override
    public void run() {




        for (;;) {

            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {


                InputStream in = new DataInputStream(socket.getInputStream());
                OutputStream out = new DataOutputStream(socket.getOutputStream());


                byte[] data = new byte[1310];


                in.read(data);


                String message = new String(data);
                int parameterndex = message.indexOf("parameter=");
                int interfaceNameIndex = message.indexOf("InterfaceName=");
                int methodIndex = message.indexOf("method=");
                int parameterTypesStringIndex = message.indexOf("parameterTypesString=");


                String parameter = message.substring(parameterndex + 10, message.length() - 1).trim();
                String interfaceName = message.substring(interfaceNameIndex + 10,methodIndex).trim();
                String method = message.substring(methodIndex + 10,parameterTypesStringIndex).trim();
                String parameterTypesString = message.substring(parameterTypesStringIndex + 10,parameterndex).trim();

                String id = new String(String.valueOf(atomicInteger.getAndAdd(1)));


                NioServer.concurrentHashMap.put(id, out);

                try {
                    consumer(interfaceName,method,parameterTypesString,parameter,id);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void consumer(String interfaceName, String method, String parameterTypesString, String parameter,String id)
            throws Exception
    {

        byte[] paByte = parameter.getBytes();
        byte[] interfaceNameByte = interfaceName.getBytes();
        byte[] methodByte = method.getBytes();
        byte[] parameterTypeStringByte = parameterTypesString.getBytes();




        byte[] idByte = id.getBytes();

        byte[] bodyLen = new byte[2];
        int len = paByte.length + (BYTE_LENTH * 4 + 1) + idByte.length + interfaceNameByte.length + methodByte.length + parameterTypeStringByte.length;




        if (len > 127) {
            bodyLen[1] = (byte) (len / 127);
            bodyLen[0] = (byte) (len % 127);
        } else {
            bodyLen[0] = (byte) len;
            bodyLen[1] = 0;
        }

        byte[] Len = new byte[2];
        byte[] interfaceByteLen = new byte[2];
        byte[] methodByteLen = new byte[2];
        byte[] parameterTypeStringByteLen = new byte[2];
        int l = paByte.length;
        int interfaceLen = interfaceName.length();
        int methodLen = methodByte.length;
        int parameterTypeStringLen = parameterTypesString.length();
        if (l > 127) {
            Len[1] = (byte) (l / 127);
            Len[0] = (byte) (l % 127);
        } else {
            Len[0] = (byte) l;
            Len[1] = 0;
        }

        if (interfaceLen > 127) {
            interfaceByteLen[1] = (byte) (interfaceLen / 127);
            interfaceByteLen[0] = (byte) (interfaceLen % 127);
        } else {
            interfaceByteLen[0] = (byte) interfaceLen;
            interfaceByteLen[1] = 0;
        }

        if (methodLen > 127) {
            methodByteLen[1] = (byte) (methodLen / 127);
            methodByteLen[0] = (byte) (methodLen % 127);
        } else {
            methodByteLen[0] = (byte) methodLen;
            methodByteLen[1] = 0;
        }

        if (parameterTypeStringLen > 127) {
            parameterTypeStringByteLen[1] = (byte) (parameterTypeStringLen / 127);
            parameterTypeStringByteLen[0] = (byte) (parameterTypeStringLen % 127);
        } else {
            parameterTypeStringByteLen[0] = (byte) parameterTypeStringLen;
            parameterTypeStringByteLen[1] = 0;
        }


        ByteBuf byteBuf = Unpooled.buffer(len + BYTE_LENTH);

        byteBuf.writeBytes(bodyLen);

        byteBuf.writeBytes(Len);
        byteBuf.writeBytes(paByte);
        byteBuf.writeByte(idByte.length);
        byteBuf.writeBytes(idByte);

        byteBuf.writeBytes(interfaceByteLen);
        byteBuf.writeBytes(interfaceNameByte);
        byteBuf.writeBytes(methodByteLen);
        byteBuf.writeBytes(methodByte);
        byteBuf.writeBytes(parameterTypeStringByteLen);
        byteBuf.writeBytes(parameterTypeStringByte);

        int check = this.random.nextInt(EtcdRegistry.channels.size());

        Channel channel = (Channel)EtcdRegistry.channels.get(check);

        ChannelFuture channelFuture = channel.writeAndFlush(parameter);
    }
}

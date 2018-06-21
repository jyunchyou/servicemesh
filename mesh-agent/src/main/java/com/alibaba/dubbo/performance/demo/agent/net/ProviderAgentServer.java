package com.alibaba.dubbo.performance.demo.agent.net;

import com.alibaba.dubbo.performance.demo.agent.dubbo.RpcClient;
import com.alibaba.dubbo.performance.demo.agent.registry.EtcdRegistry;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProviderAgentServer
{
    private Selector selector;
    private RpcClient rpcClient;
    ExecutorService cachedThreadPool;


    public ProviderAgentServer()
    {
        this.rpcClient = new RpcClient(EtcdRegistry.etcdRegistry);

        this.cachedThreadPool = Executors.newCachedThreadPool();

     }



    public ProviderAgentServer init(int port)
            throws IOException
    {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);

        serverChannel.socket().bind(new InetSocketAddress(port));

        this.selector = Selector.open();

        serverChannel.register(this.selector, 16);
        return this;
    }

    public void listen()
            throws IOException
    {
        while (true)
        {
            this.selector.select();

            Iterator ite = this.selector.selectedKeys().iterator();

            while (ite.hasNext()) {
                SelectionKey key = (SelectionKey)ite.next();

                ite.remove();
                try
                {
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel)key.channel();

                        SocketChannel channel = server.accept();
                        channel.configureBlocking(false);

                        channel.register(this.selector, 1);
                    }
                    else if (key.isReadable())
                    {
                        SocketChannel channel = (SocketChannel)key.channel();
                        com.alibaba.dubbo.performance.demo.agent.dubbo.RpcClientHandler.channel = channel;

                        ByteBuffer buffLen = ByteBuffer.allocate(2);
                        ByteBuffer buffer = null;
                        try
                        {
                            channel.read(buffLen);
                            int len1 = buffLen.get(1);
                            int len2 = buffLen.get(0);
                            int len = len1 * 127 + len2;

                            buffer = ByteBuffer.allocate(len);

                            int i = channel.read(buffer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        byte[] len = new byte[2];

                        buffer.get(len);

                        int len1 = len[1];
                        int len2 = len[0];
                        int parameterLen = len1 * 127 + len2;

                        byte[] parameter = new byte[parameterLen];
                        buffer.get(parameter);





                        int hashCodeLen = buffer.get();
                        byte[] hashcode = new byte[hashCodeLen];
                        buffer.get(hashcode);


                        ByteBuffer interfacebuffLen = ByteBuffer.allocate(2);
                        ByteBuffer interfacebuffer = null;
                        try
                        {
                            channel.read(interfacebuffLen);
                            int interfacelen1 = interfacebuffLen.get(1);
                            int interfacelen2 = interfacebuffLen.get(0);
                            int interfacelen = interfacelen1 * 127 + interfacelen2;

                            interfacebuffer = ByteBuffer.allocate(interfacelen);

                            int i = channel.read(interfacebuffer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        byte[] interfacelen = new byte[2];


                        interfacebuffer.get(interfacelen);

                        int interfacelen1 = len[1];
                        int interfacelen2 = len[0];
                        int interfaceLen = len1 * 127 + len2;

                        byte[] interfaceByte = new byte[interfaceLen];
                        interfacebuffer.get(interfaceByte);



                        ByteBuffer methodLen = ByteBuffer.allocate(2);
                        ByteBuffer methodbuffer = null;
                        try
                        {
                            channel.read(methodLen);
                            int methodlen1 = methodLen.get(1);
                            int methodlen2 = methodLen.get(0);
                            int methodlen = methodlen1 * 127 + methodlen2;

                            methodbuffer = ByteBuffer.allocate(methodlen);

                            int i = channel.read(methodbuffer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        byte[] methodlen = new byte[2];

                        methodbuffer.flip();
                        methodbuffer.get(len);

                        int methodlen1 = methodlen[1];
                        int methodlen2 = methodlen[0];
                        int methodparameterLen = methodlen1 * 127 + methodlen2;

                        byte[] methodparameter = new byte[methodparameterLen];
                        methodbuffer.get(methodparameter);


                        ByteBuffer parameterTypeStringLen = ByteBuffer.allocate(2);
                        ByteBuffer parameterTypeStringbuffer = null;
                        try
                        {
                            channel.read(parameterTypeStringLen);
                            int parameterTypeStringlen1 = parameterTypeStringLen.get(1);
                            int parameterTypeStringlen2 = parameterTypeStringLen.get(0);
                            int parameterTypeStringlen = parameterTypeStringlen1 * 127 + parameterTypeStringlen2;

                            parameterTypeStringbuffer = ByteBuffer.allocate(parameterTypeStringlen);

                            int i = channel.read(parameterTypeStringbuffer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        byte[] parameterTypeStringlenByte = new byte[2];

                        parameterTypeStringbuffer.flip();
                        parameterTypeStringbuffer.get(len);

                        int parameterTypeStringlen1 = parameterTypeStringlenByte[1];
                        int parameterTypeStringlen2 = parameterTypeStringlenByte[0];
                        int parameterTypeStringLenInt = len1 * 127 + len2;

                        byte[] parameterTypeByte = new byte[parameterTypeStringLenInt];
                        buffer.get(parameterTypeByte);



                        String message = new String(parameter);
                        String interfaceString = new String(interfaceByte);
                        String methodString = new String(methodparameter);
                        String parameterTypeString = new String(parameterTypeByte);




                        try
                        {
                            this.rpcClient.invoke(interfaceString, methodString, parameterTypeString, message, hashcode);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }

                }
                catch (Exception e)
                {
                    key.cancel();
                }
            }
        }
    }
}
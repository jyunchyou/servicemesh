package com.alibaba.dubbo.performance.demo.agent.net;

import com.alibaba.dubbo.performance.demo.agent.registry.Endpoint;
import com.alibaba.dubbo.performance.demo.agent.registry.EtcdRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NioServer
        implements Runnable
{
    private Selector selector;

    public static Hashtable concurrentHashMap = new Hashtable();

    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

    AtomicInteger atomicInteger = new AtomicInteger(1);
    public int port;



    public ServerSocket serverSocket = null;
    public NioServer(int port)
    {

        this.port = port;


    }

    public void run() {




            ServerSocket server = null;

        try {
            server = new ServerSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }


        fixedThreadPool.submit(new ExecuteRunnable(server,atomicInteger));
        fixedThreadPool.submit(new ExecuteRunnable(server,atomicInteger));
        fixedThreadPool.submit(new ExecuteRunnable(server,atomicInteger));




    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        Object lock = new Object();

        Lock lockTable = new ReentrantLock();

        Class.forName("com.alibaba.dubbo.performance.demo.agent.registry.EtcdRegistry");

        if ("consumer".equals(System.getProperty("type")))
        {
            if (null == EtcdRegistry.endpoints)
                synchronized (lock)
                {
                    if (null == EtcdRegistry.endpoints) {
                        List dus = null;
                        try {
                            EtcdRegistry.endpoints = EtcdRegistry.etcdRegistry.find("nettyPort");

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }

                    //TODO 负载均衡
                    if (null == EtcdRegistry.channels) {
                        EtcdRegistry.channels = new ArrayList<>();
                        NettyClient nettyClient = new NettyClient();

                        List<Long> sizeList = new ArrayList(3);




                        long temp = 0;
                        Endpoint flag = null;
                        for (Endpoint endpoint : EtcdRegistry.endpoints) {




                            long jvmSize = Long.parseLong(endpoint.getPort().substring(endpoint.getPort().indexOf(",") + 1, endpoint.getPort().length()));

                            if (jvmSize > temp) {
                                flag = endpoint;
                                temp = jvmSize;
                            }

                          }

                        long tem = 0;
                        Endpoint smallEndPoint = null;
                        for (Endpoint endpoint : EtcdRegistry.endpoints) {



                            int port = Integer.parseInt(endpoint.getPort().substring(0, endpoint.getPort().indexOf(",")));

                            long jvmSize = Long.parseLong(endpoint.getPort().substring(endpoint.getPort().indexOf(",") + 1, endpoint.getPort().length()));

                            if (jvmSize < tem) {
                                smallEndPoint = endpoint;
                                tem = jvmSize;
                            }

                        }

                            for (Endpoint endpoint : EtcdRegistry.endpoints) {




                            int port = Integer.parseInt(endpoint.getPort().substring(0,endpoint.getPort().indexOf(",")));


                            if (endpoint == flag) {
                                Channel channel = nettyClient.start(endpoint.getHost(), port, lockTable);

                                EtcdRegistry.channels.add(channel);
                                EtcdRegistry.channels.add(channel);
                                EtcdRegistry.channels.add(channel);
                                EtcdRegistry.channels.add(channel);
                                EtcdRegistry.channels.add(channel);
                                EtcdRegistry.channels.add(channel);

                            }
                            else if (endpoint == smallEndPoint) {
                                Channel channel = nettyClient.start(endpoint.getHost(), port, lockTable);

                                EtcdRegistry.channels.add(channel);
                                EtcdRegistry.channels.add(channel);
                                EtcdRegistry.channels.add(channel);
                                EtcdRegistry.channels.add(channel);




                            }

                            else{
                                Channel channel1 = nettyClient.start(endpoint.getHost(), port, lockTable);

                                EtcdRegistry.channels.add(channel1);

                                EtcdRegistry.channels.add(channel1);
                            }
                            }








                    }
                }


            NioServer serverApp = new NioServer(Integer.parseInt(System.getProperty("server.port")));
            serverApp.run();
        }
        else
        {

            ProviderAgentServer providerAgentServer = new ProviderAgentServer().init(Integer.parseInt(System.getProperty("server.port")));

            providerAgentServer.listen();

        }
    }


}
package com.alibaba.dubbo.performance.demo.agent.net;

import com.alibaba.dubbo.performance.demo.agent.dubbo.RpcClient;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;

public class ProviderRunnable implements Runnable{

    private SelectionKey key = null;

    private Selector selector = null;

    private RpcClient rpcClient = null;

    private Lock lock = null;

    Iterator ite = null;

    public ProviderRunnable(SelectionKey key, RpcClient rpcClient,Lock lock){

        this.key = key;

        this.rpcClient = rpcClient;
        this.lock = lock;

    }



    @Override
    public void run() {







                SocketChannel channel = (SocketChannel) key.channel();
                com.alibaba.dubbo.performance.demo.agent.dubbo.RpcClientHandler.channel = channel;

                ByteBuffer buffLen = ByteBuffer.allocate(2);
                ByteBuffer buffer = null;
                try {
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

                buffer.flip();
                buffer.get(len);

                int len1 = len[1];
                int len2 = len[0];
                int parameterLen = len1 * 127 + len2;

                byte[] parameter = new byte[parameterLen];
                buffer.get(parameter);
                int hashCodeLen = buffer.get();
                byte[] hashcode = new byte[hashCodeLen];
                buffer.get(hashcode);

                String message = new String(parameter);

                message = message.trim();



                try {
                    this.rpcClient.invoke("com.alibaba.dubbo.performance.demo.provider.IHelloService", "hash", "Ljava/lang/String;", message, hashcode);
                } catch (Exception e) {
                    key.cancel();
                }

            }
            }

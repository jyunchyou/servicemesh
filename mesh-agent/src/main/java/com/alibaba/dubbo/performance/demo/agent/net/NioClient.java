package com.alibaba.dubbo.performance.demo.agent.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

class NioClient
{
    private Selector selector;

    NioClient()
    {
        try
        {
            this.selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SocketChannel initClient(String ip, int port)
            throws IOException
    {
        SocketChannel channel = SocketChannel.open();

        channel.configureBlocking(false);

        channel.connect(new InetSocketAddress(ip, port));

        channel.register(this.selector, 8);
        return channel;
    }

    public void ConnectionAll()
            throws IOException
    {
        SocketChannel channel = null;

        this.selector.select();
        Iterator ite = this.selector.selectedKeys().iterator();
        while (ite.hasNext()) {
            SelectionKey key = (SelectionKey)ite.next();

            ite.remove();
            if (key.isConnectable()) {
                channel = (SocketChannel)key.channel();

                if (channel.isConnectionPending()) {
                    channel.finishConnect();
                }

                channel.configureBlocking(false);
            }
        }
    }

    public void listen()
            throws IOException
    {
        this.selector.select();

        Iterator ite = this.selector.selectedKeys().iterator();
        while (ite.hasNext()) {
            SelectionKey key = (SelectionKey)ite.next();

            ite.remove();

            if (key.isConnectable())
            {
                SocketChannel channel = (SocketChannel)key.channel
                        ();

                if (key.isReadable())
                    read(key);
            }
        }
    }

    public void read(SelectionKey key)
            throws IOException
    {
    }

    public static void main(String[] args)
            throws IOException
    {
        NioClient client = new NioClient();
        client.initClient("localhost", 8000);
        client.listen();
    }
}
package com.alibaba.dubbo.performance.demo.agent.net;

import com.alibaba.dubbo.performance.demo.agent.constant.ConstantAgent;
import com.alibaba.dubbo.performance.demo.agent.handler.NettyServerHandlerAdapter;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by fbhw on 18-5-27.
 */
public class NettyServer implements NettyBase{

    Logger logger = LoggerFactory.getLogger("NettyServer");

    private EventLoopGroup work = new NioEventLoopGroup();

    private EventLoopGroup boss = new NioEventLoopGroup();



    public void bind(int port){
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss,work);

        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.TCP_NODELAY,true);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE,true);
        bootstrap.childHandler(new NewServerChannelInitializer(ConstantAgent.MAX_FRAME_LENGTH,ConstantAgent.LENGTH_FIELD_LENGTH,ConstantAgent.LENGTH_FIELD_OFFSET,ConstantAgent.LENGTH_ADJUSTMENT,ConstantAgent.INITIAL_BYTES_TO_STRIP));


        ChannelFuture channelFuture = null;


            channelFuture = bootstrap.bind(port);

        if (channelFuture.isSuccess()) {
            logger.info("bind server port success");
        }else{
            logger.info("bind server port fail");
        }


    }

    @Override
    public Channel start(String address, int port) {
        return null;
    }



//    public void bindPullPort(int port){
//
//
//        ServerBootstrap bootstrap = new ServerBootstrap();
//        bootstrap.group(boss,work);
//
//        bootstrap.channel(NioServerSocketChannel.class);
//        bootstrap.option(ChannelOption.TCP_NODELAY,true);
//        bootstrap.childOption(ChannelOption.SO_KEEPALIVE,true);
//        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
//            @Override
//            protected void initChannel(SocketChannel socketChannel) throws Exception {
//                socketChannel.pipeline().addLast(new PullHandlerAdapter());
//            }
//        });
//        ChannelFuture channelFuture = null;
//
//        try {
//            channelFuture = bootstrap.bind(port).sync();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            work.shutdownGracefully();
//            boss.shutdownGracefully();
//        }
//        if (channelFuture.isSuccess()) {
//            logger.info("bind consumer port success");
//        }
//
//    }
//
//
//    public Channel bind(NameServerInfo nameServerInfo){
//        Bootstrap bootstrap = new Bootstrap();
//        bootstrap.group(work);
//
//        bootstrap.channel(NioSocketChannel.class);
//        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
//        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
//            @Override
//            protected void initChannel(SocketChannel socketChannel) throws Exception {
//                socketChannel.pipeline().addLast(new RestartHandlerAdapter());
//            }
//        });
//        ChannelFuture channelFuture = null;
//
//        try {
//            channelFuture = bootstrap.connect(nameServerInfo.getIp(),nameServerInfo.getPort()).sync();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            work.shutdownGracefully();
//        }
//        if (channelFuture.isSuccess()) {
//
//
//        }
//       return  channelFuture.channel();
//
//
//    }
//
//
//    public void sendTableToNameServer(NameServerInfo nameServerInfo){
//
//        Channel channel = nameServerConnectionCacheTable.get(nameServerInfo);
//        if (channel == null) {
//
//            channel = bind(nameServerInfo);
//            nameServerConnectionCacheTable.put(nameServerInfo,channel);
//
//        }
//
//
////            System.out.println("aaaa"+new String(routeByteBuffer.array()));
//
//
//
//        BrokerInfo brokerInfo = new BrokerInfo();
//        brokerInfo.setIp(ConstantBroker.BROKER_MESSAGE_IP);
//        brokerInfo.setNameServerPort(ConstantBroker.NAMESERVER_PORT);
//        brokerInfo.setProducerPort(ConstantBroker.BROKER_MESSAGE_PORT);
//        brokerInfo.setConsumerPort(ConstantBroker.PULL_PORT);
//        ByteBuf byteBuf = encodeAndDecode.encodeToNameServer(brokerInfo);
//
//        channel.writeAndFlush(byteBuf);
//
//
//
//
//
//    }
//
//    public void bindNameServerPort(int port){
//
//
//        ServerBootstrap bootstrap = new ServerBootstrap();
//        bootstrap.group(boss,work);
//
//        bootstrap.channel(NioServerSocketChannel.class);
//        bootstrap.option(ChannelOption.TCP_NODELAY,true);
//        bootstrap.childOption(ChannelOption.SO_KEEPALIVE,true);
//        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
//            @Override
//            protected void initChannel(SocketChannel socketChannel) throws Exception {
//                socketChannel.pipeline().addLast(new UpdateTopicHandlerAdapter());
//            }
//        });
//        ChannelFuture channelFuture = null;
//
//        try {
//            channelFuture = bootstrap.bind(port).sync();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            work.shutdownGracefully();
//            boss.shutdownGracefully();
//        }
//        if (channelFuture.isSuccess()) {
//
//            logger.info("bind nameserver port success");
//
//        }
//
//
//    }
//


}

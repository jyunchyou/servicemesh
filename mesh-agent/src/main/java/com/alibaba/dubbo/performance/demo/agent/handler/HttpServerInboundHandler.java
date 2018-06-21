package com.alibaba.dubbo.performance.demo.agent.handler;



import com.alibaba.dubbo.performance.demo.agent.net.NettyClient;
import io.netty.channel.*;
import okhttp3.OkHttpClient;

import java.util.*;

public class HttpServerInboundHandler extends ChannelInboundHandlerAdapter{





    private Random random = new Random();



    private Object lock = new Object();
    private OkHttpClient httpClient = new OkHttpClient();
    private List<NettyClient> nettyClients = null;


    private int index = 0;



    boolean test = false;
    List<ChannelHandlerContext> list = new Vector();



    public HttpServerInboundHandler() throws Exception {


       /* if (null == EtcdRegistry.endpoints) {
            synchronized (lock) {

                if (null == EtcdRegistry.endpoints) {
                    EtcdRegistry.endpoints = EtcdRegistry.etcdRegistry.find("nettyPort");
                    List dus = EtcdRegistry.etcdRegistry.find("com.alibaba.dubbo.performance.demo.provider.IHelloService");


                }

                //TODO 负载均衡
                if (null == EtcdRegistry.channels) {
                    EtcdRegistry.channels = new ArrayList<>();
                    NettyClient nettyClient = new NettyClient();
                    for (Endpoint endpoint : EtcdRegistry.endpoints) {


                        Channel channel = nettyClient.start(endpoint.getHost(), endpoint.getPort());
                        EtcdRegistry.channels.add(channel);

                    }}}}}*/
    /*//dubbo协议
    public byte[] provider(String interfaceName,String method,String parameterTypesString,String parameter) throws Exception {


        Object result = EtcdRegistry.rpcClient.invoke(interfaceName,method,parameterTypesString,parameter);
        //远程调用provider service


        System.out.println(result);
        return (byte[]) result;
    }*/
        //tcp协议 自定义序列化格式
    }
  /*
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {


        HttpContent httpContent = (HttpContent) msg;

        ByteBuf content = httpContent.content();

        byte bu = '=';
        int index = content.indexOf(0, content.readableBytes(), bu);
        int index2 = content.indexOf(index + 1, content.readableBytes(), bu);
        int index3 = content.indexOf(index2 + 1, content.readableBytes(), bu);
        int index4 = content.indexOf(index3 + 1, content.readableBytes(), bu);


        String type = System.getProperty("type");   // 获取type参数
        String interfaceName = "1";
        String parameterTypesString = "1";
        String method = "1";

        content.markReaderIndex();

        ByteBuf byteBuf = Unpooled.buffer(content.readableBytes() - index4 + 3);
        byteBuf.writeInt(content.readableBytes() - index4 - 1);
        content.resetReaderIndex();
        content.skipBytes(index4 + 1);


        content.readBytes(byteBuf);



       if ("consumer".equals(type)) {

            byteBuf.markReaderIndex();
            byte[] paByte = new byte[byteBuf.readableBytes() - 4];
            byteBuf.skipBytes(4);
            byteBuf.readBytes(paByte);

            byteBuf.resetReaderIndex();
            String hashString = new String(String.valueOf(paByte.hashCode()));



            List list = (List) NioServer.concurrentHashMap.get(hashString);
            if (list == null) {
                List ctxList = new Vector();
                ctxList.add(ctx);
                NioServer.concurrentHashMap.put(hashString, ctxList);


            } else {
                list.add(ctx);

            }


            consumer("1", "2", "3", byteBuf);


        }

*/




           /* if (HttpHeaders.isKeepAlive(request)) {
                response.headers().set(CONNECTION, Values.KEEP_ALIVE);
            }*/




           /* ctx.write(response);
            ctx.flush();*/


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        ctx.close();
    }

}
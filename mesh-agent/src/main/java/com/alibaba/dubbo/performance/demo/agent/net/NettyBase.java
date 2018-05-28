package com.alibaba.dubbo.performance.demo.agent.net;


import io.netty.channel.Channel;

public interface NettyBase {

    void bind(int port);

    Channel start(String address,int port);

}

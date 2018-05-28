package com.alibaba.dubbo.performance.demo.agent.registry;

import com.alibaba.dubbo.performance.demo.agent.net.NettyBase;
import com.alibaba.dubbo.performance.demo.agent.net.NettyClient;
import com.alibaba.dubbo.performance.demo.agent.net.NettyServer;

//启动参数 -Dtype=provider -Dserver.port=30000 -Ddubbo.protocol.port=20889 -Detcd.url=http://127.0.0.1:2379
//-Dtype=consumer -Dserver.port=20000 -Detcd.url=http://127.0.0.1:2379
public class AgentSimple {
    private String type = System.getProperty("type");

    private int dubboProtocolPort = Integer.parseInt(System.getProperty("netty.port"));

    private String etcdUrl = System.getProperty("etcd.url");

    private NettyBase nettyBase = null;



    public AgentSimple(String registryAddress){
        if ("provider".equals(type)) {




        } else {

        }

    }
}

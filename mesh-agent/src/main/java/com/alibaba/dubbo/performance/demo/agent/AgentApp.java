package com.alibaba.dubbo.performance.demo.agent;

import com.alibaba.dubbo.performance.demo.agent.registry.EtcdRegistry;
import com.alibaba.dubbo.performance.demo.agent.registry.IRegistry;
import com.alibaba.dubbo.performance.demo.agent.registry.LoopServerName;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * 启动 etcd 实例
 启动三个 Provider 实例，Provider Agent 将 Provider 服务信息写入 etcd 注册中心
 启动 Consumer 实例，Consumer Agent 从注册中心中读取 Provider 信息
 客户端访问 Consumer 服务
 Consumer 服务将访问请求 JSON 序列化后，通过 HTTP 协议调用 Consumer Agent
 Consumer Agent 根据当前的负载情况决定调用哪个 Provider Agent，并使用自定义协议将请求发送给选中的 Provider Agent
 Provider Agent 收到请求后，将通讯协议转换为 DUBBO，然后调用 Provider 服务
 Provider 服务将处理后的请求返回给 Agent
 Provider Agent 收到请求后解析 DUBBO 协议，并将数据取出，以自定义协议返回给 Consumer Agent
 Consumer Agent 收到请求后解析出结果，再通过 HTTP 协议返回给 Consumer 服务
 Consumer 服务最后将结果返回给客户端
 */
@SpringBootApplication
public class AgentApp {
    // agent会作为sidecar，部署在每一个Provider和Consumer机器上
    // 在Provider端启动agent时，添加JVM参数-Dtype=provider -Dserver.port=30000 -Ddubbo.protocol.port=20889
    // 在Consumer端启动agent时，添加JVM参数-Dtype=consumer -Dserver.port=20000
    // 添加日志保存目录: -Dlogs.dir=/path/to/your/logs/dir。请安装自己的环境来设置日志目录。

    public static void main(String[] args) {
         SpringApplication.run(AgentApp.class,args);





    }
}

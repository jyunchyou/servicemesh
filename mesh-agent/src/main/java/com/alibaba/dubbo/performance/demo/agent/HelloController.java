package com.alibaba.dubbo.performance.demo.agent;

import com.alibaba.dubbo.performance.demo.agent.code.EncodeAndDecode;
import com.alibaba.dubbo.performance.demo.agent.code.Message;
import com.alibaba.dubbo.performance.demo.agent.code.NewEncoder;
import com.alibaba.dubbo.performance.demo.agent.dubbo.RpcClient;
import com.alibaba.dubbo.performance.demo.agent.net.NettyClient;
import com.alibaba.dubbo.performance.demo.agent.registry.AgentSimple;
import com.alibaba.dubbo.performance.demo.agent.registry.Endpoint;
import com.alibaba.dubbo.performance.demo.agent.registry.EtcdRegistry;
import com.alibaba.dubbo.performance.demo.agent.registry.IRegistry;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RestController
public class HelloController {

    private Logger logger = LoggerFactory.getLogger(HelloController.class);
    
    private IRegistry registry = new EtcdRegistry(System.getProperty("etcd.url"));//向etcd注册服务

    private RpcClient rpcClient = new RpcClient(registry);
    private Random random = new Random();
    private List<Endpoint> endpoints = null;
    private Object lock = new Object();
    private OkHttpClient httpClient = new OkHttpClient();
    private List<NettyClient> nettyClients = null;
    private List<Channel> channels;
    private EncodeAndDecode encodeAndDecode = new EncodeAndDecode();
    private NewEncoder newEncoder = new NewEncoder();
    private AgentSimple agent = new AgentSimple(System.getProperty("etcd.url"));



    @RequestMapping(value = "")
    public Object invoke(@RequestParam("interface") String interfaceName,
                         @RequestParam("method") String method,
                         @RequestParam("parameterTypesString") String parameterTypesString,
                         @RequestParam("parameter") String parameter) throws Exception {
        String type = System.getProperty("type");   // 获取type参数
        System.out.println(type);
        System.out.println("invoke");
        if ("consumer".equals(type)){
            return consumer(interfaceName,method,parameterTypesString,parameter);
        }
        else if ("provider".equals(type)){
            return provider(interfaceName,method,parameterTypesString,parameter);
        }else {
            return "Environment variable type is needed to set to provider or consumer.";
        }
    }

    //dubbo协议
    public byte[] provider(String interfaceName,String method,String parameterTypesString,String parameter) throws Exception {


        Object result = rpcClient.invoke(interfaceName,method,parameterTypesString,parameter);
        //远程调用provider service


        System.out.println(result);
        return (byte[]) result;
    }
    //tcp协议 自定义序列化格式
    public Integer consumer(String interfaceName,String method,String parameterTypesString,String parameter) throws Exception {

        if (null == endpoints) {
            synchronized (lock) {
                if (null == endpoints) {
                    endpoints = registry.find("nettyPort");
                }
                System.out.println(endpoints);
                //TODO 负载均衡
                if (null == channels){
                    channels = new ArrayList<>();
                    NettyClient nettyClient = new NettyClient();
                    for (Endpoint endpoint : endpoints) {
                        System.out.println("-----------");


                        Channel channel = nettyClient.start(endpoint.getHost(),endpoint.getPort());
                        channels.add(channel);

                    }

                }
            }
        }

        System.out.println(channels.size());

        // 简单的负载均衡，随机取一个
        int check = random.nextInt(channels.size());

        Channel channel = channels.get(check);

        // netty请求
        Message message = encodeAndDecode.encode(interfaceName,method,parameterTypesString,parameter);



        channel.write(message);





        //返回hash
        //byte[] bytes = response.body().bytes();
        String s = "123";
        return Integer.valueOf(s);
    }
}

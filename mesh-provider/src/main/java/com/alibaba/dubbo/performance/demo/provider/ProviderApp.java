package com.alibaba.dubbo.performance.demo.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@SpringBootApplication
public class ProviderApp {
    // 启动时请添加JVM参数:
    // -Dlogs.dir=/path/to/your/logs/dir。日志保存目录,请安装自己的环境来设置日志目录。
    // -Ddubbo.protocol.port=20889。Dubbo协议的端口:。
    // -Ddubbo.application.qos.enable=false。关闭dubbo的qos服务。不然qos服务会占用22222端口，如果一台机器部署多个dubbo服务，会出现端口冲突。

    public static void main(String[] args) {
        SpringApplication.run(ProviderApp.class,args);

        // 不让应用在docker中退出
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> System.out.println("do something..."),1000,5, TimeUnit.SECONDS);
    }
}

package com.alibaba.dubbo.performance.demo.agent.registry;


import java.util.Random;

public class LoopServerName {



    private static Random random = new Random(1);

    public static String createServerName(){
        return "service_" + random.nextInt();

    }
}

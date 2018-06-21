package com.alibaba.dubbo.performance.demo.agent.dubbo.model;

import java.util.concurrent.ConcurrentHashMap;

public class RpcRequestHolder {

    // key: requestId     value: RpcFuture
    private static ConcurrentHashMap<String,byte[]> processingRpc = new ConcurrentHashMap<>();

    public static void put(String requestId,byte[] rpcFuture){
        processingRpc.put(requestId,rpcFuture);
    }

    public static byte[] get(String requestId){
        return processingRpc.get(requestId);
    }

    public static void remove(String requestId){
        processingRpc.remove(requestId);
    }
}

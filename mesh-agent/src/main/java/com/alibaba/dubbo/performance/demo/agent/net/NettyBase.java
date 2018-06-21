package com.alibaba.dubbo.performance.demo.agent.net;

import io.netty.channel.Channel;
import java.util.concurrent.locks.Lock;

public abstract interface NettyBase
{
    public abstract void bind(int paramInt);

    public abstract Channel start(String paramString, int paramInt, Lock paramLock);
}
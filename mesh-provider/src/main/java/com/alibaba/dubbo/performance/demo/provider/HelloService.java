package com.alibaba.dubbo.performance.demo.provider;


import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;


public class HelloService implements IHelloService {

    private long count;

    private Logger logger = LoggerFactory.getLogger(HelloService.class);

    public HelloService() {

    }

    @Override
    public int hash(String str) throws Exception {

        System.out.println(str);
        int hashCode = str.hashCode();
        logger.info(++count + "_" + hashCode);
        sleep(50);

        return hashCode;
    }


    private void sleep(long duration) throws Exception {
        Thread.sleep(duration);
    }
}

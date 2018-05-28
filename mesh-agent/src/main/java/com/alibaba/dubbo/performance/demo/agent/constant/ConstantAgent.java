package com.alibaba.dubbo.performance.demo.agent.constant;

public class ConstantAgent {


    public static final int MAX_FRAME_LENGTH = 1024 * 1024;//最大长度
    public static final int LENGTH_FIELD_LENGTH = 2;//消息长的长度
    public static final int LENGTH_FIELD_OFFSET = 0;//消息长的偏移
    public static final int LENGTH_ADJUSTMENT = 0;//消息体修正长度
    public static final int INITIAL_BYTES_TO_STRIP = 0;//跳过字节数，如我们想跳过长度属性部分
}

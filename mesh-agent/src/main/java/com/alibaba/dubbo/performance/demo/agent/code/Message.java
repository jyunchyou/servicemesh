package com.alibaba.dubbo.performance.demo.agent.code;

public class Message {

    //消息长度
    private int length;

    //消息体
    private byte[] msgBody;

    public Message(int length, byte[] msgBody) {

        this.length = length;
        this.msgBody = msgBody;
    }


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(byte[] msgBody) {
        this.msgBody = msgBody;
    }
}

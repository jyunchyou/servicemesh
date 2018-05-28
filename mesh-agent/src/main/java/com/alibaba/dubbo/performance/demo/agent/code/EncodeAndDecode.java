package com.alibaba.dubbo.performance.demo.agent.code;

import java.util.Arrays;
import java.util.Collection;

public class EncodeAndDecode {


    //长度+字节
    public Message encode(String interfaceName,String method,String parameterTypesString,String parameter){


        byte[] interfaceNameByte = interfaceName.getBytes();
        byte[] interfaceNameByteLen = new byte[1];
        interfaceNameByteLen[0] = (byte) interfaceNameByte.length;

        byte[] methodByte = method.getBytes();
        byte[] methodByteLen = new byte[1];
        methodByteLen[0] = (byte) methodByte.length;

        byte[] parameterTypeStringByte = parameterTypesString.getBytes();
        byte[] parameterTypeStringByteLen = new byte[1];
        parameterTypeStringByteLen[0] = (byte) parameterTypeStringByte.length;


        byte[] parameterByte = parameter.getBytes();
        byte[] parameterByteLen = new byte[1];
        parameterByteLen[0] = (byte) parameterByte.length;

        int bodyLength =  interfaceNameByte.length + methodByte.length + parameterTypeStringByte.length + parameterByte.length +
                interfaceNameByteLen.length + methodByteLen.length + parameterByteLen.length + parameterByteLen.length;


        byte[] bodyByte = new byte[bodyLength];
        int copyIndex = 0;
        System.arraycopy(interfaceNameByteLen,0,bodyByte,copyIndex,interfaceNameByteLen.length);
        copyIndex = copyIndex + interfaceNameByteLen.length;
        System.arraycopy(interfaceNameByte,0,bodyByte,copyIndex,interfaceNameByte.length);
        copyIndex = interfaceNameByte.length + copyIndex;
        System.arraycopy(methodByteLen,0,bodyByte,copyIndex,methodByteLen.length);
        copyIndex = methodByteLen.length + copyIndex;
        System.arraycopy(methodByte,0,bodyByte,copyIndex,methodByte.length);
        copyIndex = copyIndex + methodByte.length;
        System.arraycopy(parameterTypeStringByteLen,0,bodyByte,copyIndex,parameterTypeStringByteLen.length);
        copyIndex = copyIndex + parameterTypeStringByteLen.length;
        System.arraycopy(parameterTypeStringByte,0,bodyByte,copyIndex,parameterTypeStringByte.length);
        copyIndex = copyIndex + parameterTypeStringByte.length;
        System.arraycopy(parameterByteLen,0,bodyByte,copyIndex,parameterByteLen.length);
        copyIndex = copyIndex + parameterByteLen.length;
        System.arraycopy(parameterByte,0,bodyByte,copyIndex,parameterByte.length);



        return new Message(bodyLength,bodyByte);



    }





    public static void main(String[] args) {
        EncodeAndDecode encodeAndDecode = new EncodeAndDecode();
        Message message = encodeAndDecode.encode("test", "invoked", "String", "hi");
        System.out.println(new String(message.getMsgBody()));

    }
}

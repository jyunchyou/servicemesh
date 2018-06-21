package com.alibaba.dubbo.performance.demo.agent.dubbo;

import com.alibaba.dubbo.performance.demo.agent.dubbo.model.Bytes;
import com.alibaba.dubbo.performance.demo.agent.dubbo.model.RpcFuture;
import com.alibaba.dubbo.performance.demo.agent.dubbo.model.RpcRequestHolder;
import com.alibaba.dubbo.performance.demo.agent.dubbo.model.RpcResponse;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import javax.xml.ws.soap.AddressingFeature;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RpcClientHandler extends SimpleChannelInboundHandler <ByteBuf> {

    public static SocketChannel channel = null;

    protected static final int HEADER_LENGTH = 16;

    private byte[] buffer = null;


    public List decode(ByteBuf byteBuf){
        byte[] byteArray = null;
        int allLen = byteBuf.readableBytes();


        if (buffer != null) {

            byteArray = new byte[allLen + buffer.length];
            System.arraycopy(buffer,0,byteArray,0,buffer.length);
            byte[] temp = new byte[allLen];
            byteBuf.readBytes(temp);
            System.arraycopy(temp,0,byteArray,buffer.length,temp.length);


            buffer = null;
        } else {
            byteArray = new byte[allLen];
            byteBuf.readBytes(byteArray);
        }
        int bodyLen = 0;


        int flag = 0;
        List<RpcResponse> list = null;

        if (byteArray.length - flag <= 16) {

            byte[] remainBuffer = Arrays.copyOfRange(byteArray,flag,byteArray.length);

            buffer = remainBuffer;
            return null;


        }

        while (byteArray.length - flag > 16) {






            byte[] requestIdBytes = Arrays.copyOfRange(byteArray,4 + flag,12 + flag);
            long requestId = Bytes.bytes2long(requestIdBytes,0);





            byte[] bodyLenBytes = Arrays.copyOfRange(byteArray,12 + flag,16 + flag);


            bodyLen =
                    ((bodyLenBytes[ 3] & 0xFF) << 0) +
                            ((bodyLenBytes[2] & 0xFF) << 8) +
                            ((bodyLenBytes[1] & 0xFF) << 16) +
                            ((bodyLenBytes[0]) << 24);


            if (byteArray.length - flag> 16 + bodyLen) {

                RpcResponse response = new RpcResponse();

                response.setRequestId(String.valueOf(requestId));



                byte[] body = Arrays.copyOfRange(byteArray, 16 + flag + 2, (int) bodyLen + 16 + flag);


                response.setBytes(body);

                if (list == null) {

                    list = new ArrayList();
                }

                list.add(response);

                buffer = null;
                if (bodyLen != 0) {
                    flag = (int) ((int) flag + HEADER_LENGTH + bodyLen);
                }






            } else if (byteArray.length - flag == 16 + bodyLen) {




                RpcResponse response = new RpcResponse();

                response.setRequestId(String.valueOf(requestId));


                byte[] body = Arrays.copyOfRange(byteArray, 16 + flag + 2, (int) bodyLen + flag + 16);


                response.setBytes(body);


                if (list == null) {

                    list = new ArrayList<>(1);
                }



                list.add(response);

                buffer = null;
                if (bodyLen != 0) {
                    flag = (int) ((int) flag + HEADER_LENGTH + bodyLen);
                }


                return list;


            } else {





                byte[] remainBuffer = Arrays.copyOfRange(byteArray,flag,byteArray.length);

                buffer = remainBuffer;
                if (bodyLen != 0) {
                    flag = (int) ((int) flag + HEADER_LENGTH + bodyLen);
                }
                return list;
            }


        }



        byte[] remainBuffer = Arrays.copyOfRange(byteArray,flag,byteArray.length);

        buffer = remainBuffer;




        return list;


    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {

/*
        String requestId = response.getRequestId();
        RpcFuture future = RpcRequestHolder.get(requestId);
        if(null != future){
            RpcRequestHolder.remove(requestId);
            future.done(response);
        }

*/


        List<RpcResponse> responses = decode(byteBuf);
        if (responses == null) {
            return;

        }



        for (RpcResponse response : responses) {

            String requestId = response.getRequestId();
            byte[] hashcode = RpcRequestHolder.get(requestId);
            byte[] result = response.getBytes();
            ByteBuffer byteBuffer = ByteBuffer.allocate(result.length + 3 + hashcode.length);


            byteBuffer.put((byte) (result.length + 2 + hashcode.length));
            byteBuffer.put((byte) result.length);
            byteBuffer.put(result);
            byteBuffer.put((byte) hashcode.length);
            byteBuffer.put(hashcode);


            byteBuffer.flip();


            try {


                channel.write(byteBuffer);



            } catch (IOException e) {
                e.printStackTrace();
            }

        }
                    }
                    }




package com.bat;

import com.alibaba.fastjson.JSON;
import com.bat.entity.Root;

import java.io.IOException;
import java.net.*;

public class UDPServer {
    public static void main(String[] args) throws Exception {
        System.out.println("===Server===");
        DatagramSocket serverSocket = new DatagramSocket(9876, InetAddress.getByName("192.168.4.251"));
        byte[] sendData = {};
        while (true) {
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            //获得字节输入流
            byte[] data = receivePacket.getData();
            String sentence = new String(receivePacket.getData());
            System.out.println("客户端传参sentence----"+sentence);
            if (sentence.contains("Json=")) {
                DecryptJson decryptJson = new DecryptJson();
                 sentence = decryptJson.decrypt(data);
                System.out.println("时间："+ System.currentTimeMillis() + "  and json明文=" + sentence);
                try {
                    Root root = JSON.parseObject(sentence, Root.class);
                    //补充root入库逻辑
                    System.out.println("Received:" + sentence);
                    sendData = reback("1");
                } catch (Exception e) {
                    sendData = reback("FormatJSONError");
                }
                send(serverSocket, sendData, 1030);
                continue;
            }
            //查询HandleId逻辑
            if (!"".equals(sentence)) {
                if (sentence.contains("addrLen=") && sentence.contains("addr=")) {
                    sendData = reback("86.5000.12/iPole.1111222233334444");
                } else {
                    sendData = reback("InputError");
                }
            } else {
                sendData = reback("NullStrError");
            }
            send(serverSocket, sendData, 1030);
        }
    }
    private static byte[] reback(String returnMsg) {
        System.out.println("服务端发送----" + returnMsg);
        return returnMsg.getBytes();
    }

    private static void send(DatagramSocket serverSocket, byte[] sendData, int port) throws IOException {
        serverSocket.send(new DatagramPacket(sendData, sendData.length, InetAddress.getByName("192.168.3.72"), port));
    }
}
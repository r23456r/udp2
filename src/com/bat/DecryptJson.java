package com.bat;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2019/12/5 10:50
 **/
public class DecryptJson {
    String decrypt(byte[] bytes) {

        final int S_KEY_LEN = 16;
        final String ID = "ID=";
        final String ID_LEN = "IDLen=";
        final String JSON_LEN = "JsonLen=";
        final String JSON = "Json=";
        //完整的udp入参
        String wholeStr = new String(bytes);

        int index1 = wholeStr.indexOf(ID);
        int index2 = wholeStr.indexOf(JSON_LEN);
        int index3 = wholeStr.indexOf(JSON);
        int seat;
        byte[] s_key = new byte[S_KEY_LEN];

        System.out.println("收到的客户端字符串：" + wholeStr);
        String handleId = wholeStr.substring(index1 + ID.length(), index2);
        byte[] handleIdBytes = handleId.getBytes();
        int handleIdLen = handleId.length();
        int dataLen = Integer.parseInt(wholeStr.substring(index2 + JSON_LEN.length(), index3));
        String miJson = wholeStr.substring(index3 + JSON.length(), index3 + JSON.length() + dataLen);
        System.out.println("密文json--" + miJson);
        byte[] bytes1 = miJson.getBytes();

       //根据HandleId派生s_key
        for (int i = 0; i < handleIdLen; i++) {
            seat = i % S_KEY_LEN;
            s_key[seat] ^= handleIdBytes[i];
        }
        for (int i = 0; i < dataLen; i++) {
            seat = i % S_KEY_LEN;
            bytes1[i] ^= s_key[seat];
        }
        return new String(bytes1);
    }
}

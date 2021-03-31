package com.bat;

import java.util.Arrays;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2020/11/18 9:22
 **/
public class ByteUtilss {
    public static byte[] toByteArray(int i) {
        byte[] byteArray = new byte[4];
        byteArray[0] = (byte) (i >>> 24);
        byteArray[1] = (byte) ((i & 0xFFFFFF) >>> 16);
        byteArray[2] = (byte) ((i & 0xFFFF) >>> 8);
        byteArray[3] = (byte) (i & 0xFF);
        return byteArray;
    }
    public static byte[] toLH(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }
    public static void main(String[] args) {
        byte[] bytes = ByteUtilss.toByteArray(1);
        System.out.println(Arrays.toString(bytes));
        System.out.println(Arrays.toString(ByteUtilss.toLH(1)));
    }
}

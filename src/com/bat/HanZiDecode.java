package com.bat;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2020/11/23 15:51
 **/
public class HanZiDecode {
    private static final Base64.Decoder DECODER = Base64.getDecoder();
    private static final String IGNORE_STR = "+/=";
    private static final String KEY = "boch";

    public static String decode(String str) {
        System.out.println("密文：  " + str);
        System.out.println("密钥： " + KEY);
        byte[] ciperBytes = str.getBytes();
        byte[] keyBytes = KEY.getBytes();
        byte[] result = new byte[ciperBytes.length];
        for (int i = 0; i < ciperBytes.length; i++) {

            //如果是 = + \，原样输出
            byte ciperByte = ciperBytes[i];
            String ciperChar = String.valueOf(ciperByte);
            if (IGNORE_STR.contains(ciperChar)) {
                result[i] = ciperByte;
                continue;
            }
            //解密
            byte i1;
            //解密前是大写字母
            if (highCase(ciperByte)) {
                i1 = (byte) (ciperBytes[i] - keyBytes[i % 4] % 26);
                //解密后依然是大写字母，写回
                if (i1 >= 'A') {
                    ciperByte = i1;
                } else {
                    //取获得新的大写字母
                    ciperByte = (byte) (i1 + 26);
                }
            }
            if (lowCase(ciperByte)) {
                i1 = (byte) (ciperBytes[i] - keyBytes[i % 4] % 26);
                if (i1 >= 'a') {
                    ciperByte = i1;
                } else {
                    ciperByte = (byte) (i1 + 26);
                }
            }
            if (numCase(ciperByte)) {
                i1 = (byte) ((ciperBytes[i] - keyBytes[i % 4] % 10));
                if (i1 >= '0') {
                    ciperByte = i1;
                } else {
                    ciperByte = (byte) (i1 + 10);
                }
            }
            result[i] = ciperByte;
        }
        System.out.println("解密后HexResult:  " + byteArrToHexString(result));

        System.out.println("解密后StringResult:  " + new String(DECODER.decode(hexStringToBytes(byteArrToHexString(result)))));
        return byteArrToHexString(result);
    }

    private static Boolean lowCase(Byte b) {
        return b >= 'a' && b <= 'z';
    }

    private static Boolean highCase(Byte b) {
        return b >= 'A' && b <= 'Z';
    }

    private static Boolean numCase(Byte b) {
        return b >= '0' && b <= '9';
    }

    private static String byteArrToHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    private static String toHexString1(byte[] b) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; ++i) {
            buffer.append(toHexString1(b[i]));
        }
        return buffer.toString();
    }

    private static String toHexString1(byte b) {
        String s = Integer.toHexString(b & 0xFF);
        if (s.length() == 1) {
            return "0" + s;
        } else {
            return s;
        }
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString.length() % 2 != 0) {
            return null;
        }
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] bytes = new byte[length];
        for (int i = 0, j = 0; j < length; i++, j++) {
            String step = "" + hexChars[i++] + hexChars[i];
            int k = Integer.parseInt(step, 16);
            bytes[j] = new Integer(k).byteValue();
        }
        return bytes;
    }
}

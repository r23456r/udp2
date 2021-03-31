package com.bat;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2020/11/23 15:51
 **/
public class HanZiEncode {
    private static final Base64.Encoder ENCODER = Base64.getEncoder();
    private static final String IGNORE_STR = "+/=";
    private static final String KEY = "boch";

    public static String encode(String str) {

        System.out.println("明文汉字：  " + str);
        System.out.println("密钥： " + KEY);
        byte[] utf8Bytes = str.getBytes(StandardCharsets.UTF_8);
        byte[] keyBytes = KEY.getBytes();
        byte[] ciperBytes = ENCODER.encode(utf8Bytes);
        System.out.println("base64后String： " + new String(ciperBytes));
        System.out.println("base64后Hex： " + toHexString1(ciperBytes));
        byte[] result = new byte[ciperBytes.length];
        for (int i = 0; i < ciperBytes.length; i++) {

            //如果是 = + \，原样输出
            byte ciperByte = ciperBytes[i];
            String ciperChar = String.valueOf(ciperByte);
            if (IGNORE_STR.contains(ciperChar)) {
                result[i] = ciperByte;
                continue;
            }
            //加密
            byte i1;

            //加密前是大写字母
            if (highCase(ciperByte)) {
                i1 = (byte) ((ciperBytes[i] + keyBytes[i % 4] % 26));
                //加密后依然是大写字母，写回
                if (highCase(i1)) {
                    ciperByte = i1;
                } else {
                    //取获得新的大写字母
                    ciperByte = (byte) (i1 - 26);
                }
            }
            if (lowCase(ciperByte)) {
                i1 = (byte) ((ciperBytes[i] + keyBytes[i % 4] % 26));
                if (lowCase(i1)) {
                    ciperByte = i1;
                } else {
                    ciperByte = (byte) (i1 - 26);
                }
            }
            if (numCase(ciperByte)) {
                i1 = (byte) ((ciperBytes[i] + keyBytes[i % 4] % 10));
                if (numCase(i1)) {
                    ciperByte = i1;
                } else {
                    ciperByte = (byte) (i1 - 10);
                }
            }
            result[i] = ciperByte;
        }
//        System.out.println("加密后HexResult:  " + byteArrToHexString(result));

        System.out.println("加密后StringResult:  " + new String(result));
        return new String(result);
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
}

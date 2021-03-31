//package com.bat;
//
//import cn.hutool.core.lang.Assert;
//
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//
///**
// * @author: zhangyuhang
// * @modified By：
// * @date ：Created in 2020/11/23 15:51
// **/
//public class HanZiDecrypt {
//    public static void main(String[] args) {
//
//        String ignoreStr = "+/=";
//
//        String key = "boch";
//        final Base64.Decoder encoder = Base64.getDecoder();
//        String cipher = "3146776d3146776d";
//        System.out.println("输入--Hex：  " + cipher);
//        System.out.println("密钥： " + key);
//        byte[] keyBytes = key.getBytes();
//        byte[] bytes = ByteUtil.hexStringToBytes(cipher);
//        assert bytes != null;
//        for (int i = 0; i < bytes.length; i++) {
//            byte aByte = bytes[i];
//            if (numCase(aByte)) {
//                aByte - keyBytes[i % 4];
//                if (numCase(i1)) {
//                    ciperByte = i1;
//                } else {
//                    ciperByte = (byte) (48 + (ciperBytes[i] + keyBytes[i % 4]) % 10);
//                }
//            }
//        }
//
//
//        for (int i = 0; i < ciperBytes.length; i++) {
//
//            //如果是 = + \，原样输出
//            byte ciperByte = ciperBytes[i];
//            String ciperChar = String.valueOf(ciperByte);
//            if (ignoreStr.contains(ciperChar)) {
//                result[i] = ciperByte;
//                continue;
//            }
//            //首次加密截一个byte数
//            byte i1 = (byte) ((ciperBytes[i] + keyBytes[i % 4]) % 127);
//            //大写字母
//            if (highCase(ciperByte)) {
//                //截得的byte依然是大写字母，写回
//                if (highCase(i1)) {
//                    ciperByte = i1;
//                } else {
//                    //截取的byte非大写字母，重新加密后，取余获得新的大写字母
//                    ciperByte = (byte) (65 + (ciperBytes[i] + keyBytes[i % 4]) % 26);
//                }
//            }
//            if (lowCase(ciperByte)) {
//                if (lowCase(i1)) {
//                    ciperByte = i1;
//                } else {
//                    ciperByte = (byte) (97 + (ciperBytes[i] + keyBytes[i % 4]) % 26);
//                }
//            }
//            if (numCase(ciperByte)) {
//                if (numCase(i1)) {
//                    ciperByte = i1;
//                } else {
//                    ciperByte = (byte) (48 + (ciperBytes[i] + keyBytes[i % 4]) % 10);
//                }
//            }
//            result[i] = ciperByte;
//        }
//        System.out.println("加密后HexResult:  " + byteArrToHexString(result));
//        System.out.println("加密后StringResult:  " + new String(result));
//
//    }
//
//    private static Boolean lowCase(Byte b) {
//        return b >= 'a' && b <= 'z';
//    }
//
//    private static Boolean highCase(Byte b) {
//        return b >= 'A' && b <= 'Z';
//    }
//
//    private static Boolean numCase(Byte b) {
//        return b >= '0' && b <= '9';
//    }
//    private static String byteArrToHexString(byte[] b) {
//        String result = "";
//        for (int i = 0; i < b.length; i++) {
//            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
//        }
//        return result;
//    }
//    private static String toHexString1(byte[] b) {
//        StringBuffer buffer = new StringBuffer();
//        for (int i = 0; i < b.length; ++i) {
//            buffer.append(toHexString1(b[i]));
//        }
//        return buffer.toString();
//    }
//
//    private static String toHexString1(byte b) {
//        String s = Integer.toHexString(b & 0xFF);
//        if (s.length() == 1) {
//            return "0" + s;
//        } else {
//            return s;
//        }
//    }
//}

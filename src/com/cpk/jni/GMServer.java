//package com.cpk.jni;
//
//import com.nsec.software.Tool;
//
//public class GMServer {
//
//    public static void run(byte[] priKey, byte[] pubKey, byte[] E, byte[] sig) {
//        //SM2签名验证
//        int rv = GMJni.SM2Verify(pubKey, E, sig);
//        if (rv != 0) {
//            System.out.println("Verify signature fail, error code is " + String.valueOf(rv));
//        } else {
//            System.out.println("Verify signature success.");
//        }
//    }
//
//}

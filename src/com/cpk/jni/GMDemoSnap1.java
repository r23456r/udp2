package com.cpk.jni;

import cn.hutool.Hutool;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.bat.HutoolAESUtil;
import com.nsec.software.Tool;

import java.util.HashMap;
import java.util.Map;

public class GMDemoSnap1 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String keyFile = "D:\\data\\kms\\liweigang.idc";
        String message = "dksf3r38324hrewjrh3r&^%djfdsjkek";
        String pubmatrix = "D:\\data\\kms\\sm2cpk.cn.pkm";
        int rv = 0;
        long start = System.currentTimeMillis();
        // 引入Map缓存 key为pubKey，E，sig的16进制的MD5值
        MapCache cache = new MapCache();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            message = RandomUtil.randomString(message.length());
            byte[] keycard = Tool.ReadFile(keyFile);
            System.out.println("keyCard:" + Tool.Bytes2Hex(keycard));

            //得到私钥
            byte[] priKey = GMJni.GetPriKey(keycard);
            System.out.println("private key:" + Tool.Bytes2Hex(priKey));

            //得到密钥标识
            byte[] keyId = GMJni.GetKeyId(keycard);
            System.out.println("keyId:" + Tool.Bytes2String(keyId));

            //得到公钥
            byte[] pubKey = GMJni.CalPubKey(Tool.String2Bytes(pubmatrix), keyId);
            System.out.println("public key:" + Tool.Bytes2Hex(pubKey));

            //计算E值
            byte[] E = GMJni.SM2GetE(keyId, pubKey, Tool.String2Bytes(message));
            System.out.println("E:" + Tool.Bytes2Hex(E));

            //SM2签名
            byte[] sig = GMJni.SM2Sign(priKey, E);
            System.out.println("sign value:" + Tool.Bytes2Hex(sig));

            //SM2签名验证
            String s = builder.append(Tool.Bytes2Hex(pubKey)).append(Tool.Bytes2Hex(E)).append(Tool.Bytes2Hex(sig)).toString();
            builder.setLength(0);
            String md5 = SecureUtil.md5(s);
            if (null != cache.get(md5)) {
                System.out.println("命中");
            } else {
                rv += GMJni.SM2Verify(pubKey, E, sig);
                cache.add(md5, (byte) 1, 50 * 1000);
            }
        }
        long end = System.currentTimeMillis();
        double v = (end - start) / 1000.0;
        System.out.println("time consuming:" + v + " sec");
        System.out.println(Math.round(100000 / v) + " times/sec");
        if (rv != 0) {
            System.out.println("Verify signature fail, error code is " + String.valueOf(rv));
        } else {
            System.out.println("Verify signature success.");
        }
    }

}

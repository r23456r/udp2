package com.cpk.jni;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.nsec.software.Tool;

import java.util.*;

public class GMDemo {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String keyFile = "D:\\data\\kms\\liweigang.idc";
        String message = "dksf3r38324hrewjrh3r&^%djfdsjkek";
        String pubmatrix = "D:\\data\\kms\\sm2cpk.cn.pkm";

        byte[] keycard = Tool.ReadFile(keyFile);
        System.out.println("keyCard:" + Tool.Bytes2Hex(keycard));

        //得到私钥
        byte[] priKey = GMJni.GetPriKey(keycard);
        String priKeyHex = Tool.Bytes2Hex(priKey);
        System.out.println("private key:" + priKeyHex);

        //得到密钥标识
        byte[] keyId = GMJni.GetKeyId(keycard);
        System.out.println("keyId:" + Tool.Bytes2String(keyId));

        //得到公钥
        byte[] pubKey = GMJni.CalPubKey(Tool.String2Bytes(pubmatrix), keyId);
        System.out.println("public key:" + Tool.Bytes2Hex(pubKey));

        //1000个不同message
        List<String> msgList = new ArrayList<>(12);
        for (int i = 0; i < 1000; i++) {
            //message基础上字符串长度+10
            msgList.add(message + RandomUtil.randomString(10));
        }
        long start = System.currentTimeMillis();
        //签名结果
        int rv = 0;
        //缓存
        MapCache cache = new MapCache();
        //命中缓存次数
        int count = 0;
        int count2 = 0;
        int total = 10000;
        for (int i = 0; i < total; i++) {
            System.out.println("模拟请求第" + i + "次数");

            //计算E值
            //30种不同的message，即仅30个不走缓存
            byte[] E = GMJni.SM2GetE(keyId, pubKey, Tool.String2Bytes(msgList.get(new Random().nextInt(1000))));
            String hexE = Tool.Bytes2Hex(E);
            //SM2签名
            byte[] sig = GMJni.SM2Sign(priKey, E);
            // SM2签名验证
            String hex = hexE + priKeyHex;
            String md5 = SecureUtil.md5(hex);
            if (null != cache.get(md5)) {
                count++;
            } else {
            rv += GMJni.SM2Verify(pubKey, E, sig);
                count2++;
                cache.add(md5, (byte) 1, 50 * 1000);
            }
        }
        long end = System.currentTimeMillis();
        double v = (end - start) / 1000.0;
        System.out.println("time consuming:" + v + " sec");
        System.out.println(Math.round(total / v) + " times/sec");
        System.out.println("命中缓存" + (count));
        System.out.println("未命中" + (count2));
        if (rv != 0) {
            System.out.println("Verify signature fail, error code is " + String.valueOf(rv));
        } else {
            System.out.println("Verify signature success.");
        }
    }

}

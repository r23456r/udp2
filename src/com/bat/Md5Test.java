package com.bat;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.cpk.jni.GMJni;
import com.nsec.software.Tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2021/1/4 10:01
 **/
public class Md5Test {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String keyFile = "D:\\data\\kms\\liweigang.idc";
        String message = "dksf3r38324hrewjrh3r&^%djfdsjkek";
        //messageList初始化
        List<String> msgList = new ArrayList<>(12);
        for (int i = 0; i < 1000; i++) {
            //message基础上字符串长度+10
            msgList.add(message + RandomUtil.randomString(10));
        }
        String pubmatrix = "D:\\data\\kms\\sm2cpk.cn.pkm";

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
        byte[] E = GMJni.SM2GetE(keyId, pubKey, Tool.String2Bytes(msgList.get(new Random().nextInt(1000))));
        long st = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            SecureUtil.md5(Tool.Bytes2Hex(E) + Tool.Bytes2Hex(keyId));
        }
        long end = System.currentTimeMillis();
        System.out.println(end-st);

    }
}

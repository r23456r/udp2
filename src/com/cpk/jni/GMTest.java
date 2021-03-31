package com.cpk.jni;

import com.cpk.jni.GMJni;
import com.nsec.software.Tool;

public class GMTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String keyFile = "D:\\data\\kms\\liweigang.idc";
        String message = "11dksf3r38324hrewjrhsdsdsads3r&^%djfdsjkek";
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


        byte[] E = GMJni.SM2GetE(keyId, pubKey, Tool.String2Bytes(message));
//            System.out.println("E:" + Tool.Bytes2Hex(E));

        //SM2签名
        byte[] sig = GMJni.SM2Sign(priKey, E);
//            System.out.println("sign value:" + Tool.Bytes2Hex(sig));

        //SM2签名验证
        int rv = GMJni.SM2Verify(pubKey, E, sig);

    }

}

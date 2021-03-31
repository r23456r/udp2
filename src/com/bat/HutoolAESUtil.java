package com.bat;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

/**
 * @author zhangyuhang
 */
public class HutoolAESUtil {

    /**
     * 随机生成密钥，并加密
     */
    private final static AES AES = SecureUtil.aes("otms--1234--otms".getBytes());


    /**
     * 加密为16进制表示
     *
     * @param content
     * @return
     */
    public static String encrypt(String content) {
        return AES.encryptHex(content);
    }

    /**
     * 16进制解密
     *
     * @param encrypt
     * @return
     */
    public static String decrypt(String encrypt) {
        return AES.decryptStr(encrypt);
    }

    public static void main(String[] args) {
        String name = "12345678";
        String miwen = encrypt(name);
        System.out.println(name + "AES加密：" + miwen);
        System.out.println(name + "AES解密：" + decrypt("73a9e00314801c17e7504570857fdf5a"));
        final Base64.Decoder decoder = Base64.getDecoder();
        final Base64.Encoder encoder = Base64.getEncoder();

        String m1 = "12345678";
        String miwen2 = new String(encoder.encode(m1.getBytes()));
        String plainPw = new String(decoder.decode(miwen2));
        System.out.println(m1 + "加密后--------" + miwen2);
        System.out.println(m1 + "加解密后--------" + plainPw);

        int x = 0;

        System.out.println();

    }
}

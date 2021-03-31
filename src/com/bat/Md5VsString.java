package com.bat;

import cn.hutool.crypto.SecureUtil;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2021/1/8 16:17
 **/
public class Md5VsString {
    byte[] x = "11^$$^$1".getBytes();
    public static void main(String[] args) {
        long s1 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            String md5 = SecureUtil.md5("11^$$^$1" + i);

        }
        long e1 = System.currentTimeMillis();
        System.out.println(e1 - s1);

        long s2 = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {

        }
        long e2 = System.currentTimeMillis();
        System.out.println(e2 - s2);
    }
}

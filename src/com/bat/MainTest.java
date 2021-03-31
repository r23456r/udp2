package com.bat;

import com.nsec.exception.CPKException;
import com.nsec.software.CPKSoftwareApi;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static com.bat.MultiThreadBase.Hex2Byte;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2020/11/30 15:54
 **/
public class MainTest {

    private static final CPKSoftwareApi softwareApi = CPKSoftwareApi.getInstance();
    private static final AtomicInteger count = new AtomicInteger(0);
    private static final AtomicInteger error_count = new AtomicInteger(0);
    private static byte[] signVal = null;
    private static byte[] E = null;
    private static final byte[] MESSAGE = "一文中，我们谈到了云原生计算包含三个维度的内容：云原生基础设施，软件架构和交付与运维体系，本文将聚焦于软件架构层面。".getBytes();
    private static final Integer THREAD_NUM = 10000;
    private static volatile CountDownLatch downLatch = new CountDownLatch(THREAD_NUM - 1);
    private static final String userId = "test";
    private static ThreadLocal<Integer> map = new ThreadLocal<>();

    public static void main(String[] args) throws CPKException {
        String priKeyString = "58b33fa1f9673c72f7841189c2086a8d28167254a20fea31e29b2101f3a5c429";
        byte[] priKey = Hex2Byte(priKeyString);
        String pubKeyString = "8c3b1578f55670d28ca0bbc1dd26fc568007f80977240acd3637a7ceb8920fe86a69a5e1ee03507713d1c2011a36d6ef3629c1da4b2b684f3335882bb3a9ab86";
        byte[] pubKey = Hex2Byte(pubKeyString);

        E = softwareApi.SM2GetE(userId, pubKey, System.currentTimeMillis() / 1000, MESSAGE);
        signVal = softwareApi.SM2Sign(priKey, E);
        long l = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            softwareApi.SM2VerifyByPubKey(pubKey, E, signVal);
        }
        long end = System.currentTimeMillis();
        System.out.println(10000 / ((end - l) / 1000));
    }

    private static byte[] Hex2Byte(String hexString) {
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

package com.bat;

import com.nsec.exception.CPKException;
import com.nsec.software.CPKSoftwareApi;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentService {

    private static final CPKSoftwareApi softwareApi = CPKSoftwareApi.getInstance();
    private static final AtomicInteger count = new AtomicInteger(0);
    private static final AtomicInteger error_count = new AtomicInteger(0);
    private static byte[] signBytes = "".getBytes();
    private static byte[] getE = "".getBytes();
    private static final byte[] MESSAGE = "一文中，我们谈到了云原生计算包含三个维度的内容：云原生基础设施，软件架构和交付与运维体系，本文将聚焦于软件架构层面。".getBytes();
    //并发数量
    private static CountDownLatch cdl;
    private static final Integer THREAD_NUM = 2000;

    public static void main(String[] args) throws CPKException {
        cdl = new CountDownLatch(THREAD_NUM);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1,
                1,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        String priKeyString1 = "58b33fa1f9673c72f7841189c2086a8d28167254a20fea31e29b2101f3a5c429";
        byte[] priKey = hexStringToBytes(priKeyString1);
        String pubKeyString1 = "8c3b1578f55670d28ca0bbc1dd26fc568007f80977240acd3637a7ceb8920fe86a69a5e1ee03507713d1c2011a36d6ef3629c1da4b2b684f3335882bb3a9ab86";
        byte[] pubKey = hexStringToBytes(pubKeyString1);

        String priKeyString = toHexString1(priKey);
        String pubKeyString = toHexString1(pubKey);


        getE = softwareApi.SM2GetE("1", pubKey, 0L, MESSAGE);
        signBytes = softwareApi.SM2Sign(priKey, getE);
        System.out.println("线程数: " + THREAD_NUM);
        System.out.println("priKey： " + priKeyString);
        System.out.println("pubKey：" + pubKeyString);
        System.out.println("getE：" + toHexString1(getE));
        System.out.println("signBytes：" + toHexString1(signBytes));

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < THREAD_NUM; i++) {
            //当前JVM占用的内存总数(M)
            double total = (Runtime.getRuntime().totalMemory()) / (1024.0 * 1024);
            //JVM最大可用内存总数(M)
            double max = (Runtime.getRuntime().maxMemory()) / (1024.0 * 1024);
            //JVM空闲内存(M)
            double free = (Runtime.getRuntime().freeMemory()) / (1024.0 * 1024);
            //可用内存内存(M)
            double mayuse = (max - total + free);
            //已经使用内存(M)
            double used = (total - free);
            executor.execute(new ConcurrentService.Run(cdl, priKey, pubKey));
        }

        // 等待
        try {
            cdl.await();
            long endTime = System.currentTimeMillis();
            System.out.println("目标方法--SM2VerifyByPubKey");
            System.out.println("start at [" + new Date(startTime));
            System.out.println("ended at:" + new Date(endTime));
            System.out.println("cost: " + (endTime - startTime) + "ms");
            System.out.println("count times: " + count.get());
            System.out.println("error_count times: " + error_count.get());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage() + "\t\t" + e.getCause());
        }
        executor.shutdown();
    }

    /**
     * 线程类
     */
    private static class Run implements Runnable {
        private final CountDownLatch startLatch;

        private final byte[] priKey;

        private final byte[] pubKey;

        public Run(CountDownLatch startLatch, byte[] priKey, byte[] pubKey) {
            this.startLatch = startLatch;
            this.priKey = priKey;
            this.pubKey = pubKey;
        }

        @Override
        public void run() {
            // 减一
            cdl.countDown();
            /**
             *  先加密  再解密
             */
            try {
                softwareApi.SM2VerifyByPubKey(pubKey, getE, signBytes);
                System.out.println("线程名称： " + Thread.currentThread().getName() + "，downLatch：" + cdl.getCount());
                count.getAndIncrement();
            } catch (CPKException e) {
                error_count.getAndIncrement();
            }
        }
    }

    public static byte[] hexStringToBytes(String hexString) {
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

    public static String toHexString1(byte b) {
        String s = Integer.toHexString(b & 0xFF);
        if (s.length() == 1) {
            return "0" + s;
        } else {
            return s;
        }
    }

    public static String toHexString1(byte[] b) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; ++i) {
            buffer.append(toHexString1(b[i]));
        }
        return buffer.toString();
    }
}

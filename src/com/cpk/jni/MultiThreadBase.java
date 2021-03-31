package com.cpk.jni;

import com.nsec.exception.CPKException;
import com.nsec.software.CPKSoftwareApi;
import com.nsec.software.Tool;

import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreadBase {

    private static final CPKSoftwareApi softwareApi = CPKSoftwareApi.getInstance();
    private static final AtomicInteger count = new AtomicInteger(0);
    private static final AtomicInteger error_count = new AtomicInteger(0);
    private static byte[] signVal = null;
    private static byte[] E = null;
    private static final byte[] MESSAGE = "一文中，我们谈到了云原生计算包含三个维度的内容：云原生基础设施，软件架构和交付与运维体系，本文将聚焦于软件架构层面。".getBytes();
    private static final Integer THREAD_NUM = 10000;
    private static volatile CountDownLatch downLatch = new CountDownLatch(THREAD_NUM - 1);
    private static ThreadLocal<Integer> map = new ThreadLocal<>();
    // TODO Auto-generated method stub
    private static String keyFile = "D:\\data\\kms\\liweigang.idc";
    private static String message = "dksf3r38324hrewjrh3r&^%djfdsjkek";
    private static String pubmatrix = "D:\\data\\kms\\sm2cpk.cn.pkm";

    public static void main(String[] args) throws CPKException, InterruptedException {

        /**
         * 线程池维护线程数 为2
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1,
                1,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(),
                new ThreadFactory() {
                    private final AtomicInteger threadNumber = new AtomicInteger(1);
                    SecurityManager securityManager = System.getSecurityManager();

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread((securityManager != null) ? securityManager.getThreadGroup() :
                                Thread.currentThread().getThreadGroup(), r,
                                "Thread-" + threadNumber.getAndIncrement(),
                                0);
                    }
                },
                new ThreadPoolExecutor.DiscardOldestPolicy());

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

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < THREAD_NUM; i++) {
            ((ExecutorService) executor).submit(new Run(downLatch, priKey, pubKey));
        }

        // 等待
        downLatch.await();

        long endTime = System.currentTimeMillis();
        System.out.println("目标方法--SM2VerifyByPubKey");
        System.out.println("start at [" + new Date(startTime));
        System.out.println("ended at:" + new Date(endTime));
        System.out.println("cost: " + (endTime - startTime) + "ms");
        System.out.println("count times: " + count.get());
        System.out.println("error_count times: " + error_count.get());
        System.out.println("每秒单线程执行次数" + 10000 / ((endTime - startTime) / 1000));
        ((ExecutorService) executor).shutdown();
    }

    /**
     * 线程类
     */
    private static class Run implements Runnable {

        private final byte[] priKey;

        private final byte[] pubKey;

        private CountDownLatch downLatch;

        public Run(CountDownLatch downLatch, byte[] priKey, byte[] pubKey) {
            this.downLatch = downLatch;
            this.priKey = priKey;
            this.pubKey = pubKey;
        }

        @Override
        public void run() {

            String name = Thread.currentThread().getName();
            Integer num = map.get();
            System.out.println("线程名称： " + name + "当前线程已执行次数： " + (num == null ? 0 : num) + "，总轮询次数：" + (10000 - downLatch.getCount()));
            try {
                softwareApi.SM2VerifyByPubKey(pubKey, E, signVal);
                map.set(num == null ? 1 : num + 1);
                count.getAndIncrement();
            } catch (CPKException e) {
                error_count.getAndIncrement();
            } finally {
                downLatch.countDown();
            }

        }
    }

    public static byte[] Hex2Byte(String hexString) {
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

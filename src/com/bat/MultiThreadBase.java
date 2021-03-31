package com.bat;

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
    private static final String userId = "test";
    private static ThreadLocal<Integer> map = new ThreadLocal<>();

    /**
     * new ThreadFactory() {
     * private final AtomicInteger threadNumber = new AtomicInteger(1);
     * SecurityManager s = System.getSecurityManager();
     *
     * @param args
     * @throws CPKException
     * @throws InterruptedException
     * @Override public Thread newThread(Runnable r) {
     * return new Thread((s != null) ? s.getThreadGroup() :
     * Thread.currentThread().getThreadGroup(), r,
     * "Thread-" + threadNumber.getAndIncrement(),
     * 0);
     * }
     * }
     */

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
                    SecurityManager s = System.getSecurityManager();

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread((s != null) ? s.getThreadGroup() :
                                Thread.currentThread().getThreadGroup(), r,
                                "Thread-" + threadNumber.getAndIncrement(),
                                0);
                    }
                },
                new ThreadPoolExecutor.DiscardOldestPolicy());

        String priKeyString = "58b33fa1f9673c72f7841189c2086a8d28167254a20fea31e29b2101f3a5c429";
        byte[] priKey = Hex2Byte(priKeyString);
        String pubKeyString = "8c3b1578f55670d28ca0bbc1dd26fc568007f80977240acd3637a7ceb8920fe86a69a5e1ee03507713d1c2011a36d6ef3629c1da4b2b684f3335882bb3a9ab86";
        byte[] pubKey = Hex2Byte(pubKeyString);

        E = softwareApi.SM2GetE(userId, pubKey, System.currentTimeMillis() / 1000, MESSAGE);
        signVal = softwareApi.SM2Sign(priKey, E);
        System.out.println("轮询次数: " + THREAD_NUM);
        System.out.println("priKey： " + priKeyString);
        System.out.println("pubKey：" + pubKeyString);
        System.out.println("getE：" + Tool.Bytes2Hex(E));
        System.out.println("signVal：" + Tool.Bytes2Hex(signVal));

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

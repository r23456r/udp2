package com.cpk.jni;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.nsec.exception.CPKException;
import com.nsec.software.Tool;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2020/12/31 15:58
 **/
public class GMClientSnap1 {
    //命中缓存次数
    private static AtomicInteger count = new AtomicInteger(0);
    private static AtomicInteger count2 = new AtomicInteger(0);

    public static void main(String[] args) {

        /**
         * 线程池维护线程数 为10
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                10,
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


        //服务端SM2签名验证
        //签名结果


        for (int i = 100; i < 3000; i += 100) {
            long sumTime = 0L;
            long sumTime2 = 0L;
            //缓存
            MapCache cache = new MapCache();
            count = new AtomicInteger(0);
            count2 = new AtomicInteger(0);
            System.out.println("当前模拟请求次数" + i);
            for (int j = 0; j < i; j++) {
                //计算E值
                byte[] E = GMJni.SM2GetE(keyId, pubKey, Tool.String2Bytes(msgList.get(new Random().nextInt(1000))));
                //SM2签名
                byte[] sig = GMJni.SM2Sign(priKey, E);
                long start = System.nanoTime();
                ((ExecutorService) executor).submit(new Run(cache, keyId, priKey, pubKey, E, sig, false));
//                server(cache, keyId, priKey, pubKey, E, sig, false);
                long end = System.nanoTime();
                sumTime += (end - start);

                long start2 = System.nanoTime();
                ((ExecutorService) executor).submit(new Run(cache, keyId, priKey, pubKey, E, sig, true));
//                server(cache, keyId, priKey, pubKey, E, sig, true);
                long end2 = System.nanoTime();
                sumTime2 += (end2 - start2);

            }
            double sumTimeInSecond = (double) sumTime / 1_000_000_000;
            double sumTime2InSecond = (double) sumTime2 / 1_000_000_000;
            System.out.println("--------不使用缓存-----------" + "time consuming:" + sumTimeInSecond + " s；" + Math.round(i / sumTimeInSecond) + " times/s；");
            System.out.println("--------使用缓存-------------" + "time consuming:" + sumTime2InSecond + " s；" + Math.round(i / sumTime2InSecond) + " times/s；" + "命中缓存" + (count) + "; 未命中" + (count2));

        }
        ((ExecutorService) executor).shutdown();
    }

    public static void server(MapCache cache, byte[] keyId, byte[] priKey, byte[] pubKey, byte[] E, byte[] sig, Boolean isCache) {
        if (!isCache) {
            GMJni.SM2Verify(pubKey, E, sig);
        } else {
            // 计算E和priKey字符串的Hex
            String md5 = SecureUtil.md5(Tool.Bytes2Hex(E) + Tool.Bytes2Hex(keyId));
            if (null != cache.get(md5)) {
                count.getAndIncrement();
            } else {
                GMJni.SM2Verify(pubKey, E, sig);
                count2.getAndIncrement();
                //对象在内存驻留时间为500s，值默认为1
                cache.add(md5, (byte) 1, 500 * 1000);
            }
        }
    }

    private static class Run implements Runnable {

        private final byte[] priKey;

        private final byte[] pubKey;
        private MapCache cache;
        private final byte[] keyId;

        private final byte[] sig;

        private final byte[] E;
        private final Boolean isCache;

        public Run(MapCache cache, byte[] keyId, byte[] priKey, byte[] pubKey, byte[] E, byte[] sig, Boolean isCache) {
            this.priKey = priKey;
            this.pubKey = pubKey;
            this.cache = cache;
            this.keyId = keyId;
            this.sig = sig;
            this.E = E;
            this.isCache = isCache;
        }

        @Override
        public void run() {
//            System.out.println("当前线程名称： " + Thread.currentThread().getName());
            server(cache, keyId, priKey, pubKey, E, sig, isCache);
        }
    }

}

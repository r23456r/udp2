package com.cpk.jni;

import cn.hutool.core.util.RandomUtil;
import com.nsec.software.Tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GMTestSnap4Final {
    //命中缓存次数
    private static AtomicInteger hit = new AtomicInteger(0);
    private static AtomicInteger miss = new AtomicInteger(0);
    private static ThreadLocal<Integer> map = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        // TODO Auto-generated method stub
        String keyFile = "D:\\data\\kms\\liweigang.idc";
        String message = "dksf3r38324hrewjrh3r&^%djfdsjkek";
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


        //数据准备
        List<String> msgList = new ArrayList<>(12);
        for (int i = 0; i < 1000; i++) {
            //message基础上字符串长度+10
            msgList.add(message + RandomUtil.randomString(10));
        }

        //创建线程池，如果请求大多数不同，PoolSize可以大一些，否则小一点。
        ThreadPoolExecutor exec = new ThreadPoolExecutor(
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

        for (int i = 100; i < 3000; i += 100) {
            long sumTime = 0L;
            long sumTime2 = 0L;
            //缓存
            MapCache cache = new MapCache();
            hit = new AtomicInteger(0);
            miss = new AtomicInteger(0);
            System.out.println("当前模拟请求次数" + i);
            long start = System.currentTimeMillis();
            for (int j = 0; j < i; j++) {
                //计算E值
                byte[] E = GMJni.SM2GetE(keyId, pubKey, Tool.String2Bytes(msgList.get(new Random().nextInt(1000))));
                //SM2签名
                byte[] sig = GMJni.SM2Sign(priKey, E);
                //SM2签名验证
                exec.execute(() -> {
                    server(cache, keyId, priKey, pubKey, E, sig, true);
                });
            }
            exec.shutdown();
            exec.awaitTermination(1, TimeUnit.HOURS);

            //只有线程池关闭，或超时1小时后才执行下面代码，用于统计时间
            long end = System.currentTimeMillis();
            long duration = (end - start) / 1000;
//            System.out.println(((double) (end - start) / 1000) + " s");
//            System.out.println(Math.round(total / ((double) (end - start) / 1000)) + " times");
            System.out.println("命中缓存" + (hit) + "; 未命中" + (miss));

//            System.out.println("--------不使用缓存-----------" + "time consuming:" + sumTimeInSecond + " s；" + Math.round(i / sumTimeInSecond) + " times/s；");
            System.out.println("--------使用缓存-------------" + "time consuming:" + duration + " s；" + Math.round(i / duration) + " times/s；" + "命中缓存" + (hit) + "; 未命中" + (miss) + "; md5加密耗时");
        }

        exec.shutdown();
        exec.awaitTermination(1, TimeUnit.HOURS);
        //只有线程池关闭，或超时1小时后才执行下面代码，用于统计时间
        long end = System.currentTimeMillis();
//        System.out.println(((double) (end - start) / 1000) + " s");
//        System.out.println(Math.round(total / ((double) (end - start) / 1000)) + " times");
        System.out.println("命中缓存" + (hit) + "; 未命中" + (miss));

    }

    public static void server(MapCache cache, byte[] keyId, byte[] priKey, byte[] pubKey, byte[] E, byte[] sig, Boolean isCache) {
        if (!isCache) {
            GMJni.SM2Verify(pubKey, E, sig);
        } else {
//             计算E和priKey字符串的Hex
            String hex = Tool.Bytes2Hex(E) + Tool.Bytes2Hex(keyId);
            //判断和线程池大小相关，不准确
            if (null != cache.get(hex)) {
                hit.getAndIncrement();
            } else {
                GMJni.SM2Verify(pubKey, E, sig);
                miss.getAndIncrement();
//                对象在内存驻留时间为500s，值默认为1
                cache.add(hex, (byte) 1, 500 * 1000);
            }
        }
    }
}

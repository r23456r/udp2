package com.cpk.jni;

import cn.hutool.crypto.SecureUtil;
import com.nsec.software.Tool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GMTestSnap3 {
    //命中缓存次数
    private static AtomicInteger hit = new AtomicInteger(0);
    private static AtomicInteger miss = new AtomicInteger(0);

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

        //计算E值
        byte[] E = GMJni.SM2GetE(keyId, pubKey, Tool.String2Bytes(message));
        System.out.println("E:" + Tool.Bytes2Hex(E));

        //SM2签名
        byte[] sig = GMJni.SM2Sign(priKey, E);
        System.out.println("sign value:" + Tool.Bytes2Hex(sig));


        //创建线程池，如果请求大多数不同，PoolSize可以大一些，否则小一点。
        ThreadPoolExecutor exec = new ThreadPoolExecutor(
                5,
                5,
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

        //签名缓存
        MapCache cache = new MapCache();
        //模拟请求次数
        int total = 10000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < total; i++) {
            //SM2签名验证
            exec.execute(() -> {
                server(cache, keyId, priKey, pubKey, E, sig, true);
            });
        }
        exec.shutdown();
        exec.awaitTermination(1, TimeUnit.HOURS);
        //只有线程池关闭，或超时1小时后才执行下面代码，用于统计时间
        long end = System.currentTimeMillis();
        System.out.println(((double) (end - start) / 1000) + " s");
        System.out.println(Math.round(total / ((double) (end - start) / 1000)) + " times");
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

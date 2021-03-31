package com.cpk.jni;

import com.bat.ByteUtil;
import com.nsec.software.Tool;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GMTestSnap5 {
    private static String keyFile = "D:\\data\\kms\\liweigang.idc";

    private static String pubmatrix = "D:\\data\\kms\\sm2cpk.cn.pkm";

    public static void main(String[] args) throws InterruptedException {
        // TODO Auto-generated method stub

        String message = "dksf3r38324hrewjrh3r&^%djfdsjkek";
        byte[] keycard = Tool.ReadFile(keyFile);

        //得到私钥
        byte[] priKey = GMJni.GetPriKey(keycard);

        //得到密钥标识
        byte[] keyId = GMJni.GetKeyId(keycard);

        //                 Server 端
        MapCache cache = new MapCache();

        //创建线程池，四核CPU size=5即可
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
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            run(cache, keyId, priKey, message);
        }
        exec.shutdown();
        exec.awaitTermination(1, TimeUnit.HOURS);
        long end = System.currentTimeMillis();
        System.out.println("----" + (end - start) + "----");
    }

    public static void run(MapCache cache, byte[] keyId, byte[] priKey, String message) {
        {
            String keyIdHex = Tool.Bytes2Hex(keyId);


            //查询keyId和Message联合缓存是否存在
            String kmUnion = Tool.Bytes2Hex(keyId) + message;
            String o1 = (String) cache.get(kmUnion);
            if (null != o1) {
                System.out.println("Verify signature success. 已签名");
                return;
            }

            //查询如果联合缓存不存在，查询pubKey缓存是否存在
            String o = (String) cache.get(keyIdHex);
            byte[] pubKey;
            if (o == null) {
                //得到公钥
                pubKey = GMJni.CalPubKey(Tool.String2Bytes(pubmatrix), keyId);
                cache.add(keyIdHex, Tool.Bytes2Hex(pubKey), 5 * 60 * 1000);
            } else {
                pubKey = ByteUtil.hexStringToBytes(o);
            }

            //计算E值
            byte[] E = GMJni.SM2GetE(keyId, pubKey, Tool.String2Bytes(message));
            //SM2签名
            byte[] sig = GMJni.SM2Sign(priKey, E);
            //SM2签名验证
            int rv = GMJni.SM2Verify(pubKey, E, sig);
            if (rv != 0) {
                System.out.println("Verify signature fail, error code is " + String.valueOf(rv));
            } else {
                //联合对象的缓存存活5分钟
                cache.add(kmUnion, "1", 5 * 60 * 1000);
                System.out.println("Verify signature success.");
            }
        }
    }
}

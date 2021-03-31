package com.cpk.jni;

import com.bat.ByteUtil;
import com.nsec.software.Tool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GMTestSnap6_MutThread {
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

        //创建线程池，四核CPU size=5即可
        ThreadPoolExecutor exec = new ThreadPoolExecutor(
                70,
                70,
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

        //计算公钥
        byte[] pubKey = GMJni.CalPubKey(Tool.String2Bytes(pubmatrix), keyId);

        //计算E值
        byte[] E = GMJni.SM2GetE(keyId, pubKey, Tool.String2Bytes(message));
        //SM2签名
        byte[] sig = GMJni.SM2Sign(priKey, E);

        long start = System.currentTimeMillis();
        Map map = new HashMap<>();
        map.put("1", "1");
        for (int i = 0; i < 10000000; i++) {
            //SM2签名验证
//           exec.execute(() -> GMJni.SM2Verify(pubKey, E, sig));
            map.get("1");
        }
        exec.shutdown();
        exec.awaitTermination(1, TimeUnit.HOURS);
        long end = System.currentTimeMillis();
        System.out.println("----" + (end - start) + "----");
        System.out.println("" + (double) 100000 * 1000 / (end - start));
    }
}

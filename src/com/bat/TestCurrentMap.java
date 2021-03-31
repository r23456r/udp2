package com.bat;

import cn.hutool.crypto.SecureUtil;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2021/1/6 14:39
 **/
public class TestCurrentMap {
    public static void main(String[] args) {
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
        exec.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(1);
            }
        });
        exec.shutdown();
        System.out.println(2);
        exec.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(2);
            }
        });
        System.out.println(exec.isTerminated());
        System.out.println(3);
    }
}

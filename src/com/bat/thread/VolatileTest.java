package com.bat.thread;

import cn.hutool.core.thread.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2021/1/29 17:29
 **/
public class VolatileTest {
    public static void main(String[] args) {

        ThreadFactory factory = new ThreadFactoryBuilder().setNamePrefix("thread-").build();

        ExecutorService pool = new ThreadPoolExecutor(1, 2,
                60L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), factory, new ThreadPoolExecutor.DiscardPolicy());


        for (int i = 0; i < 10000; i++) {
            pool.submit(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }
        pool.shutdown();
    }
}

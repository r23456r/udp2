package com.bat;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import java.util.concurrent.*;

class SimpleThreadFactory {

    public static void main(String args[]) {
        ThreadFactory factory = new ThreadFactoryBuilder().setNamePrefix("thread-").build();
        final ExecutorService executorService = Executors.newFixedThreadPool(5, factory);

        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
        }
        executorService.shutdown();
    }
}
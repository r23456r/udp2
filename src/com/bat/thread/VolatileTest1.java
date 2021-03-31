package com.bat.thread;

import cn.hutool.core.thread.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2021/1/29 17:29
 **/
public class VolatileTest1 {

    public static void main(String[] args) throws InterruptedException {
        Task task = new Task();
        Thread t1 = new Thread(task, "t1");

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("通知线程停止---");
                task.aBoolean = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2");
        t1.start();
        t2.start();
        Thread.sleep(1000);
    }
}

class Task implements Runnable {
   volatile boolean aBoolean = false;
    int i = 0;

    @Override
    public void run() {
        while (!aBoolean) {
            i++;
        }
        System.out.println("成功突破" + i);
    }
}

package com.bat.entity;

import java.util.Date;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2020/5/29 15:56
 **/
public class Time {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            long time = System.currentTimeMillis();
            Thread.sleep(10);

            System.out.println("第  "+i +"  :  " +new Date((time+100000)*1000));
        }
    }
}

package com.bat;

import cn.hutool.Hutool;
import cn.hutool.core.thread.ThreadUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;

public class TestCalculateHours {

    public static void main(String[] args) throws ParseException, InterruptedException {
        String beginTime = "2020-11-02 09:00:15";
        String endTime = "2020-11-03 18:00:15";
        CalculateHours ch = new CalculateHours();
//        ch.calculateHours(beginTime, endTime);
        System.out.println("---------------");
        CalculateOtHours otHours = new CalculateOtHours();
        ExecutorService executor = ThreadUtil.newExecutor(1);
        while (true) {
            executor.submit(() -> {
                float hours = 0;
                try {
                    hours = otHours.calculateHours(DateSyncUtil.parse(beginTime), DateSyncUtil.parse(endTime));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (hours > 100) {
                    System.out.println("---------------------------------------------------");
                    System.out.println("apply -Minute" + (int) (hours * 60));
                    System.out.println(beginTime + endTime);
                    System.out.println("---------------------------------------------------");
                }
                ThreadUtil.sleep(10000);
            });


        }

    }

}
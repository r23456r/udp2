package com.bat.entity;

import org.apache.commons.lang3.RandomUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2020/7/22 17:15
 **/
public class Cron {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 2020);
            calendar.set(Calendar.MONTH, 7 - 1);
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH").format(calendar.getTime()));
        }
    }
}

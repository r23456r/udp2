package com.bat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2020/11/24 17:34
 **/
public class HanziTest {
    public static void main(String[] args) {
        String cipher = HanZiEncode.encode("晋商博创（北京）科技有限公司");
        Calendar calender = Calendar.getInstance();
        /*
         * 希望表示 2008-08-30 10:22:33
         */
        calender.set(Calendar.YEAR, 2008);
        /*
         * 月份的值可以使用常量
         * 也可以使用定量，需要注意:月份从0开始
         * 即0表示1月
         */
        calender.set(Calendar.MONTH, 7);
        /*
         * Calender。Date与Calender.DAY_OF_MONTH等价
         */
        calender.set(Calendar.DAY_OF_MONTH, 30);
        calender.set(Calendar.HOUR_OF_DAY, 10);
        calender.set(Calendar.MINUTE, 22);
        calender.set(Calendar.SECOND, 33);
        Date date = calender.getTime();
        System.out.println(date);

        for (int i = 0; i < 100; i++) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println(simpleDateFormat.format(new Date(1611590400000L + new Random().nextInt(3542400) * 1000)));
        }
        System.out.println("1----------------");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(simpleDateFormat.format(new Date(1611590400000L + new Random().nextInt(3542400) * 1000L)));

        System.out.println(new Date(1611590400000L));
        System.out.println(HanZiDecode.decode(cipher));
        System.out.println("（".getBytes().length);
    }
}

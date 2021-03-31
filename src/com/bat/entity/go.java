package com.bat.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2020/2/25 17:19
 **/
public class go {
    public static void main(String[] args) throws ParseException {

        System.out.println(System.currentTimeMillis());
        for (String s : getMonthBetween2("2019-05", "2020-01")) {
            System.out.println(s);
        }
    }
    private static List<String> getMonthBetween2(String minDate, String maxDate) throws ParseException {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }
}

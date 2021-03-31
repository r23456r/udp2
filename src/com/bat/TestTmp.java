package com.bat;

import com.bat.entity.DateUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2020/10/23 9:28
 **/
public class TestTmp {
    public static void main(String[] args) throws ParseException {
        System.out.println(DateUtils.formatMonthStr("10-02"));
        System.out.println(DateUtils.formatYearStr("2000-10"));
        System.out.println(DateUtils.formatQuarterStr("2020-37"));


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-w e");
//        return LocalDate.parse(text+" 2", formatter);

    }

}

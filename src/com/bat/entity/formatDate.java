package com.bat.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2020/10/22 11:10
 **/
public class formatDate {
    public static void main(String[] args) throws ParseException {
        DateTimeFormatter QUARTER_FORMAT = DateTimeFormatter.ofPattern("yyyy 'Q'q");
        DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(DATE_FORMAT.parse("2007-01-23").toInstant().atZone(ZoneId.systemDefault()).format(QUARTER_FORMAT));
    }

}

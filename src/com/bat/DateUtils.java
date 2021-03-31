package com.bat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class DateUtils {

    private static final String WEEK_FORMAT = "yyyy-w";
    private static final String WEEK_FORMAT_E = "YYYY-w e";
    private static final String HOUR_FORMAT = "HH:00";
    public static final String DAYS_FORMAT = "MM-dd";
    public static final String MONTH_FORMAT = "yyyy-MM";
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static LocalDate now = LocalDate.now();

    /**
     * str转date
     *
     * @param str
     * @return
     */
    public static LocalDate formatYearStr(String str) throws ParseException {
        return formatYearStr(str, MONTH_FORMAT);
    }

    public static LocalDate formatYearStr(String str, String pattern) throws ParseException {
        LocalDate aLD = LocalDate.parse(str + "-01");
        return aLD;
    }

    public static LocalDate formatQuarterStr(String str) throws ParseException {
        return formatQuarterStr(str, WEEK_FORMAT_E);
    }

    public static LocalDate formatQuarterStr(String str, String pattern) throws ParseException {
        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern(pattern);
        //默认周日到周六是7天 周一从2开始
        LocalDate aLD = LocalDate.parse(str + " 1", ofPattern);
        return aLD;
    }

    public static LocalDate formatMonthStr(String str) throws ParseException {
        return formatMonthStr(str, DAYS_FORMAT);
    }

    public static LocalDate formatMonthStr(String str, String pattern) throws ParseException {
        LocalDate aLD = LocalDate.parse(now.getYear() + "-" + str);
        return aLD;
    }


    /**
     * date转String
     *
     * @param date
     * @return
     */
    public static String getStrInDate(Date date) {
        return format.format(date);
    }

    public static String formatHour(Date date) {
        return formatHour(date, HOUR_FORMAT);
    }

    public static String formatMonth(Date date) {
        return formatHour(date, DAYS_FORMAT);
    }

    public static String formatYear(Date date) {

        return formatHour(date, MONTH_FORMAT);
    }

    public static String formatHour(LocalDateTime date) {
        return formatHour(date, HOUR_FORMAT);
    }

    public static String formatQuerter(LocalDateTime date) {
        return formatHour(date, WEEK_FORMAT);
    }

    public static String formatMonth(LocalDateTime date) {
        return formatHour(date, DAYS_FORMAT);
    }

    public static String formatYear(LocalDateTime date) {
        return formatHour(date, MONTH_FORMAT);
    }

    private static String formatHour(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(localDateTime);
    }

    private static String formatHour(Date date, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = fromDate(date);
        return dateTimeFormatter.format(localDateTime);
    }




    public static String formatFullTime(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(dateTimeFormatter);
    }

    public static Date fromLocalDateTime(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime fromDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDate fromDateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date fromLocalDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }


}


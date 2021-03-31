package com.bat;

import java.text.ParseException;

import java.text.SimpleDateFormat;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 获取两个时间段之间重合的部分
 *
 * @author <a href=
 * <p>
 * 'http://www.wlgdo.com'>com.wlgdo</a>[wangligang<a>wlgchun@163.com</a>]
 * @date 2017年5月14日
 * @date 2017-05-14 21:54:18
 */

public class DateUtilz {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static void main(String[] args) throws ParseException {
        Date start = sdf.parse("2020-05-02 17:30:00");
        Date end = sdf.parse("2020-05-03 18:40:00");
        System.out.println((end.getTime() - start.getTime()) > 86400000);
        System.out.println(getAlphalDate(start, end));
    }

    public static Long getAlphalDate(Date start, Date end) {

        LocalDateTime startLocal = DateUtils.fromDate(start);
        LocalDateTime endLocal = DateUtils.fromDate(end);
        //获取调休标准时间 当天18：30至第二天9：00
        LocalDateTime baseStartTime = LocalDateTime.of(startLocal.getYear(), startLocal.getMonth(), startLocal.getDayOfMonth(), 18, 30, 0);
        //第二天00：00
        LocalDateTime baseEndTime = LocalDateTime.of(startLocal.getYear(), startLocal.getMonth(), startLocal.getDayOfMonth() + 1, 9, 0, 0);

        if (endLocal.isAfter(baseEndTime)) {
            end = DateUtils.fromLocalDateTime(baseEndTime);
        }
        // 标准时间
        Date bt = DateUtils.fromLocalDateTime(baseStartTime);
        Date ot = DateUtils.fromLocalDateTime(baseEndTime);

        // 目标时间
        Date st = start;
        Date ed = end;

        long btlong = Math.min(bt.getTime(), ot.getTime());// 开始时间
        long otlong = Math.max(bt.getTime(), ot.getTime());// 结束时间
        long stlong = Math.min(st.getTime(), ed.getTime());// 开始时间
        long edlong = Math.max(st.getTime(), ed.getTime());// 结束时间

        // 具体算法如下
        // 首先看是否有包含关系
        if ((stlong >= btlong && stlong <= otlong) || (edlong >= btlong && edlong <= otlong)) {
            // 一定有重叠部分
            long sblong = stlong >= btlong ? stlong : btlong;
            long eblong = otlong >= edlong ? edlong : otlong;
            System.out.println("包含的开始时间是：" + sdf.format(sblong) + "-结束时间是：" + sdf.format(eblong));
            return (eblong - sblong) / 1000 / 60;
        }
        return 0L;
    }

}
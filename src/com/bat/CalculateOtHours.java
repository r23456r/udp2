package com.bat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * @author zhangyuhang
 */
public class CalculateOtHours {

    private final  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //这里的格式可以自己设置
    //设置上班时间：该处时间可以根据实际情况进行调整
    int abh = 9;//上午上班时间,小时
    int abm = 0;//上午上班时间,分钟
    int aeh = 12;//下午下班时间，小时
    int aem = 0;//下午下班时间，分钟
    int pbh = 13;//下午上班时间，小时
    int pbm = 0;//下午上班时间，分钟
    int peh = 18;//下午下班时间，小时
    int pem = 0;//下午下班时间，分钟

    float h1 = abh + (float) abm / 60;
    float h2 = aeh + (float) aem / 60;
    float h3 = pbh + (float) pbm / 60;
    float h4 = peh + (float) pem / 60;

    float hoursPerDay = h2 - h1 + h4 - h3;//每天上班小时数

    int daysPerWeek = 5;//每周调休天数
    long milsecPerDay = 1000 * 60 * 60 * 24;//每天的毫秒数
    float hoursPerWeek = hoursPerDay * daysPerWeek;//每星期小时数

    public float calculateHours(Date t1, Date t2) {

        System.out.println("设置的上班时间为：" + abh + ":" + abm + "-" + aeh + ":" + aem
                + "   " + pbh + ":" + pbm + "-" + peh + ":" + pem);
        System.out.println("每天调休时间为：" + hoursPerDay + "小时");

        System.out.println("设置的开始时间为：" + printDate(t1));
        System.out.println("设置的结束时间为：" + printDate(t2));

        //对时间进行预处理
        t1 = processBeginTime(t1);
        t2 = processEndTime(t2);

        System.out.println("预处理后的开始时间为：" + printDate(t1));
        System.out.println("预处理后的结束时间为：" + printDate(t2));

        //若开始时间晚于结束时间，返回0
        if (t1.getTime() > t2.getTime()) {
            System.out.println("总调休时间为：0小时");
            return 0;
        }

        //开始时间到结束时间的完整星期数
        int weekCount = (int) ((t2.getTime() - t1.getTime()) / (milsecPerDay * 7));
        System.out.println("时间间隔内共包含了" + weekCount + "个完整的星期");

        float totalHours = 0;
        totalHours += weekCount * hoursPerWeek;

        //调整结束时间，使开始时间和结束时间在一个星期的周期之内
        t2.setTime(t2.getTime() - weekCount * 7 * milsecPerDay);
        System.out.println("结束时间调整为：" + printDate(t2));

        int dayCounts = 0;//记录开始时间和结束时间之间调休日天数

        //调整开始时间，使得开始时间和结束时间在同一天，或者相邻的调休日内。
        while (t1.getTime() <= t2.getTime()) {
            Date temp = new Date(t1.getTime() + milsecPerDay);
            temp = processBeginTime(temp);
            temp.setHours(t1.getHours());
            temp.setMinutes(t1.getMinutes());
            if (temp.getTime() > t2.getTime()) {
                break;
            } else {
                t1 = temp;
                dayCounts++;
            }
        }
        System.out.println("开始时间向后移动了" + dayCounts + "个调休日");
        System.out.println("开始时间调整为：" + printDate(t1));
        totalHours += dayCounts * hoursPerDay;

        float hh1 = t1.getHours() + (float) t1.getMinutes() / 60;
        float hh2 = t2.getHours() + (float) t2.getMinutes() / 60;

        //处理开始结束是同一天的情况
        if (t1.getDay() == t2.getDay()) {
            float tt = 0;
            tt = hh2 - hh1;
            if (hh1 >= h1 && hh1 <= h2 && hh2 >= h3) {
                tt = tt - (h3 - h2);
            }
            totalHours += tt;
        } else {
            //处理开始结束不是同一天的情况
            float tt1 = h4 - hh1;
            float tt2 = hh2 - h1;
            if (hh1 <= h2) {
                tt1 = tt1 - (h3 - h2);
            }
            if (hh2 >= h3) {
                tt2 = tt2 - (h3 - h2);
            }
            totalHours += (tt1 + tt2);
        }

        System.out.println("总调休时间为：" + totalHours + "小时");
        return totalHours;
    }

    /**
     * 格式化输出时间： yyyy-mm-dd hh:mm:ss 星期x
     *
     * @param t
     * @return
     */
    private String printDate(Date t) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(t);
        String str;
        String xingqi = null;
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                xingqi = "星期天";
                break;
            case 2:
                xingqi = "星期一";
                break;
            case 3:
                xingqi = "星期二";
                break;
            case 4:
                xingqi = "星期三";
                break;
            case 5:
                xingqi = "星期四";
                break;
            case 6:
                xingqi = "星期五";
                break;
            case 7:
                xingqi = "星期六";
                break;
            default:
                break;
        }
        str = format.format(t) + "  " + xingqi;
        return str;
    }

    /**
     * 对结束时间进行预处理，使其处于调休日内的调休时间段内
     *
     * @param t
     * @return
     */
    private Date processEndTime(Date t) {

        float h = t.getHours() + (float) t.getMinutes() / 60;

        //若结束时间晚于下午下班时间，将其设置为下午下班时间
        if (h >= h4) {
            t.setHours(peh);
            t.setMinutes(pem);
        } else {
            //若结束时间介于中午休息时间，那么设置为上午下班时间
            if (h >= h2 && h <= h3) {
                t.setHours(aeh);
                t.setMinutes(aem);
            } else {
                //若结束时间早于上午上班时间，日期向前推一天，并将时间设置为下午下班时间
                if (t.getHours() <= h1) {
                    t.setTime(t.getTime() - milsecPerDay);
                    t.setHours(peh);
                    t.setMinutes(pem);
                }
            }
        }

        //若结束时间是周末，那么将结束时间向前推移到最近的调休日的下午下班时间
        if (t.getDay() == 0 || t.getDay() == 6) {
            t.setTime(t.getTime() - milsecPerDay * (t.getDay() == 6 ? 1 : 2));
            t.setHours(peh);
            t.setMinutes(pem);
        }

        return t;
    }

    /**
     * 对开始时间进行预处理
     *
     * @param t
     * @return
     */
    private Date processBeginTime(Date t) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(t);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        float h = hour + (float) minute / 60;

        //若开始时间晚于下午下班时间，将开始时间向后推一天
        if (h >= h4) {
            cal.setTimeInMillis(t.getTime() + milsecPerDay);
            cal.set(Calendar.HOUR_OF_DAY, abh);
            cal.set(Calendar.MINUTE, abm);
        } else {
            //若开始时间介于中午休息时间，那么设置为下午上班时间
            if (h >= h2 && h <= h3) {
                cal.set(Calendar.HOUR_OF_DAY, pbh);
                cal.set(Calendar.MINUTE, pbm);
            } else {
                //若开始时间早于上午上班时间，将hour设置为上午上班时间
                if (cal.get(Calendar.HOUR_OF_DAY) <= h1) {
                    cal.set(Calendar.HOUR_OF_DAY, abh);
                    cal.set(Calendar.MINUTE, abm);
                }
            }
        }

        //若开始时间是周末，那么将开始时间向后推移到最近的调休日的上午上班时间
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day == 1 || day == 7) {
            cal.setTimeInMillis(t.getTime() + milsecPerDay * (day == 6 ? 2 : 1));
            cal.set(Calendar.HOUR_OF_DAY, abh);
            cal.set(Calendar.MINUTE, abm);
        }
        return cal.getTime();
    }


    /**
     * 将字符串形式的时间转换成Date形式的时间
     *
     * @param time
     * @return
     */
    private Date stringToDate(String time) {

        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
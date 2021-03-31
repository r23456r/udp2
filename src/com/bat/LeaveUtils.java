package com.bat;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: zhangyuhang
 * @modified By：
 * @date ：Created in 2020/11/13 17:30
 **/
public class LeaveUtils {
    /**
     * @param startTime Java接受的格式：Sun Dec 08 08:30:00 GMT+08:00 2019
     *                  前端传的格式是  2019-12-08 08:30:00
     * @param endTime   和startTime格式是一样的
     * @return 返回Map对象   key:leaveDays   value:就是计算结果
     * @description 上午请假3小时。下午5小时
     * @throws Exception
     */
    public Map<String, String> calcLeaveTime(Date startTime, Date endTime) throws Exception {
        String goWorkTimeString = "09:00:00";
        String midWorkTimeString = "12:00:00";
        String endWorkTimeString = "18:00:00";

        String[] legalHolidays2019 = {//2019国家规定的法定节假日
                "2019-01-01", "2019-02-04", "2019-02-05", "2019-02-06", "2019-02-07",
                "2019-02-08", "2019-02-09", "2019-02-10", "2019-04-05", "2019-04-06",
                "2019-04-07", "2019-05-01", "2019-06-07", "2019-06-08", "2019-06-09",
                "2019-09-13", "2019-09-14", "2019-09-15", "2019-10-01", "2019-10-02",
                "2019-10-03", "2019-10-04", "2019-10-05", "2019-10-06", "2019-10-07"
        };
        String[] T2019 = {//2019国家规定需要调休的班
                "2019-02-02", "2019-02-03", "2019-09-29", "2019-10-12"
        };
        String[] legalHolidays2020 = {//2020国家规定的法定节假日
                "2020-01-01", "2020-01-24", "2020-01-25", "2020-01-26", "2020-01-27",
                "2020-01-28", "2020-01-29", "2020-01-30", "2020-04-04", "2020-04-05",
                "2020-04-06", "2020-05-01", "2020-05-02", "2020-05-03", "2020-05-04",
                "2020-05-05", "2020-06-25", "2020-06-26", "2020-06-27", "2020-10-01",
                "2020-10-02", "2020-10-03", "2020-10-04", "2020-10-05", "2020-10-06",
                "2020-10-07", "2020-10-08"
        };
        String[] T2020 = {//2020国家规定需要调休的班
                "2020-01-19", "2020-02-01", "2020-04-26", "2020-05-09", "2020-06-28",
                "2020-09-27", "2020-10-10"
        };

        //2021国务院办公厅尚未发布，假数据
        String[] legalHolidays2021 = {//2021国家规定的法定节假日
                "2021-01-01", "2021-01-24", "2021-01-25", "2021-01-26", "2021-01-27",
                "2021-01-28", "2021-01-29", "2021-01-30", "2021-04-04", "2021-04-05",
                "2021-04-06", "2021-05-01", "2021-05-02", "2021-05-03", "2021-05-04",
                "2021-05-05", "2021-06-25", "2021-06-26", "2021-06-27", "2021-10-01",
                "2021-10-02", "2021-10-03", "2021-10-04", "2021-10-05", "2021-10-06",
                "2021-10-07", "2021-10-08"
        };
        String[] T2021 = {//2021国家规定需要调休的班
                "2021-01-19", "2021-02-01", "2021-04-26", "2021-05-09", "2021-06-28",
                "2021-09-27", "2021-10-10"
        };

        Map<String, String> map = new HashMap<String, String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String sStartDate = sdf.format(startTime);
        String sEndDate = sdf.format(endTime);

        String[] legalHolidays = {};
        String[] tiaoxiudays = {};

        System.out.println("传入的请假时间:" + sdf2.format(startTime) + " " + sdf2.format(endTime));

        List<Date> lstDays = new ArrayList<Date>();

        Date startD = sdf.parse(sStartDate);
        Date endD = sdf.parse(sEndDate);
        Date newstartD;
        Date newendD;
        Date date = startD;

        Calendar cal = Calendar.getInstance();
        cal.setTime(startD);
        int i = cal.get(Calendar.DAY_OF_WEEK);
        while (date.equals(endD) || date.before(endD)) {
            int iFiled = cal.get(Calendar.DAY_OF_WEEK);
            String strDateString = sdf.format(date);
            String strCurrentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
            String strDateYear = sdf.format(date).split("-")[0];
            legalHolidays = strDateYear.equals(strCurrentYear) ? legalHolidays2020 : legalHolidays2021;
            tiaoxiudays = strDateYear.equals(strCurrentYear) ? T2020 : T2021;


            if ((iFiled == Calendar.SATURDAY || iFiled == Calendar.SUNDAY ||
                    Arrays.asList(legalHolidays).contains(strDateString))
                    && !Arrays.asList(tiaoxiudays).contains(strDateString)) {
                //这里就是做的周六周末和国家法定节假日的过滤
            } else {
                lstDays.add(date);
            }
            cal.add(Calendar.DATE, 1);
            date = cal.getTime();
        }

        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        String sStartTime = "";
        String sEndTime = "";

        if (lstDays.size() > 0) {//除过节假日后的请假天
            newstartD = lstDays.get(0);
            newendD = lstDays.size() == 1 ? lstDays.get(0) : lstDays.get(lstDays.size() - 1);

            //新的开始天和原来的开始天是一样的，那就用原来的请假开始时间  否则就用早上上班时间
            sStartTime = newstartD.equals(startD) ? sdfTime.format(startTime) : goWorkTimeString;
            //新的结束天和原来的结束天是一样的，那就用原来的请假结束时间  否则就用下午下班时间
            sEndTime = newendD.equals(endD) ? sdfTime.format(endTime) : endWorkTimeString;
        } else {
            System.out.println("请假时间属于国家法定假节日或周六周末，不计算");
            map.put("leaveDays", "0");
            return map;
        }

        //判断是否是同一天
        boolean sameDayBoolean = newstartD.equals(newendD);
        double leaveDays = 0.0;
        double leaveHours = 0.0;
        if (sameDayBoolean) {//同一天的情况
            System.out.println("同一天计算方式");
            Integer startInteger = Integer.parseInt(sStartTime.split(":")[0]);
            Integer endInteger = Integer.parseInt(sEndTime.split(":")[0]);

            String resultString = String.valueOf(endInteger - startInteger);
            switch (resultString) {
                //上午
                case "3":
                    leaveHours += 3;
                    break;
                case "5":
                    leaveHours += 5;
                    break;
                case "9":
                    leaveHours += 8;
                    break;
                default:
                    break;
            }
        } else {//不同一天
            Calendar clCalendar = Calendar.getInstance();
            clCalendar.setTime(newstartD);
            clCalendar.add(Calendar.DATE, 1);
            Date newStDate = clCalendar.getTime();
            if (newStDate.equals(newendD)) {//相邻天的情况
                System.out.println("相邻天无间隔计算方式");
                Integer startInteger = Integer.parseInt(sStartTime.split(":")[0]);
                Integer endInteger = Integer.parseInt(sEndTime.split(":")[0]);

                String resultString = String.valueOf(endInteger - startInteger);
                switch (resultString) {
                    case "0":
                        leaveHours += 8;
                        break;
                    case "3":
                        leaveHours += 8 + 3;
                        break;
                    case "5":
                        leaveHours += 8 + 5;
                        break;
                    case "9":
                        leaveHours += 8 + 8;
                        break;
                    default:
                        break;
                }

            } else {//中间有跨天情况
                System.out.println("有间隔计算方式");
                for (int j = 1; j < lstDays.size() - 1; j++) {
                    leaveHours += 8;
                }
                int startInteger = Integer.parseInt(sStartTime.split(":")[0]);

                switch (Integer.toString(startInteger)) {
                    case "9":
                        leaveHours += 8;
                        break;
                    case "13":
                        leaveHours += 5;
                        break;
                    case "18":
                        leaveHours += 0;
                        break;
                    default:
                        break;
                }
                int endInteger = Integer.parseInt(sEndTime.split(":")[0]);
                switch (Integer.toString(endInteger)) {
                    case "8":
                        leaveHours += 0;
                        break;
                    case "12":
                        leaveHours += 3;
                        break;
                    case "18":
                        leaveHours += 8;
                        break;
                    default:
                        break;
                }
            }
        }
        map.put("leaveHours", Double.toString(leaveHours));
        return map;
    }

    public static void main(String[] args) throws Exception {
        LeaveUtils manage = new LeaveUtils();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date start1 = format.parse("2020-12-21 09:00");
        Date end1 = format.parse("2020-12-21 18:00");

        Date start2 = format.parse("2020-10-09 09:00");
        Date end2 = format.parse("2020-10-13 12:00");

        Date start3 = format.parse("2020-09-30 09:00");
        Date end3 = format.parse("2020-10-09 12:00");

        Date start4 = format.parse("2020-12-31 09:00");
        Date end4 = format.parse("2021-01-04 18:00");

        System.out.println("1、正常工作日请假：");
        System.out.println(manage.calcLeaveTime(start1, end1));
        System.out.println("2、带调休请假：");
        System.out.println(manage.calcLeaveTime(start2, end2));
        System.out.println("3、法定节假日请假：");
        System.out.println(manage.calcLeaveTime(start3, end3));
        System.out.println("4、跨年请假：");
        System.out.println(manage.calcLeaveTime(start4, end4));

    }

}

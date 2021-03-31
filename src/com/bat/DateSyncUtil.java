package com.bat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**@Date 2020年10月30日 21:34:46
 * @author zhangyuhang
 */
public class DateSyncUtil {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String formatDate(Date date) throws ParseException {
        synchronized(sdf) {
            return sdf.format(date);
        }  
    }

    public static Date parse(String strDate) throws ParseException {
        synchronized(sdf) {
            return sdf.parse(strDate);
        }
    }
}
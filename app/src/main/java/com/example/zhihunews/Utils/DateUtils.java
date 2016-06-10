package com.example.zhihunews.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Lxq on 2016/5/25.
 */
public class DateUtils {
    public static final String LAST_DATE = "lastDate";

    /**
     * Parse"yyyyMMdd" to "yyyy-MM-dd"
     *
     * @param date "yyyyMMdd" String date
     * @return "yyyy-MM-dd" for display
     */
    public static String getDisplayDate(String date) {
        Date d = parseStandardDate(date);
        return getDisplayDate(d);

    }

    /**
     * Get date String to display
     *
     * @param date Date object
     * @return "yyyy-MM-dd" for display
     */
    public static String getDisplayDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return formatter.format(date);
    }

    /**
     * Parse Date to String
     *
     * @param date Date object
     * @return "yyyyMMdd" String
     */
    public static String parseStandardDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        formatter.setLenient(false);
        return formatter.format(date);
    }

    /**
     * Parse "yyyyMMdd" to Date
     *
     * @param date "yyyyMMdd" String
     * @return Date object
     */
    public static Date parseStandardDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        formatter.setLenient(false);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static Date lastDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);
        return c.getTime();
    }

    public static String lastDay(String date) {
        Date d = DateUtils.parseStandardDate(date);
        return parseStandardDate(lastDay(d));
    }
}

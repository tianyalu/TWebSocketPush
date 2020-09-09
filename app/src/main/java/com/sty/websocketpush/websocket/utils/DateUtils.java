package com.sty.websocketpush.websocket.utils;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.text.format.DateUtils.MINUTE_IN_MILLIS;
import static android.text.format.DateUtils.getRelativeTimeSpanString;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/8 10:02 AM
 */
public class DateUtils {
    public static final String DATE_PARRERN = "yyyy-MM-dd";
    public static final String DATE_TIME_PARRERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_PARRERN_NO_SEPARATOR = "yyMMddHHmmssSSS";

    /**
     * 18/12/16 8:54:42 PM
     * dd/MM/yy HH:mm:ss tt
     * 将时间戳转换为友好时间显示
     */
    public static String toTimeAgo(Context context, long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }
        Calendar ago = Calendar.getInstance();
        ago.setTimeInMillis(time);
        long duration = Math.abs(System.currentTimeMillis() - ago.getTimeInMillis());
        if (duration < MINUTE_IN_MILLIS) {
            return "刚刚";
        }
        return String.valueOf(getRelativeTimeSpanString(ago.getTimeInMillis()));
    }

    public static long changeDate(long date,int addDay)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);
        cal.add(Calendar.DAY_OF_MONTH, addDay);
        return cal.getTimeInMillis();

    }
    public static String getStringDate(Long date, String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);

        return dateString;
    }

    public static String normalDateNow()
    {
        return format(DATE_TIME_PARRERN,new Date());
    }

    public static String millisecondTime(){ return format(DATE_TIME_PARRERN_NO_SEPARATOR,new Date());}

    /**
     * 转换为友好的时间显示
     */
    public static String toTimeAgo(Context context, String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PARRERN);
        // 转换字符串为Date
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            return dateStr;
        }
        Calendar ago = Calendar.getInstance();
        ago.setTime(date);
        long duration = Math.abs(System.currentTimeMillis() - ago.getTimeInMillis());
        if (duration < MINUTE_IN_MILLIS) {
            return "刚刚";
        }
        return String.valueOf(getRelativeTimeSpanString(ago.getTimeInMillis()));
    }

    public static String getDate(String dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PARRERN);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            return dateStr;
        }
        return format(DATE_PARRERN,date);
    }

    public static String format(String pattern, long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        Date date = c.getTime();
        return format(pattern, date);
    }

    public static String format(String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date parse(String pattern, String datetime) {
        try {
            return new SimpleDateFormat(pattern).parse(datetime);
        } catch (ParseException e) {
            return null;
        }
    }
    public static Date parse(String datetime) {
        try {
            return new SimpleDateFormat().parse(datetime);
        } catch (ParseException e) {
            return null;
        }
    }


    public static Date addDay(String dateStr,int days)
    {
        SimpleDateFormat sdf= new SimpleDateFormat(DATE_TIME_PARRERN);
        Calendar calendar;
        Date startDate;
        try {
            startDate=sdf.parse(dateStr);
            calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + days);
            return calendar.getTime();
        }catch (Exception ex)
        {
            Log.e("ex:",ex.getMessage());
        }
        return null;
    }

    public static Date addDay(String pattern,String dateStr,int days)
    {
        SimpleDateFormat sdf= new SimpleDateFormat(pattern);
        Calendar calendar;
        Date startDate;
        try {
            startDate=sdf.parse(dateStr);
            calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + days);
            return calendar.getTime();
        }catch (Exception ex)
        {
            Log.e("ex:",ex.getMessage());
        }
        return null;
    }

    /**
     * 获得昨天的日期字符串
     * @param pattern 解析格式
     * @return
     */
    public static String getYesterdayDateStr(String pattern){

        return getYesterdayDateStr(pattern,new Date());
    }
    /**
     * 获得昨天的日期字符串
     * @param pattern 解析格式
     * @return
     */
    public static String getYesterdayDateStr(String pattern,Date today){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) -1);

        return sdf.format(calendar.getTime());
    }

    public static Date addDay(Date startDate,int days){
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + days);
            return calendar.getTime();
        }catch (Exception ex) {
            Log.e("ex:",ex.getMessage());
        }
        return null;
    }


    public static long getStartTime(String time){
        return parse(DATE_PARRERN,time).getTime()/1000;
    }

    public static long getStartTime(TextView text){
        return getStartTime(text.getText().toString().trim());
    }

    public static long getEndTime(String time){
        return parse(DATE_PARRERN,time).getTime()/1000 + (24*60*60)-1;
    }

    public static long getEndTime(TextView text){
        return getEndTime(text.getText().toString());
    }

    public static boolean isMoreThanDays(String str1 ,String str2, int num) {
        Date date1 = parse(DATE_PARRERN,str1);
        Date date2 = parse(DATE_PARRERN,str2);
        long time = date1.getTime() - date2.getTime();
        if (time >= num * 60 * 60 * 24 * 1000) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param date
     * @return 当前季度
     */

    public static int getQuarter(Date date) {

        int quarter = 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                quarter = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                quarter = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                quarter = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                quarter = 4;
                break;
            default:
                break;
        }
        return quarter;
    }

    /**
     * 判断传入的日期是否是当前年月
     * @param date
     * @return
     */
    public static boolean isCurrentMonth(Date date) {
        Calendar current = Calendar.getInstance();
        Calendar param = Calendar.getInstance();
        current.setTime(new Date());
        param.setTime(date);

        if(current.get(Calendar.YEAR) == param.get(Calendar.YEAR)) {
            if(current.get(Calendar.MONTH) == param.get(Calendar.MONTH)) {
                return true;
            }else {
                return false;
            }
        }

        return false;
    }

    /**
     * 判断传入的日期是否是今天
     * @param date
     * @return
     */
    public static boolean isCurrentDay(Date date) {
        Calendar current = Calendar.getInstance();
        Calendar param = Calendar.getInstance();
        current.setTime(new Date());
        param.setTime(date);

        if(current.get(Calendar.YEAR) == param.get(Calendar.YEAR)) {
            if(current.get(Calendar.MONTH) == param.get(Calendar.MONTH)) {
                if(current.get(Calendar.DATE) == param.get(Calendar.DATE)) {
                    return true;
                }
            }
        }

        return false;
    }
}

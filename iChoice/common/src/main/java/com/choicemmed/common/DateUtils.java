package com.choicemmed.common;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jiangnan on 2019/11/23.
 */
public class DateUtils {
    /**
     * 获取年
     * @return
     */
    public static int getYear(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.YEAR);
    }
    /**
     * 获取月
     * @return
     */
    public static int getMonth(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.MONTH)+1;
    }
    /**
     * 获取日
     * @return
     */
    public static int getDay(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.DATE);
    }
    /**
     * 获取时
     * @return
     */
    public static int getHour(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.HOUR);
    }
    /**
     * 获取分
     * @return
     */
    public static int getMinute(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.MINUTE);
    }

    /**
     * 获取当前时间的时间戳
     * @return
     */
    public static long getCurrentTimeMillis(){
        return System.currentTimeMillis();
    }

    public static void showDatePicker(Context context, DatePickerDialog.OnDateSetListener listener) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(context, listener, 1970, 0, 1);
        DatePicker picker = dialog.getDatePicker();
        picker.setMaxDate(c.getTimeInMillis());
        dialog.show();
    }

    public static void showDatePicker(Context context, DatePickerDialog.OnDateSetListener listener, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(context, listener, year, month - 1, day);
        DatePicker picker = dialog.getDatePicker();
        picker.setMaxDate(c.getTimeInMillis());
        dialog.show();
    }


    /**
     *将yyyy-MM-dd HH:mm:ss 转换为Date
     */
    public static Date strToDate(String strDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static Calendar strToCalendar(String strDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        ParsePosition pos = new ParsePosition(0);
        calendar.setTime(formatter.parse(strDate,pos));
        return calendar;
    }


    /**
     * 判断是不是今天
     * @param calendar
     * @return
     */
    public static boolean isToday(Calendar calendar){
        int Year = calendar.get(Calendar.YEAR);
        int Month = calendar.get(Calendar.MONTH);
        int Day = calendar.get(Calendar.DATE);

        Calendar now = Calendar.getInstance();
        int nYear = now.get(Calendar.YEAR);
        int nMonth = now.get(Calendar.MONTH);
        int nDay = now.get(Calendar.DAY_OF_MONTH);
        if (Year == nYear && Month == nMonth && Day == nDay){
            return true;
        }
        return false;
    }

    public static float decimalFormat(String pattern, double value) {
        return Float.parseFloat(new DecimalFormat(pattern).format(value));
    }

    /**
     * 计算String之间相差多少天
     * @param beginStr
     * @param endStr
     * @return
     */
    public static int differentDays(String beginStr, String endStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int day = 1;
        try {
            Date beginDate = format.parse(beginStr);
            Date endDate = format.parse(endStr);
            long diffSeconds = endDate.getTime() - beginDate.getTime();
            long diffHour = diffSeconds / (1000 * 3600);
            float diffDay = (float) diffHour / 24;
            float floatDay = decimalFormat("0.00", diffDay);
            day = (int)(floatDay + day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    /**
     * 计算两个calendar相差多少天
     * @param beginCalendar
     * @param endCalendar
     * @return
     */
    public  static int differentDayCalendar(Calendar beginCalendar, Calendar endCalendar){
        beginCalendar.set(Calendar.HOUR_OF_DAY,0);
        beginCalendar.set(Calendar.MINUTE,0);
        beginCalendar.set(Calendar.SECOND,0);
        endCalendar.set(Calendar.HOUR_OF_DAY,0);
        endCalendar.set(Calendar.MINUTE,0);
        endCalendar.set(Calendar.SECOND,0);
        long diffSeconds = endCalendar.getTimeInMillis() - beginCalendar.getTimeInMillis();
        long days = diffSeconds/(1000*3600*24);
        return Integer.parseInt(String.valueOf(days));
    }


    public  static int differentHoursCalendar(Calendar beginCalendar, Calendar endCalendar){
        long diffSeconds = endCalendar.getTimeInMillis() - beginCalendar.getTimeInMillis();
        long days = diffSeconds/(1000*3600);
        return Integer.parseInt(String.valueOf(days));
    }


    public static int differentWeekCalendar(Calendar beginCalendar, Calendar endCalendar){
       int dayOffset  = differentDayCalendar(beginCalendar,endCalendar);
        int dayOfWeek = beginCalendar.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = dayOfWeek - 1;
        if (dayOfWeek == 0){
            dayOfWeek = 7;
        }
        int weekOffset = dayOffset / 7;
        int a;
        if (dayOffset > 0) {
            a = (dayOffset % 7 + dayOfWeek > 7) ? 1 : 0;
        } else {
            a = (dayOfWeek + dayOffset % 7 < 1) ? -1 : 0;
        }
        weekOffset = weekOffset + a;
        return weekOffset;
    }

    /**
     * 相差多少月
     * @param startCalendar
     * @param endCalendar
     * @return
     */
    public static int differentMonthCalendar(Calendar startCalendar,Calendar endCalendar){
        int startYear = startCalendar.get(Calendar.YEAR);
        int endYear = endCalendar.get(Calendar.YEAR);
        int startMonth = startCalendar.get(Calendar.MONTH);
        int endMonth = endCalendar.get(Calendar.MONTH);
        int diffMonth = endMonth-startMonth;
        if (diffMonth == 0){
            diffMonth = 1;
        }
        int monthNum = (endYear - startYear)*12+(diffMonth);
        return monthNum;
    }


    public static int differentSecondCalendar(Calendar startCalendar,Calendar endCalendar){
        long difference=endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();
        long seconds = difference / 1000;
        return (int)seconds;
    }



    public static String getDateHour(String beginStr, String endStr) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        Date nowDate = strToDate(beginStr);
        Date endDate = strToDate(endStr);
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        return (day * 24 + hour) + ":" + min;
    }

    /**
     * 时间戳转换成字符串
     * @param milSecond
     * @param pattern
     * @return
     */
    public static String getDateToString(long milSecond, String pattern) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

}

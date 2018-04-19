package com.avit.itdap.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 * @author Andy
 *
 */
public class DateUtil
{
    /**
     * 年月日yyyy-MM-dd格式
     */
    public static final String YYYMMDD = "yyyy-MM-dd";
    
    /**
     * 年月日yyyyMMdd格式
     */
    public static final String _YYYYMMDD = "yyyyMMdd";
    
    /**
     * 年
     */
    public static final String YYYY = "yyyy";
    
    /**
     * 年月
     */
    public static final String YYYYMM = "yyyy-MM";
    
    /**
     * 月日
     */
    public static final String MMDD = "MM-dd";
    
    /**
     * 年月日时分秒 yyyyMMddHHmmss格式
     */
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    
    /**
     * 年月日时分秒 yyyy-MM-dd HH:mm:ss格式
     */
    public static final String  _YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 时分秒HH:mm:ss格式
     */
    public static final String  _HHMMSS = "HH:mm:ss";
    
    /**
     * 时分秒 HHmmss格式
     */
    public static final String  HHMMSS = "HHmmss";
    
    /**
     * 按照指定方式格式化日期
     * @param date 日期
     * @param regex 表达式
     * @return 格式化后日期字符串
     */
    public static String formatDate(Date date,String regex)
    {
        return new SimpleDateFormat(regex).format(date);
    }
    
    /**
     * 按照指定方式格式化日期
     * @param GMT 格林威治时间long秒数
     * @param regex 表达式
     * @return 格式化后日期字符串
     */
    public static String formatDate(Long GMT,String regex)
    {
        return new SimpleDateFormat(regex).format(new Date(GMT));
    }
    
    /**
     * 获取昨天的时间
     * @return 昨天时间
     */
    public static Long getBeforeDate()
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTimeInMillis();
    }
    
    
    /**
     * 返回当前时间yyyy-MM-dd HH:mm:ss 格式
     * @return 当前时间 yyyy-MM-dd HH:mm:ss
     */
    public static String getNow()
    {
        return formatDate(new Date(), _YYYYMMDDHHMMSS);
    }
    
    /**
     * 返回当前时间yyyyMMddHHmmss 格式
     * @return 当前时间 yyyyMMddHHmmss
     */
    public static String getNow2()
    {
        return formatDate(new Date(), YYYYMMDDHHMMSS);
    }
    
    /**
     * 获取 指定年的第一天 例如： 2015-01-01 00:00:00年第一天
     * @return 长整型
     */
    public static Long getYearFirstDayLong(int year)
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, year - c.get(Calendar.YEAR));
        //月份0-11
        c.add(Calendar.MONTH, 0 - c.get(Calendar.MONTH));
        c.add(Calendar.DAY_OF_MONTH, 1 - c.get(Calendar.DAY_OF_MONTH));
        c.add(Calendar.HOUR_OF_DAY, 0 - c.get(Calendar.HOUR_OF_DAY));
        c.add(Calendar.MINUTE, - c.get(Calendar.MINUTE));
        c.add(Calendar.SECOND, - c.get(Calendar.SECOND));
        return c.getTimeInMillis();
    }
    
    /**
     * 获取 指定年的第一天 例如： 2015-01-01 00:00:00年第一天
     * @return 返回日期格式：2015-01-01 00:00:00
     */
    public static String getYearFirstDayStr(int year)
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, year - c.get(Calendar.YEAR));
        //月份0-11
        c.add(Calendar.MONTH, 0 - c.get(Calendar.MONTH));
        c.add(Calendar.DAY_OF_MONTH, 1 - c.get(Calendar.DAY_OF_MONTH));
        c.add(Calendar.HOUR_OF_DAY, 0 - c.get(Calendar.HOUR_OF_DAY));
        c.add(Calendar.MINUTE, - c.get(Calendar.MINUTE));
        c.add(Calendar.SECOND, - c.get(Calendar.SECOND));
        return formatDate(c.getTimeInMillis(), _YYYYMMDDHHMMSS);
    }
    
    /**
     * 获取当前日期是星期几
     * return 星期几
     */
    public static String getWeekDay()
    {
        Calendar c = Calendar.getInstance(Locale.CHINA);   
        c.setTime(new Date());   
        int day=c.get(Calendar.DAY_OF_WEEK);
        String weekDay = "";
        switch (day)
        {
            case 1:
                weekDay = "星期日";
                break;
            case 2:
                weekDay = "星期一";
                break;
            case 3:
                weekDay = "星期二";
                break;
            case 4:
                weekDay = "星期三";
                break;
            case 5:
                weekDay = "星期四";
                break;
            case 6:
                weekDay = "星期五";
                break;
            case 7:
                weekDay = "星期六";
                break;
            default:
                break;
        }
        return weekDay;
    }
    
    /**
     * 获取当前日期的前XX个月
     * @param 之前的第几个月
     * @return 前XX个月字符串
     */
    public static String getBeforeMonth(int beforeMonth)
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -beforeMonth);
        return new SimpleDateFormat("yyyy-MM").format(c.getTime());
    }
    
    /**
     * 使用参数Format将字符串转为Date
     * @author andy
     * @param strDate
     * @param pattern
     * @return
     * @throws ParseException 
     */
    public static Date parse(String strDate, String pattern)throws ParseException
    {
        return new SimpleDateFormat(pattern).parse(strDate);
    }
    
    public static void main(String[] args) throws Exception
    {
//        System.out.println(getYearFirstDayStr(2014));
//        System.out.println(getNow());
//        System.out.println(getNow2());
    	System.out.println(DateUtil.parse("20170828002000", DateUtil.YYYYMMDDHHMMSS));
    }
}

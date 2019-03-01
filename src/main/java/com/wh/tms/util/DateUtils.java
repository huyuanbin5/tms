package com.wh.tms.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtils {
	
private static final Log log = LogFactory.getLog(DateUtils.class);
	
	public static final long TIME_CARRY = 60; /*时间进位制*/
	public static final long SECOND_MILLIS = 1000; /*一秒的毫秒数*/
	public static final long MINUTE_MILLIS = SECOND_MILLIS * 60; /*一分钟的毫秒数*/
	public static final long HOUR_MILLIS = MINUTE_MILLIS * 60;  /*一个小时的毫秒数*/
	public static final long DAY_MILLIS = HOUR_MILLIS * 24; /*一天的毫秒数*/
	
	/**
	 * 字符串转时间，转失败返回默认
	 * @param dateString
	 * @param pattern
	 * @param defaultValue
	 * @return
	 */
	public static Date parse(String dateString, String pattern, Date defaultValue){
		if(StringUtils.isEmpty(dateString) || StringUtils.isEmpty(pattern)){
			return defaultValue;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date date = sdf.parse(dateString);
			return date;
		} catch (Exception e) {
			log.error("parse date error", e);
		}
		return defaultValue;
	}
	
	
	/**
	 * getWeekTime
	 * @param flag: 0 本周 1下周 n下n周
	 * @return
	 */
	public static Date getWeekTime(int flag){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.WEEK_OF_YEAR, flag);
		calendar.set(Calendar.MILLISECOND,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		return new Date(calendar.getTimeInMillis());
	}
	
	public static int getDayOfWeek(){
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek =  calendar.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek==1) {
			return 7;
		}
		else return dayOfWeek-1;
	}
	
	public static boolean withinPeriod(long bTime,long eTime){	
		return withinPeriod(System.currentTimeMillis(), bTime, eTime);
	}
	
	public static boolean withinPeriod(long nowTime, long bTime,long eTime){	
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		long beginTime = calendar.getTimeInMillis() + bTime;
		long endTime = calendar.getTimeInMillis() + eTime;
		if (nowTime >= beginTime && nowTime <= endTime) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String format(long time, String format) {
		if (format == null) {
			format = "MM-dd";
		}
		SimpleDateFormat f = new SimpleDateFormat(format);
		return f.format(new Date(time)).toString();
	}
	
	public static String format(long time) {		
		return format(time,null);
	}
	
	public static String format2(long time) {
		return format(time, "MM月dd日");
	}
	
	/**
	 * 获得当天零时零分零秒 的毫秒数
	 * 
	 * @return long
	 */
	public static long getDailyTime(){
		Calendar cal = Calendar.getInstance();       
		cal.set(Calendar.HOUR_OF_DAY, 0);       
		cal.set(Calendar.SECOND, 0);    
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);	
		return cal.getTimeInMillis();
	}
	
	/**
	 * 获得当时零分零秒 的毫秒数
	 * 
	 * @return long
	 */
	public static long getHourlyTime(long time) {
		return getHourlyTime(time, 0);
	}
	
	/**
	 * 获得当前时间的 小时的数值
	 * @return int
	 */
	public static int getHour(long time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	/**
	 * 获得当前时间的 分钟数
	 * @return int
	 */
	public static int getMinute(long time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return calendar.get(Calendar.MINUTE);
	}
	/**
	 * 获得当前时间是这个月的几号
	 * @return int
	 */
	public static int getDayOfMonth(long time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	/**
	 * 获得当前时间是周几？ SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, and SATURDAY
	 * @return int
	 */
	public static int getDayOfWeek(long time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	/**
	 * 获得此刻前N个或后N个小时零分零秒 的毫秒数
	 * 
	 * @return long
	 */
	public static long getHourlyTime(long time, int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		int hour = calendar.get(Calendar.HOUR_OF_DAY) + n;
		calendar.set(Calendar.HOUR_OF_DAY,  hour);

		return calendar.getTimeInMillis();
	}
	
	public static long getDailyTime(long time, int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		int day = calendar.get(Calendar.DAY_OF_MONTH) + n;
		calendar.set(Calendar.DAY_OF_MONTH,  day);

		return calendar.getTimeInMillis();
	}
	
	public static long getDailyTime(long time){
		return getDailyTime(time,0);
	}
	
	/* 格式化时间，从毫秒->*/
	public static String formatTime(long time){
		if ( time <= 0) {
			return  "0:0:0";
		}
		time = time / 1000;
	    int h = (int)Math.floor(time / 3600);
	    int m = (int)Math.floor((time - h * 3600) / 60);
	    int s = (int)time - h*3600 - m*60 ;
	    String fmt = h + ":" + m + ":" + s; 
	    return fmt;
	}
	
	/**
	 * 判断日期是否在今天之前
	 * @param endTime
	 * @return 
	 */
	public static boolean isBeforeToday(long endTime){
		return endTime < getDailyTime(System.currentTimeMillis(),0);
	}
	/**
	 * 获取前一天的时间
	 * @return
	 */
	public static Date getBeforeDay(){
		 Calendar calendar = Calendar.getInstance();
		 int day = calendar.get(Calendar.DATE);
		 calendar.set(Calendar.DATE, day-1);
		 return calendar.getTime();
	}
	/**
	 * 判断是否是0时0分0秒
	 * @param time
	 * @return
	 */
	public static boolean isDailyTime(long time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return calendar.get(Calendar.HOUR)==0 && calendar.get(Calendar.MINUTE)==0 && calendar.get(Calendar.SECOND)==0;
	}
	
	/**
	 * 判断是否在同一天
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static boolean isInOneDay(long beginTime,long endTime){
		return   endTime == beginTime;
	}
	
	/**
	 * 判断是否是今天
	 * @return
	 */
	public static boolean isToday(long beginTime,long endTime){
		return isTodayBegin(beginTime) && isTodayEnd(endTime);
	}
	
	/**
	 * 判断是否为一天开始时间
	 * @param beginCa
	 * @return
	 */
	public static boolean isTodayBegin(long time) {
		return time == getDailyTime();
	}

	/**
	 * 判断是否为一天结束时间
	 * @param endCa
	 * @return
	 */
	public static boolean isTodayEnd(long time) {
		return time == getDailyTime();//getDailyTime(System.currentTimeMillis(),1);
	}
	
	/**
	 * 获得当日0时0分0秒
	 * @param time
	 * @return Calendar
	 */
	public static Calendar getAdjustedCalendar(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
	
	/**
	 * 计算总共经历了多少天.如1.1-1.2结果为2天.
	 * @param fDate
	 * @param eDate
	 * @return
	 */
	public static long getDisDays(Date fDate, Date eDate){
		Calendar fc = Calendar.getInstance();
		fc.setTime(fDate);
		fc.set(Calendar.HOUR_OF_DAY, 0);
		fc.set(Calendar.MINUTE, 0);
		fc.set(Calendar.SECOND, 0);
		fc.set(Calendar.MILLISECOND, 0);
		//System.out.println(fc.getTime());
		
		Calendar ec = Calendar.getInstance();
		ec.setTime(eDate);
		ec.set(Calendar.HOUR_OF_DAY, 0);
		ec.set(Calendar.MINUTE, 0);
		ec.set(Calendar.SECOND, 0);
		ec.set(Calendar.MILLISECOND, 0);
		//System.out.println(ec.getTime());
		
		long days = (ec.getTimeInMillis() - fc.getTimeInMillis())/(24 * 60 * 60 * 1000);
		return days < 0 ? -1 : days + 1;
	}
	/**
	 * 获取当前日期 格式: yyyy-M-d
	 * 
	 * @return
	 */
	public static String getDate() {
		Date date = new Date();
		String formater = "yyyy-MM-dd"; // yyyy-M-d
		SimpleDateFormat format = new SimpleDateFormat(formater);
		return format.format(date);
	}

	/**
	 * 获取当前日期 格式:参数 例如 yyyy-MM-dd
	 * 
	 * @param formatStr
	 * @return
	 */
	public static String getDate(String formatStr) {
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat(formatStr);
		// TimeZone zone = new SimpleTimeZone(28800000, "Asia/Shanghai");
		// df.setTimeZone(zone);
		return df.format(c.getTime());
	}

	/**
	 * 格式化指定日期 格式:参数 例如 yyyy-MM-dd
	 * 
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String getDate(Date date, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr==null?"yyyy-MM-dd":formatStr);// yyyy-MM-dd
		//
		return date == null ? "" : format.format(date);
	}

	public static String getDate(String dateTime,String formatStr){
		if(StringUtils.isEmpty(dateTime)) return null;
		int len = dateTime.length();
		if(len == 7){
			dateTime += "-01";
		}
		return getDate(getDateTime(dateTime,"yyyy-MM-dd"),formatStr);
	}
	/**
	 * 获取当前日期时间 格式: yyyy-MM-dd
	 * 
	 * @return
	 */
	public static Date getDateTime() {
		return getDateTime(getDate("yyyy-MM-dd"));
	}

	/**
	 * 获取当前日期时间 格式: yyyy-MM-dd
	 * 
	 * @param dtime
	 * @return Date
	 */
	public static Date getDateTime(String dtime) {
		try {
			DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			return format1.parse(dtime);
		} catch ( ParseException e ) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前日期时间 格式:参数 例如 yyyy-MM-dd
	 * 
	 * @param dtime
	 * @param formatStr
	 * @return
	 */
	public static Date getDateTime(String dtime, String formatStr) {
		try {
			DateFormat format1 = new SimpleDateFormat(formatStr);
			return format1.parse(dtime);
		} catch ( ParseException e ) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 格式化时间 格式: yyyy-MM-dd
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDate(String date) {
		String formater = "yyyy-MM-dd";
		SimpleDateFormat format = new SimpleDateFormat(formater);
		return date == null ? null : format.format(getDateTime(date));
	}

	/**
	 * 格式化时间 格式:第2参数 例如 yyyy-MM-dd
	 * 
	 * @param date
	 * @param formatStr
	 * @return String
	 */
	public static String formatDate(String date, String formatStr) {
		String formater = formatStr;
		SimpleDateFormat format = new SimpleDateFormat(formater);
		return (date == null || date.length() == 0) ? null : format.format(getDateTime(date));
	}

	/**
	 * 取得某一特定的日期
	 * 
	 * @param sDate
	 * @param iDay
	 * @param sformat
	 * @return
	 */
	public static String getSomeDate(String sDate, int iDay, String sformat) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(sformat);
			Date date = format.parse(sDate);
			long Time = (date.getTime() / 1000) + 60 * 60 * 24 * iDay;
			date.setTime(Time * 1000);
			return format.format(date);
		} catch ( Exception ex ) {
			return "";
		}
	}

	/**
	 * 时间间隔 是否在当前时间范围
	 * 
	 * @param btime
	 * @param etime
	 * @return
	 */
	public static boolean dateBound(String btime, String etime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date sd = formatter.parse(btime, new ParsePosition(0));
		Date ed = formatter.parse(etime, new ParsePosition(0));
		return dateBound(sd, ed);
	}

	public static boolean dateBound(Date sd, Date ed) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date td = formatter.parse(getDate(), new ParsePosition(0)); // 当前时间
		if (sd == null || ed == null || td == null) return false;
		if (sd.getTime() > td.getTime() || ed.getTime() < td.getTime()) return false;
		return true;
	}
	
	/**
	 * 显示当前时间与目标时间的距离
	 * 
	 * **秒前  ** 分钟前  ** 小时前 超过7天显示时间
	 * @param ctime
	 * @param format
	 * @return
	 */
	public static String distance(Date ctime, String format) {
		if (ctime == null) return "";
		format = format==null?"yyyy-MM-dd HH:mm":format;
		long result = Math.abs(System.currentTimeMillis() - ctime.getTime());
		if (result < 60000) {
			long s = (result / 1000);
			return s < 60 ? "刚刚" : s+"秒钟前";
		} else if (result >= 60000 && result < 3600000) {
			return (result / 60000) + "分钟前";
		} else if (result >= 3600000 && result < 86400000) {
			return (result / 3600000) + "小时前";
		} else if (result >= 86400000 && result < 86400000 * 7) {
			return (result / 86400000) + "天前";
		} else {
			return getDate(ctime, format);
		}
	}
	public static String getDistanceTime(long diff) {  
	    if(diff<0){
	        return null;
	    }
        long day = diff / (24 * 60 * 60 * 1000);  
        long hour = (diff / (60 * 60 * 1000) - day * 24);  
        long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);  
        long sec = (diff/1000-day*24*60*60-hour*60*60-min*60);  
        String time = "";
        if(day != 0) {
        	time += day + "天";
        }
        if(hour != 0){
        	time += hour + "小时";
        }
        if(min != 0){
        	time += min + "分";
        }
        if(sec != 0) {
        	time += sec + "秒";
        }
        return time;  
    }
	/**
	 * 获取两个日期之间相差的天数
	 * @param date 当前时间
	 * @param nowDate
	 * @return
	 */
	public static long getDatePoor(Date date, Date nowDate) {
	    long nd = 1000 * 24 * 60 * 60;
	    long diff = date.getTime() - nowDate.getTime();
	    long day = diff / nd;
	    return day;
	}
	/**
	 * 两个日期相差的分钟
	 * @param endDate
	 * @param nowDate
	 * @return
	 */
	public static long getMinPoor(Date endDate, Date nowDate) {
	    if (null == endDate || null == nowDate) {
	    	return 0;
	    }
		long diff = endDate.getTime() - nowDate.getTime();
	    long min = diff / (1000 * 60);
	    return min;
	}
	/**
	 * 两个日期之间所有的日期
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<Date> getDatesBetweenTwoDate(Date startDate, Date endDate) { 
		List<Date> dates = new ArrayList<Date>();
		if (null == startDate || null == endDate) {
			return dates;
		}
	    
	    dates.add(startDate);
	    String stime = getDate(startDate, "yyyy-MM-dd");
	    String etime = getDate(endDate, "yyyy-MM-dd");
	    
	    if (StringUtils.isNotBlank(stime) && StringUtils.isNotBlank(etime) && stime.equals(etime)) {
	    	return dates;
	    }
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(startDate);
	    boolean bContinue = true;
	    while (bContinue) {
	        cal.add(Calendar.DAY_OF_MONTH, 1);
	        if (endDate.after(cal.getTime())) {
	        	dates.add(cal.getTime());
	        } else {
	            break;
	        }
	    }
	    dates.add(endDate);//把结束时间加入集合
	    return dates;
	}
	  /** 
     * 获取某年第一天日期 
     * @param year 年份 
     * @return Date 
     */  
    public static Date getYearFirst(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        Date currYearFirst = calendar.getTime();  
        return getDateTime(getDate(currYearFirst, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");  
    }
	/** 
     * 获取某年最后一天日期 
     * @param year 年份 
     * @return Date 
     */  
    public static Date getYearLast(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        calendar.roll(Calendar.DAY_OF_YEAR, -1);  
        Date currYearLast = calendar.getTime();  
        return getDateTime(getDate(currYearLast, "yyyy-MM-dd 23:59:59"), "yyyy-MM-dd HH:mm:ss");  
    }  
    
    /**
     * 获取指定月份之前或之后的日期
     * @param month
     * @return
     */
    public static Date getBeforeMonth(int month) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(new Date());
    	if (month != 0) {
    		c.add(Calendar.MONTH, month);
    	}
        return c.getTime();
    }
    /**
     * 获取指定天数之前或之后的日期
     * @param day
     * @return
     */
    public static Date getBeforeDate(int day) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(new Date());
    	if (day != 0) {
    		c.add(Calendar.DATE, day);
    	}
        return c.getTime();
    }
    public static String getFormatTime(Date date){
    	Calendar calendar = Calendar.getInstance();
    	// 当前年  
        int year = calendar.get(Calendar.YEAR);
    	calendar.setTime(date);
    	if(year == calendar.get(Calendar.YEAR)){
    		return DateUtils.getDate(calendar.getTime(), "MM-dd HH:mm");
    	}
    	return DateUtils.getDate(calendar.getTime(), "yyyy-MM-dd HH:mm");
    }

}

package com.guaboy.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author zhousir
 * @since 1.0.0-RELEASE
 * 
 */
public class DateUtil {
	public final static String format1 = "yyyy-MM-dd HH:mm:ss";
	public final static String format2 = "yyyy-MM-dd HH:mm:ss.SSS";
	public final static String format3 = "yyyy/MM/dd HH:mm:ss";
	public final static String format4 = "yyyy/MM/dd HH:mm:ss.SSS";
	public final static String format5 = "yyyyMMdd HH:mm:ss.SSS";
	public final static String format6 = "yyyyMMdd HH:mm:ss.SSS";

	
	/**
	 * 根据日期字符串，匹配出对应的格式化模式<br>
	 * 支持以 - 和 / 分割的年月日<br>
	 * 
	 * @param date 例如: 2016-08-16 10:23:38.138 返回 yyyy-MM-dd HH:mm:ss.SSS
	 * @return
	 */
	public static String matchesPattern(String date) {
		String pattern = "";
		if (date.matches("\\d{4}\\-\\d{1,2}\\-\\d{1,2}\\s+\\d{1,2}\\:\\d{1,2}\\:\\d{1,2}\\.\\d{1,3}")) {
			pattern = "yyyy-MM-dd HH:mm:ss.SSS";
		} else if (date.matches("\\d{4}\\-\\d{1,2}\\-\\d{1,2}\\s+\\d{1,2}\\:\\d{1,2}\\:\\d{1,2}")) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		} else if (date.matches("\\d{4}\\-\\d{1,2}\\-\\d{1,2}\\s+\\d{1,2}\\:\\d{1,2}")) {
			pattern = "yyyy-MM-dd HH:mm";
		} else if (date.matches("\\d{4}\\-\\d{1,2}\\-\\d{1,2}\\s+\\d{1,2}")) {
			pattern = "yyyy-MM-dd HH";
		} else if (date.matches("\\d{4}\\-\\d{1,2}\\-\\d{1,2}")) {
			pattern = "yyyy-MM-dd";
		} else if (date.matches("\\d{4}\\/\\d{1,2}\\/\\d{1,2}\\s+\\d{1,2}\\:\\d{1,2}\\:\\d{1,2}\\.\\d{1,3}")) {
			pattern = "yyyy/MM/dd HH:mm:ss.SSS";
		} else if (date.matches("\\d{4}\\/\\d{1,2}\\/\\d{1,2}\\s+\\d{1,2}\\:\\d{1,2}\\:\\d{1,2}")) {
			pattern = "yyyy/MM/dd HH:mm:ss";
		} else if (date.matches("\\d{4}\\/\\d{1,2}\\/\\d{1,2}\\s+\\d{1,2}\\:\\d{1,2}")) {
			pattern = "yyyy/MM/dd HH:mm";
		} else if (date.matches("\\d{4}\\/\\d{1,2}\\/\\d{1,2}\\s+\\d{1,2}")) {
			pattern = "yyyy/MM/dd HH";
		} else if (date.matches("\\d{4}\\/\\d{1,2}\\/\\d{1,2}")) {
			pattern = "yyyy/MM/dd";
		}
		return pattern;
	}
	
	
	/**
	 * 格式化时间戳
	 * 
	 * @param date 时间戳
	 * @param pattern 格式化模式
	 * 
	 * @return String
	 */
	public static String format(long time, String pattern) {
		return format(new Date(time), pattern);
	}

	
	/**
	 * 格式化日期
	 * 
	 * @param date 日期
	 * @param pattern 格式化模式
	 * 
	 * @return String
	 */
	public static String format(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String dateStr = sdf.format(date);
		return dateStr;
	}
	
	
	/**
	 * 将字符串的时间转换成时间戳,无效的格式则返回O<br>
	 * 支持格式:<br>
	 * yyyy-MM-dd HH:mm:ss | yyyy-MM-dd HH:mm:ss.SSS<br>
	 * yyyy/MM/dd HH:mm:ss | yyyy/MM/dd HH:mm:ss.SSS<br>
	 * 
	 * @param date 例如:2016-08-16 12:00:00
	 * 
	 * @return 
	 */
	public static long getTimeInMillis(String date) {
		String pattern = matchesPattern(date);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		long time = 0L;
		try {
			time = sdf.parse(date).getTime();
		} catch (ParseException e) {
		}
		return time;
	}
	
	
	/**
	 * 根据参考日期获取前后几天的时间戳
	 * 
	 * @param referDate 参考日期
	 * @param days 正数向后推、负数向前推
	 * @param hour 时 0-23
	 * @param minute 分 0-59
	 * @param second 秒 0-59
	 * 
	 * @return 
	 */
	public static long getTimeInMillis(Date referDate, int days, int hour, int minute, int second) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(referDate);
		cl.set(Calendar.HOUR_OF_DAY, hour);
		cl.set(Calendar.MINUTE, minute);
		cl.set(Calendar.SECOND, second);
		cl.set(Calendar.MILLISECOND, 0);
		cl.add(Calendar.DAY_OF_MONTH, days);
		
		return cl.getTimeInMillis();
	}

	
	/**
	 * 根据参考日期获取所在周星期一的时间戳
	 * 
	 * @param referDate 参考日期
	 * @param hour 时 0-23
	 * @param minute 分 0-59
	 * @param second 秒 0-59
	 * 
	 * @return 
	 */
	public static long getFirstDayOfWeek(Date referDate, int hour, int minute, int second) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(referDate);
		cl.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cl.set(Calendar.HOUR_OF_DAY, hour);
		cl.set(Calendar.MINUTE, minute);
		cl.set(Calendar.SECOND, second);
		cl.set(Calendar.MILLISECOND, 0);
		return cl.getTimeInMillis();
	}
	
	
	/**
	 * 根据参考日期获取所在周星期天的时间戳
	 * 
	 * @param referDate 参考日期
	 * @param hour 时 0-23
	 * @param minute 分 0-59
	 * @param second 秒 0-59
	 * 
	 * @return 
	 */
	public static long getLastDayOfWeek(Date referDate, int hour, int minute, int second) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(referDate);
		cl.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		cl.set(Calendar.HOUR_OF_DAY, hour);
		cl.set(Calendar.MINUTE, minute);
		cl.set(Calendar.SECOND, second);
		cl.set(Calendar.MILLISECOND, 0);
		
		cl.add(Calendar.DAY_OF_WEEK, 1);
		return cl.getTimeInMillis();
	}
	
	
	/**
	 * 根据参考日期获取所在月第一天的时间戳
	 * 
	 * @param referDate 参考日期
	 * @param hour 时 0-23
	 * @param minute 分 0-59
	 * @param second 秒 0-59
	 * 
	 * @return 
	 */
	public static long getFirstDayOfMonth(Date referDate, int hour, int minute, int second) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(referDate);
		cl.set(Calendar.DAY_OF_MONTH, 1);
		cl.set(Calendar.HOUR_OF_DAY, hour);
		cl.set(Calendar.MINUTE, minute);
		cl.set(Calendar.SECOND, second);
		cl.set(Calendar.MILLISECOND, 0);
		
		return cl.getTimeInMillis();
	}
	
	
	/**
	 * 根据参考日期获取所在月最后一天的时间戳
	 * 
	 * @param referDate 参考日期
	 * @param hour 时 0-23
	 * @param minute 分 0-59
	 * @param second 秒 0-59
	 * 
	 * @return 
	 */
	public static long getLastDayOfMonth(Date referDate, int hour, int minute, int second) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(referDate);
		cl.set(Calendar.DAY_OF_MONTH, 1);
		cl.set(Calendar.HOUR_OF_DAY, hour);
		cl.set(Calendar.MINUTE, minute);
		cl.set(Calendar.SECOND, second);
		cl.set(Calendar.MILLISECOND, 0);
		
		cl.add(Calendar.MONTH, 1);
		cl.add(Calendar.DAY_OF_MONTH, -1);
		
		return cl.getTimeInMillis();
	}
	
	
	/**
	 * 根据参考日期获取所在年第一天的时间戳
	 * 
	 * @param referDate 参考日期
	 * @param hour 时 0-23
	 * @param minute 分 0-59
	 * @param second 秒 0-59
	 * 
	 * @return 
	 */
	public static long getFirstDayOfYear(Date referDate, int hour, int minute, int second) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(referDate);
		cl.set(Calendar.DAY_OF_YEAR, 1);
		cl.set(Calendar.HOUR_OF_DAY, hour);
		cl.set(Calendar.MINUTE, minute);
		cl.set(Calendar.SECOND, second);
		cl.set(Calendar.MILLISECOND, 0);
		
		return cl.getTimeInMillis();
	}
	
	
	/**
	 * 根据参考日期获取所在年最后一天的时间戳
	 * 
	 * @param referDate 参考日期
	 * @param hour 时 0-23
	 * @param minute 分 0-59
	 * @param second 秒 0-59
	 * 
	 * @return 
	 */
	public static long getLastDayOfYear(Date referDate, int hour, int minute, int second) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(referDate);
		cl.set(Calendar.MONTH, 11);
		cl.set(Calendar.DAY_OF_MONTH, 31);
		cl.set(Calendar.HOUR_OF_DAY, hour);
		cl.set(Calendar.MINUTE, minute);
		cl.set(Calendar.SECOND, second);
		cl.set(Calendar.MILLISECOND, 0);
		
		return cl.getTimeInMillis();
	}
	
}

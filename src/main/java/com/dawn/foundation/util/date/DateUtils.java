package com.dawn.foundation.util.date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;

/**
 * 已测试，
 * 方法功能注释可参考SPDateTimeUitls
 */
public class DateUtils {
    
    public static String format(Date date, String format) {
    	return DateTimeUtils.format(DateTimeUtils.toDateTime(date), format);
    }
/*    public static Date parse(String time, String format) {
        return DateTimeUtils.toDate(DateTimeUtils.parse(time, format));
    }*/
    public static Date parse(String time, String format) {
		if(StringUtils.isBlank(time)){
			return null;
		}
        return DateTimeFormat.forPattern(format).parseLocalDateTime(time).toDate();
    }
    public static Date parseDateDefault(String time){
    	return parse(time, DateFormatTemplate.DATE_FORMAT_DEFAULT);
    }
    public static Date parseDateTimeDefault(String time){
    	return parse(time, DateFormatTemplate.DATE_TIME_FORMAT_DEFAULT);
    }
    public static Date parseDateTimeISO8601(String time){
    	return parse(time, DateFormatTemplate.DATE_TIME_FORMAT_ISO8601);
    }
    public static String formatDateDefault(Date date){
    	return format(date,DateFormatTemplate.DATE_FORMAT_DEFAULT);
    }
    public static String formatDateTimeDefault(Date date){
    	return format(date,DateFormatTemplate.DATE_TIME_FORMAT_DEFAULT);
    }
    public static String formatDateTimeISO8601(Date date){
    	return format(date,DateFormatTemplate.DATE_TIME_FORMAT_ISO8601);
    }
    public static Date ignoreTime(Date date){
    	return parseDateDefault(formatDateDefault(date));
    }
    public static Date addSeconds(Date date, int num){
    	return DateTimeUtils.toDate(
    		DateTimeUtils.addSeconds(DateTimeUtils.toDateTime(date), num));
    }
    public static Date addMinutes(Date date, int num){
    	return DateTimeUtils.toDate(
    		DateTimeUtils.addMinutes(DateTimeUtils.toDateTime(date), num));
    }
    public static Date addHours(Date date, int num){
    	return DateTimeUtils.toDate(
    		DateTimeUtils.addHours(DateTimeUtils.toDateTime(date), num));
    }
    public static Date addDays(Date date, int num){
    	return DateTimeUtils.toDate(
    		DateTimeUtils.addDays(DateTimeUtils.toDateTime(date), num));
    }
    public static Date addMonths(Date date, int num){
    	return DateTimeUtils.toDate(
    		DateTimeUtils.addMonths(DateTimeUtils.toDateTime(date), num));
    }
    public static Date addYears(Date date, int num){
    	return DateTimeUtils.toDate(
    		DateTimeUtils.addYears(DateTimeUtils.toDateTime(date), num));
    }
    public static int durationYear(Date startTime, Date endTime){
    	return DateTimeUtils.durationYear(
    			DateTimeUtils.toDateTime(startTime),
    			DateTimeUtils.toDateTime(endTime));
    }
    public static int durationMonth(Date startTime, Date endTime){
    	return DateTimeUtils.durationMonth(
    			DateTimeUtils.toDateTime(startTime),
    			DateTimeUtils.toDateTime(endTime));
    }
    public static int durationDay(Date startTime, Date endTime){
    	return DateTimeUtils.durationDay(
    			DateTimeUtils.toDateTime(startTime),
    			DateTimeUtils.toDateTime(endTime));
    }
	public static int durationHour(Date startTime, Date endTime){
		return DateTimeUtils.durationHour(
    			DateTimeUtils.toDateTime(startTime),
    			DateTimeUtils.toDateTime(endTime));
	}
	public static int durationMinute(Date startTime, Date endTime){
		return DateTimeUtils.durationMinute(
    			DateTimeUtils.toDateTime(startTime),
    			DateTimeUtils.toDateTime(endTime));
	}
	public static int durationSecond(Date startTime, Date endTime){
		return DateTimeUtils.durationSecond(
    			DateTimeUtils.toDateTime(startTime),
    			DateTimeUtils.toDateTime(endTime));
	}

	/**
	 * 获取T+1天（0点）
	 * @param baseDate
	 * @return
	 */
	public static Date getNextDay(Date baseDate){
		Calendar nextDay = Calendar.getInstance();

		nextDay.setTime(baseDate);
		nextDay.add(Calendar.DAY_OF_YEAR, 1);
		nextDay.set(Calendar.HOUR_OF_DAY, 0);
		nextDay.set(Calendar.MINUTE, 0);
		nextDay.set(Calendar.SECOND, 0);
		nextDay.set(Calendar.MILLISECOND, 0);

		return nextDay.getTime();
	}

	@Deprecated
    public static Date plusNumToTime(Date date, int num, DateFieldType fieldType){
    	return DateTimeUtils.toDate(DateTimeUtils.plusNumToTime(DateTimeUtils.toDateTime(date), num, fieldType));
    }
	@Deprecated
    public static Date minusNumToTime(Date date, int num, DateFieldType fieldType){
    	return DateTimeUtils.toDate(DateTimeUtils.minusNumToTime(DateTimeUtils.toDateTime(date), num, fieldType));
    }

}

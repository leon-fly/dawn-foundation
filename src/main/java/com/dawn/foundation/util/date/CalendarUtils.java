package com.dawn.foundation.util.date;

import java.util.Calendar;
import java.util.Date;

/**
 * 已测试，
 * 方法功能注释可参考SPDateTimeUitls
 *
 *
 */
public class CalendarUtils {
    
	public static String format(Calendar date, String format) {
    	return DateTimeUtils.format(DateTimeUtils.toDateTime(date), format);
    }
    public static Calendar parse(String time, String format) {
        return DateTimeUtils.toCalendar(DateTimeUtils.parse(time, format));
    }
    public static Calendar parseDateDefault(String time){
    	return parse(time, DateFormatTemplate.DATE_FORMAT_DEFAULT);
    }
    public static Calendar parseDateTimeDefault(String time){
    	return parse(time, DateFormatTemplate.DATE_TIME_FORMAT_DEFAULT);
    }
    public static Calendar parseDateTimeISO8601(String time){
    	return parse(time, DateFormatTemplate.DATE_TIME_FORMAT_ISO8601);
    }
    public static String formatDateDefault(Calendar date){
    	return format(date,DateFormatTemplate.DATE_FORMAT_DEFAULT);
    }
    public static String formatDateTimeDefault(Calendar date){
    	return format(date,DateFormatTemplate.DATE_TIME_FORMAT_DEFAULT);
    }
    public static String formatDateTimeISO8601(Calendar date){
    	return format(date,DateFormatTemplate.DATE_TIME_FORMAT_ISO8601);
    }
    public static Calendar addSeconds(Calendar date, int num){
    	return DateTimeUtils.toCalendar(
    		DateTimeUtils.addSeconds(DateTimeUtils.toDateTime(date), num));
    }
    public static Calendar addMinutes(Calendar date, int num){
    	return DateTimeUtils.toCalendar(
    		DateTimeUtils.addMinutes(DateTimeUtils.toDateTime(date), num));
    }
    public static Calendar addHours(Calendar date, int num){
    	return DateTimeUtils.toCalendar(
    		DateTimeUtils.addHours(DateTimeUtils.toDateTime(date), num));
    }
    public static Calendar addDays(Calendar date, int num){
    	return DateTimeUtils.toCalendar(
    		DateTimeUtils.addDays(DateTimeUtils.toDateTime(date), num));
    }
    public static Calendar addMonths(Calendar date, int num){
    	return DateTimeUtils.toCalendar(
    		DateTimeUtils.addMonths(DateTimeUtils.toDateTime(date), num));
    }
    public static Calendar addYears(Calendar date, int num){
    	return DateTimeUtils.toCalendar(
    		DateTimeUtils.addYears(DateTimeUtils.toDateTime(date), num));
    }
    public static int durationYear(Calendar startTime, Calendar endTime){
    	return DateTimeUtils.durationYear(
    			DateTimeUtils.toDateTime(startTime),
    			DateTimeUtils.toDateTime(endTime));
    }
    public static int durationMonth(Calendar startTime, Calendar endTime){
    	return DateTimeUtils.durationMonth(
    			DateTimeUtils.toDateTime(startTime),
    			DateTimeUtils.toDateTime(endTime));
    }
    public static int durationDay(Calendar startTime, Calendar endTime){
    	return DateTimeUtils.durationDay(
    			DateTimeUtils.toDateTime(startTime),
    			DateTimeUtils.toDateTime(endTime));
    }
	public static int durationHour(Calendar startTime, Calendar endTime){
		return DateTimeUtils.durationHour(
    			DateTimeUtils.toDateTime(startTime),
    			DateTimeUtils.toDateTime(endTime));
	}
	public static int durationMinute(Calendar startTime, Calendar endTime){
		return DateTimeUtils.durationMinute(
    			DateTimeUtils.toDateTime(startTime),
    			DateTimeUtils.toDateTime(endTime));
	}
	public static int durationSecond(Calendar startTime, Calendar endTime){
		return DateTimeUtils.durationSecond(
    			DateTimeUtils.toDateTime(startTime),
    			DateTimeUtils.toDateTime(endTime));
	}
	
	public static int getAge(Date dateOfBirth) {
		Calendar dob = Calendar.getInstance();
		dob.setTime(dateOfBirth);  
		Calendar today = Calendar.getInstance();
		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
		if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
		  age--;  
		} else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
		    && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
		  age--;  
		}
		
		return age;
	}
    
	@Deprecated
    public static Calendar plusNumToTime(Calendar date, int num, DateFieldType fieldType){
    	return DateTimeUtils.toCalendar(DateTimeUtils.plusNumToTime(DateTimeUtils.toDateTime(date), num, fieldType));
    }

	public static Date getStartOfLastWeek(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK,calendar.getActualMinimum(calendar.DAY_OF_WEEK));
		calendar.add(Calendar.WEEK_OF_MONTH,-1);
		calendar.add(Calendar.DAY_OF_WEEK,1);
		setStartOfDay(calendar);
		return calendar.getTime();
	}
	public static Date getStartOfLastMonth(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMinimum(calendar.DAY_OF_MONTH));
		calendar.add(Calendar.MONTH,-1);
		setStartOfDay(calendar);
		return calendar.getTime();
	}

	public static Date getStartOfThisMonth(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMinimum(calendar.DAY_OF_MONTH));
		setStartOfDay(calendar);
		return calendar.getTime();
	}

	public static Date getStartOfLastYear(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR,calendar.getActualMinimum(calendar.DAY_OF_YEAR));
		calendar.add(Calendar.YEAR,-1);
		setStartOfDay(calendar);
		return calendar.getTime();
	}
	public static Date getStartOfDay(int dateOffset){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,dateOffset);
		setStartOfDay(calendar);
		return calendar.getTime();
	}
	public static Date getEndOfLastWeek(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK,calendar.getActualMaximum(calendar.DAY_OF_WEEK));
		calendar.add(Calendar.WEEK_OF_MONTH,-1);
		calendar.add(Calendar.DAY_OF_WEEK,1);
		setEndOfDay(calendar);
		return calendar.getTime();
	}
	public static Date getEndOfLastMonth(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(calendar.DAY_OF_MONTH));
		calendar.add(Calendar.MONTH,-1);
		setEndOfDay(calendar);
		return calendar.getTime();
	}
	public static Date getEndOfLastYear(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR,calendar.getActualMaximum(calendar.DAY_OF_YEAR));
		calendar.add(Calendar.YEAR,-1);
		setEndOfDay(calendar);
		return calendar.getTime();
	}
	public static Date getEndOfDay(int dateOffset){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,dateOffset);
		setEndOfDay(calendar);
		return calendar.getTime();
	}

	public static void setStartOfDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY,calendar.getActualMinimum(calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE,calendar.getActualMinimum(calendar.MINUTE));
		calendar.set(Calendar.SECOND,calendar.getActualMinimum(calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,calendar.getActualMinimum(calendar.MILLISECOND));
	}

	public static void setEndOfDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY,calendar.getActualMaximum(calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE,calendar.getActualMaximum(calendar.MINUTE));
		calendar.set(Calendar.SECOND,calendar.getActualMaximum(calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,calendar.getActualMaximum(calendar.MILLISECOND));
	}
}

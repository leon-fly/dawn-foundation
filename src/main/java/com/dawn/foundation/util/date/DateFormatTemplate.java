package com.dawn.foundation.util.date;

public class DateFormatTemplate {

    public final static String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";
    public final static String DATE_FORMAT_SOLIDUS = "yyyy/MM/dd";
    public final static String DATE_FORMAT_COMPACT = "yyyyMMdd";
    public final static String DATE_FORMAT_UTC_DEFAULT = "MM-dd-yyyy";
    public final static String DATE_FORMAT_UTC_SOLIDUS = "MM/dd/yyyy";
    public final static String DATE_FORMAT_MONTH = "yyyy-MM";
    public final static String DATE_FORMAT_YEAR = "yyyy";
    public final static String DATE_FORMAT_CHINESE = "yyyy年MM月dd日";


    public final static String DATE_TIME_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_TIME_FORMAT_SOLIDUS = "yyyy/MM/dd HH:mm:ss";
    public final static String DATE_TIME_FORMAT_COMPACT = "yyyyMMdd HHmmss";
    public final static String DATE_TIME_FORMAT_COMPACT_S = "yyyyMMddHHmmss";
    public final static String DATE_TIME_FORMAT_COMPACT_SS = "yyyyMMddHHmmssSSS";
    public final static String DATE_TIME_FORMAT_CHINESE = "yyyy年MM月dd日 HH时mm分ss秒";
    public final static String DATE_TIME_FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public final static String DATE_TIME_FORMAT_ISO8601_WITH_TIME_ZONE = "yyyy-MM-dd'T'HH:mm:ssXXX";
    public final static String DATE_TIME_FORMAT_UTC_DEFAULT = "MM-dd-yyyy HH:mm:ss";
    public final static String DATE_TIME_FORMAT_UTC_SOLIDUS = "MM/dd/yyyy HH:mm:ss";

    public final static String TIME_FORMAT_DEFAULT = "HH:mm:ss";
    public final static String TIME_FORMAT_COMPACT = "HHmmss";

    public final static String DATE_FORMAT_DEFAULT_PATTERN = "^\\d{4}-\\d{1,2}-\\d{1,2}$";
    public final static String DATE_FORMAT_SOLIDUS_PATTERN = "^\\d{4}/\\d{1,2}/\\d{1,2}$";
    public final static String DATE_FORMAT_COMPACT_PATTERN = "^\\d{8}$";
    public final static String DATE_FORMAT_UTC_DEFAULT_PATTERN = "^\\d{1,2}-\\d{1,2}-\\d{4}$";
    public final static String DATE_FORMAT_UTC_SOLIDUS_PATTERN = "^\\d{1,2}/\\d{1,2}/\\d{4}$";

    public final static String DATE_TIME_FORMAT_DEFAULT_PATTERN = "^\\d{4}-\\d{1,2}-\\d{1,2}\\s*\\d{1,2}:\\d{1,2}:\\d{1,2}$";
    public final static String DATE_TIME_FORMAT_SOLIDUS_PATTERN = "^\\d{4}/\\d{1,2}/\\d{1,2}\\s*\\d{1,2}:\\d{1,2}:\\d{1,2}$";
    public final static String DATE_TIME_FORMAT_UTC_DEFAULT_PATTERN = "^\\d{1,2}-\\d{1,2}-\\d{4}\\s*\\d{1,2}:\\d{1,2}:\\d{1,2}$";
    public final static String DATE_TIME_FORMAT_UTC_SOLIDUS_PATTERN = "^\\d{1,2}/\\d{1,2}/\\d{4}\\s*\\d{1,2}:\\d{1,2}:\\d{1,2}$";
}


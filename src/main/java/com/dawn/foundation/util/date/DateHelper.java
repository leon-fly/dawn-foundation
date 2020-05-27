package com.dawn.foundation.util.date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

    private static Logger logger = LoggerFactory.getLogger(DateHelper.class);

    public static Date parse(String dateString, String format) {
        try {
            Date date = new SimpleDateFormat(format).parse(dateString);
            return date;
        } catch (ParseException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static Date parse(String dateString) {
        return parse(dateString, DateFormatTemplate.DATE_FORMAT_DEFAULT);
    }

    public static String format(Date date, String format) {
        if(date == null) {
            return null;
        }

        return new SimpleDateFormat(
                DateFormatTemplate.DATE_FORMAT_DEFAULT).format(date);
    }

    public static String format(Date date) {
        return format(date, DateFormatTemplate.DATE_FORMAT_DEFAULT);
    }
}

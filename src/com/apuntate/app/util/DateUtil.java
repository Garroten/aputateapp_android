package com.apuntate.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rafaelgarrote on 09/06/13.
 */
public class DateUtil {

    public static Date stringToDate(String date, String dateFormat) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.parse(date);
    }

    public static String dateToString(Date date, String dateFormat) {
        SimpleDateFormat  dateformat = new SimpleDateFormat(dateFormat);
        return dateformat.format(date);
    }
}

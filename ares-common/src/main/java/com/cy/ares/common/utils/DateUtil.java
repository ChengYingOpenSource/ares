package com.cy.ares.common.utils;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.base.Preconditions;

/**
 * @author derek.wq
 * @date 2018-05-31
 * @since v1.0.0
 */
public class DateUtil {

    private static final String            DEFAULT_DATE_STYLE = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter FORMATTER          = DateTimeFormat.forPattern(DEFAULT_DATE_STYLE);

    /**
     * 将日期转换为字符串
     *
     * @param date
     * @return java.lang.String
     * @since v1.0.0
     *
     * <PRE>
     * author: derek.wq
     * Date: 2017-11-07
     * </PRE>
     */
    public static String format(Date date) {
        Preconditions.checkArgument(date != null, "date is null.");
        DateTime dt = new DateTime(date);
        return dt.toString(FORMATTER);
    }

    public static String formatWithoutException(Date date) {
        if (date == null) {
            return null;
        }
        return format(date);
    }

    public static Date toDate(String dateString) {
        Preconditions.checkArgument(dateString != null, "dateString is null.");
        DateTime dt = DateTime.parse(dateString, FORMATTER);
        return dt.toDate();
    }

    /**
     * 按指定格式获取当天的日期
     *
     * @param format
     * @return java.lang.String
     * @since v1.0.0
     *
     * <PRE>
     * author: derek.wq
     * Date: 2017-11-07
     * </PRE>
     */
    public static String now(String format) {
        return format(new Date(), format);
    }

    public static String format(Date date, String format) {
        Preconditions.checkArgument(date != null, "date is null.");
        Preconditions.checkArgument(StringUtils.isNotBlank(format), "format is null.");
        DateTime dt = new DateTime(date);
        return dt.toString(DateTimeFormat.forPattern(format));
    }

    public static String getHourStr(Date date) {
        Preconditions.checkNotNull(date, "date is null.");
        String dateStr = format(date, DEFAULT_DATE_STYLE);
        return dateStr.substring(11, 13);
    }

    public static String getMinutesStr(Date date) {
        Preconditions.checkNotNull(date, "date is null.");
        String dateStr = format(date, DEFAULT_DATE_STYLE);
        return dateStr.substring(14, 16);
    }

    public static Date getTodayStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        System.out.println(getTodayStartTime().toString());
    }
}

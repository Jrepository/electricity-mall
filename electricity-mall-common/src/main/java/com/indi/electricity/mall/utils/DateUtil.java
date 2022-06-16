package com.indi.electricity.mall.utils;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    public static final String LOCAL_DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd hh:ss:mm";

    public static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern(LOCAL_DATE_PATTERN);
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    public static final LocalTime LOCAL_TIME_MIN = LocalTime.MIN;
    public static final LocalTime LOCAL_TIME_MAX = LocalTime.MAX;


    public static String getStartTime(LocalDate localDate) {
        return toString(getStartOfDay(localDate));
    }

    public static String getEndTime(LocalDate localDate) {
        return toString(getEndOfDay(localDate));
    }


    public static LocalDateTime getStartOfDay(LocalDate localDate) {
        return localDate == null ? null : LocalDateTime.of(localDate, LOCAL_TIME_MIN);
    }

    public static LocalDateTime getEndOfDay(LocalDate localDate) {
        return localDate == null ? null : LocalDateTime.of(localDate, LOCAL_TIME_MAX);
    }

    public static Date getStartDateOfDay(LocalDate localDate) {
        return localDate == null ? null : getDateOf(getStartOfDay(localDate));
    }

    public static Date getEndDateOfDay(LocalDate localDate) {
        return localDate == null ? null : getDateOf(getEndOfDay(localDate));
    }

    public static Date getDateOf(LocalDateTime localDateTime) {
        return localDateTime == null ? null : Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String toString(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.format(DATE_TIME_FORMATTER);
    }


    public static LocalDateTime getDateTimeFromTimestamp(long timestamp) {
        if (timestamp == 0) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
//        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone
//                .getDefault().toZoneId());
    }

    public static LocalDate getDateFromTimestamp(long timestamp) {
        LocalDateTime date = getDateTimeFromTimestamp(timestamp);
        return date == null ? null : date.toLocalDate();
    }


    public static long getLong(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}

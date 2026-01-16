package dev.rafandoo.cup.date;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Provides utility methods for date and time operations.
 * <p>
 * This class is based on the {@code java.time} API and offers
 * compatibility helpers for legacy {@link Date} usage.
 */
@Slf4j
@UtilityClass
public final class DateUtils {

    /**
     * Default date pattern (ISO-8601).
     */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * Default date-time pattern (ISO-8601).
     */
    public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * Formats a {@link LocalDate} using the default pattern ({@value #DEFAULT_DATE_PATTERN}).
     *
     * @param date the date to format.
     * @return the formatted date, or {@code null} if date is null.
     */
    public static String format(LocalDate date) {
        return format(date, DEFAULT_DATE_PATTERN);
    }

    /**
     * Formats a {@link LocalDate} using a custom pattern.
     *
     * @param date    the date to format.
     * @param pattern the formatting pattern.
     * @return the formatted date, or {@code null} if date is null.
     */
    public static String format(LocalDate date, String pattern) {
        if (date == null) {
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Formats a {@link LocalDateTime} using the default pattern ({@value #DEFAULT_DATE_TIME_PATTERN}).
     *
     * @param dateTime the date-time to format.
     * @return the formatted date-time, or {@code null} if dateTime is null.
     */
    public static String format(LocalDateTime dateTime) {
        return format(dateTime, DEFAULT_DATE_TIME_PATTERN);
    }

    /**
     * Formats a {@link LocalDateTime} using a custom pattern.
     *
     * @param dateTime the date-time to format.
     * @param pattern  the formatting pattern.
     * @return the formatted date-time, or {@code null} if dateTime is null.
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Parses a date string into a {@link LocalDate}.
     *
     * @param date    the date string.
     * @param pattern the date pattern.
     * @return the parsed {@link LocalDate}, or {@code null} if input is null.
     */
    public static LocalDate parseDate(String date, String pattern) {
        if (date == null) {
            return null;
        }
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Parses a date-time string into a {@link LocalDateTime}.
     *
     * @param dateTime the date-time string.
     * @param pattern  the date-time pattern.
     * @return the parsed {@link LocalDateTime}, or {@code null} if input is null.
     */
    public static LocalDateTime parseDateTime(String dateTime, String pattern) {
        if (dateTime == null) {
            return null;
        }
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Converts a Unix timestamp (seconds) to a {@link LocalDateTime}.
     *
     * @param timestampSeconds the Unix timestamp in seconds.
     * @return the corresponding {@link LocalDateTime}.
     */
    public static LocalDateTime fromTimestamp(long timestampSeconds) {
        return LocalDateTime.ofInstant(
            Instant.ofEpochSecond(timestampSeconds),
            ZoneId.systemDefault()
        );
    }

    /**
     * Converts a {@link LocalDateTime} to a Unix timestamp (seconds).
     *
     * @param dateTime the date-time to convert.
     * @return the Unix timestamp in seconds, or {@code null} if dateTime is null.
     */
    public static Long toTimestamp(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    /**
     * Converts a legacy {@link Date} to {@link LocalDateTime}.
     *
     * @param date the {@link Date} to convert.
     * @return the corresponding {@link LocalDateTime}, or {@code null} if date is null.
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * Converts a {@link LocalDateTime} to legacy {@link Date}.
     *
     * @param dateTime the date-time to convert.
     * @return the corresponding {@link Date}, or {@code null} if dateTime is null.
     */
    public static Date toDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Returns the current date-time.
     *
     * @return the current {@link LocalDateTime}.
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * Returns the current Unix timestamp (seconds).
     *
     * @return the current Unix timestamp
     */
    public static long currentTimestamp() {
        return Instant.now().getEpochSecond();
    }
}

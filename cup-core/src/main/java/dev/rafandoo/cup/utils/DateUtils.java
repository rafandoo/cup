package dev.rafandoo.cup.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Utility class for date and time operations.
 */
@Slf4j
public class DateUtils {

    private final SimpleDateFormat dateFormatter;

    /**
     * Creates a DateUtils instance with the default format "yyyy-MM-dd".
     */
    public DateUtils() {
        this("yyyy-MM-dd");
    }

    /**
     * Creates a DateUtils instance with a custom format.
     *
     * @param format the desired date format.
     */
    public DateUtils(String format) {
        SimpleDateFormat formatter;
        try {
            formatter = new SimpleDateFormat(format);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid date format: {}. Falling back to default format.", format);
            formatter = new SimpleDateFormat("yyyy-MM-dd");
        }
        this.dateFormatter = formatter;
    }

    /**
     * Formats a given Date object.
     *
     * @param date the Date to format.
     * @return the formatted date string or null if the date is null.
     */
    public String format(Date date) {
        if (date == null) return null;
        return this.dateFormatter.format(date);
    }

    /**
     * Formats a given LocalDate object.
     *
     * @param date the LocalDate to format.
     * @return the formatted date string or null if the date is null.
     */
    public String format(LocalDate date) {
        if (date == null) return null;
        return date.format(DateTimeFormatter.ofPattern(this.dateFormatter.toPattern()));
    }

    /**
     * Parses a date string into a Date object.
     *
     * @param dateStr the date string to parse.
     * @return the parsed Date object.
     * @throws ParseException if the date string cannot be parsed.
     */
    public Date parse(String dateStr) throws ParseException {
        if (StringUtils.isNullOrEmpty(dateStr)) return null;
        return this.dateFormatter.parse(dateStr);
    }

    /**
     * Converts a timestamp (in seconds) to a Date object.
     *
     * @param timestamp the timestamp to convert.
     * @return the corresponding Date object.
     */
    public static Date fromTimestamp(String timestamp) {
        return new Date(Long.parseLong(timestamp) * 1000);
    }

    /**
     * Converts a Date object to a Unix timestamp (in seconds).
     *
     * @param date the Date to convert.
     * @return the Unix timestamp or null if the date is null.
     */
    public static String toTimestamp(Date date) {
        if (date == null) return null;
        return String.valueOf(date.getTime() / 1000);
    }

    /**
     * Gets the current date.
     *
     * @return the current Date.
     */
    public static Date now() {
        return new Date();
    }

    /**
     * Gets the current Unix timestamp (in seconds).
     *
     * @return the current Unix timestamp as a string.
     */
    public static String currentTimestamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * Formats a LocalDateTime object into a string.
     *
     * @param dateTime the LocalDateTime to format.
     * @param pattern  the pattern to format with.
     * @return the formatted date-time string or null if dateTime is null.
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        if (dateTime == null) return null;
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}

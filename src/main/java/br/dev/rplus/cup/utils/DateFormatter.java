package br.dev.rplus.cup.utils;

import br.dev.rplus.cup.log.Logger;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Utility class to format dates.
 */
public class DateFormatter {

    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Default constructor.
     *
     * @param format the format to use for the date formatter.
     */
    public DateFormatter(String format) {
        try {
            dateFormatter = new SimpleDateFormat(format);
        } catch (Exception e) {
            Logger.warn("Failed to create date formatter with format: %s", format, e);
        }
    }

    /**
     * Returns the default date formatter.
     *
     * @return the default date formatter.
     */
    public static SimpleDateFormat getDateFormatter() {
        return dateFormatter;
    }

    /**
     * Formats a given date using the DATE_FORMATTER and returns the formatted date as a string.
     *
     * @param date the date to be formatted.
     * @return the formatted date as a string.
     */
    public static String format(Date date) {
        try {
            return getDateFormatter().format(date);
        } catch (Exception e) {
            Logger.warn("Failed to format date: %s", date, e);
        }
        return null;
    }

    /**
     * Formats a given date using the DATE_FORMATTER and returns the formatted date as a string.
     *
     * @param date the LocalDate to be formatted.
     * @return the formatted date as a string.
     */
    public static String format(LocalDate date) {
        try {
            return getDateFormatter().format(date);
        } catch (Exception e) {
            Logger.warn("Failed to format date: %s", date, e);
        }
        return null;
    }

    /**
     * Parses a given date string into a Date object.
     *
     * @param date the date string to be parsed.
     * @return the parsed Date object.
     */
    public static Date parse(String date) {
        try {
            return getDateFormatter().parse(date);
        } catch (ParseException pe) {
            Logger.warn("Failed to parse date: %s", date, pe);
        }
        return null;
    }

    /**
     * Generates a formatted timestamp from a given date.
     *
     * @param date the date to format.
     * @return the formatted timestamp.
     */
    public static String timestampFormat(Date date) {
        try {
            return String.valueOf(new Timestamp(date.getTime()).getTime() / 1000);
        } catch (Exception e) {
            Logger.warn("Failed to format timestamp: %s", date, e);
        }
        return null;
    }
}
